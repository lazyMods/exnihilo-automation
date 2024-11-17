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

	public AutoHammerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.AUTO_HAMMER.get(), pos, state, "tiles.title.hammer", INV_SIZE);
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, AutoBlockEntity self) {
		Preconditions.checkNotNull(level);
		if (!level.isClientSide) {
			setFakePlayer((ServerLevel) level, "FakeHammer");

			boolean hasHammer = !self.tileInv.isSlotEmpty(0);
			boolean hasBlockToHammer = !self.tileInv.isSlotEmpty(1);

			var block = self.tileInv.getBlockItem(1);
			boolean canHammer = ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(block);
			if (canHammer && (block instanceof CompressedBlock && !hasUpgrade(ModItems.REINFORCED_UPGRADE))) {
				canHammer = false;
			}

			if (hasHammer && hasBlockToHammer && canHammer) {
				var drop = getDrop();
				if (self.tileInv.canInsertItemOnSlot(2, drop)) {
					if (self.storage.canExtractAmount(1)) {
						incrementTimer();
						self.storage.decreaseEnergy(1);
					}
					if (isDone()) {
						int dmg = tileInv.getBlockItem(1) instanceof CompressedBlock ? 9 : 1;
						self.tileInv.insertItem(2, drop, false);
						self.tileInv.extractItem(1, 1, false);
						self.tileInv.getItem(0).hurtAndBreak(dmg, fakePlayer, player -> {});
						resetTimer();
					}
				}
			} else {
				resetTimer();
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
			public int[] insertSlots() {
				return new int[] { 2 };
			}

			@Override
			public boolean canInsertOn(int slot) {
				return slot == 2;
			}

			@Override
			@Nonnull
			public int[] getSlotsForFace(@Nonnull Direction side) {
				if (side == Direction.UP) {
					return new int[] { 0, 1 };
				} else if (side == Direction.DOWN) {
					return new int[] { 2 };
				}
				return new int[0];
			}

			@Override
			public boolean canPlaceItemThroughFace(int index, @Nonnull ItemStack itemStackIn, Direction direction) {
				if (index == 2)
					return false;
				return index == 0 && itemStackIn.getItem() instanceof HammerBaseItem
						|| index == 1 && (itemStackIn.getItem() instanceof BlockItem
								&& ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(Block.byItem(itemStackIn.getItem())) || canHammerCompressedBlocks());
			}

			@Override
			public boolean canTakeItemThroughFace(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
				return index == 2;
			}
		};
	}

	@Override
	public List<ItemStack> getUpgradeSlots() {
		return this.tileInv.getStackFromTo(3, 5);
	}

	@Nullable @Override
	public AbstractContainerMenu createMenu(int id, @Nonnull Inventory playerInventory, @Nonnull Player player) {
		return new AutoHammerMenu(id, playerInventory, this.tileInv, this.data);
	}

	private boolean canHammerCompressedBlocks() {
		return this.hasUpgrade(ModItems.REINFORCED_UPGRADE) && this.tileInv.getBlockItem(1) instanceof CompressedBlock;
	}

	private ItemStack getDrop() {
		Preconditions.checkNotNull(this.level);
		int count = this.hasUpgrade(ModItems.BONUS_UPGRADE) && this.level.random.nextFloat() < (.25f * this.getCountOf(ModItems.BONUS_UPGRADE)) ? 2 : 1;
		if (this.canHammerCompressedBlocks() && this.tileInv.getBlockItem(1) instanceof CompressedBlock)
			return new ItemStack(EnumCompressedBlocks.getCompressed((CompressedBlock) this.tileInv.getBlockItem(1)), 9);
		return new ItemStack(EXNUtils.getHammeredOutput(this.tileInv.getBlockItem(1)).getItem(), count);
	}
}
