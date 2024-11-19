package net.marcosantos.exnihiloauto.world.level.block.entity;

import com.google.common.base.Preconditions;
import net.marcosantos.exnihiloauto.Configs;
import net.marcosantos.exnihiloauto.registries.ModBlockEntities;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.utils.EXNUtils;
import net.marcosantos.exnihiloauto.utils.EnumCompressedBlocks;
import net.marcosantos.exnihiloauto.utils.InvHandler;
import net.marcosantos.exnihiloauto.world.inventory.AutoHammerMenu;
import net.marcosantos.exnihiloauto.world.level.block.CompressedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import novamachina.exnihilosequentia.common.item.HammerBaseItem;
import novamachina.exnihilosequentia.common.registries.ExNihiloRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AutoHammerBlockEntity extends AutoBlockEntity {

	public static final int INV_SIZE = 6;

	public static final int HAMMER_SLOT = 0;
	public static final int INPUT_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;

	public static final int ENERGY_PER_TICK = 1;

	public AutoHammerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.AUTO_HAMMER.get(), pos, state, "tiles.title.hammer", INV_SIZE);
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, AutoBlockEntity self) {
		if (!level.isClientSide) {

			if (!storage.canExtractAmount(ENERGY_PER_TICK) || tileInv.isSlotFull(OUTPUT_SLOT)) {
				resetTimer();
				return;
			}

			setFakePlayer((ServerLevel) level, "FakeHammer");

			boolean hasHammer = !tileInv.isSlotEmpty(HAMMER_SLOT);
			boolean hasBlockToHammer = !tileInv.isSlotEmpty(INPUT_SLOT);

			if (!hasHammer || !hasBlockToHammer) {
				resetTimer();
				return;
			}

			var block = tileInv.getBlockItem(INPUT_SLOT);
			boolean canHammer = ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(block);

			if (!canHammer) {
				resetTimer();
				return;
			}

			boolean isCompressed = block instanceof CompressedBlock;

			if (isCompressed && !hasUpgrade(ModItems.REINFORCED_UPGRADE)) {
				resetTimer();
				return;
			}

			incrementTimer();
			storage.decreaseEnergy(ENERGY_PER_TICK);

			if (isDone()) {
				var drop = getDrop();
				if (tileInv.canInsertItemOnSlot(OUTPUT_SLOT, drop)) {
					int dmg = isCompressed ? 9 : 1;
					tileInv.insertItem(OUTPUT_SLOT, drop, false);
					tileInv.extractItem(INPUT_SLOT, 1, false);
					tileInv.getItem(HAMMER_SLOT).hurtAndBreak(dmg, fakePlayer, player -> {});
					resetTimer();
				}
			}
		}
	}

	@Override
	public int getFinishTime() {
		return Configs.AUTO_HAMMER_SPEED.get();
	}

	@Override
	public int getEnergyCapacity() {
		return Configs.AUTO_HAMMER_ENERGY_CAPACITY.get();
	}

	@Override
	public InvHandler createInventory() {
		return new InvHandler(INV_SIZE) {
			@Override
			public int[] outputSlots() {
				return new int[] { OUTPUT_SLOT };
			}

			@Override
			public boolean isOutputSlot(int slot) {
				return slot == OUTPUT_SLOT;
			}

			@Override
			@Nonnull
			public int[] getSlotsForFace(@Nonnull Direction side) {
				if (side == Direction.UP) {
					return new int[] { HAMMER_SLOT, INPUT_SLOT };
				} else if (side == Direction.DOWN) {
					return new int[] { OUTPUT_SLOT };
				}
				return new int[0];
			}

			@Override
			public boolean canPlaceItemThroughFace(int index, @Nonnull ItemStack itemStackIn, Direction direction) {
				if (index == OUTPUT_SLOT)
					return false;
				return index == HAMMER_SLOT && itemStackIn.getItem() instanceof HammerBaseItem
						|| index == INPUT_SLOT && (itemStackIn.getItem() instanceof BlockItem
								&& ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(Block.byItem(itemStackIn.getItem())) || canHammerCompressedBlocks());
			}

			@Override
			public boolean canTakeItemThroughFace(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
				return index == OUTPUT_SLOT;
			}
		};
	}

	@Override
	public List<ItemStack> getUpgradeSlots() {
		// Note: 'to' isn't inclusive
		return this.tileInv.getStackFromTo(3, 6);
	}

	@Nullable @Override
	public AbstractContainerMenu createMenu(int id, @Nonnull Inventory playerInventory, @Nonnull Player player) {
		return new AutoHammerMenu(id, playerInventory, this.tileInv, this.data);
	}

	private boolean canHammerCompressedBlocks() {
		return this.hasUpgrade(ModItems.REINFORCED_UPGRADE) && this.tileInv.getBlockItem(INPUT_SLOT) instanceof CompressedBlock;
	}

	private ItemStack getDrop() {
		Preconditions.checkNotNull(this.level);
		var input = tileInv.getBlockItem(INPUT_SLOT);
		int count = this.hasUpgrade(ModItems.BONUS_UPGRADE) && this.level.random.nextFloat() < (.25f * this.getCountOf(ModItems.BONUS_UPGRADE)) ? 2 : 1;
		if (this.canHammerCompressedBlocks() && input instanceof CompressedBlock)
			return new ItemStack(EnumCompressedBlocks.getCompressed((CompressedBlock) input), 9);
		return new ItemStack(EXNUtils.getHammeredOutput(input).getItem(), count);
	}
}
