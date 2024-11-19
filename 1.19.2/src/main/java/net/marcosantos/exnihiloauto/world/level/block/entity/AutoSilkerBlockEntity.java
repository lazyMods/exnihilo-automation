package net.marcosantos.exnihiloauto.world.level.block.entity;

import com.google.common.base.Preconditions;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.marcosantos.exnihiloauto.Configs;
import net.marcosantos.exnihiloauto.registries.ModBlockEntities;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.utils.InvHandler;
import net.marcosantos.exnihiloauto.world.inventory.AutoSilkerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;
import org.jetbrains.annotations.NotNull;

public class AutoSilkerBlockEntity extends AutoBlockEntity {

	public static final int INV_SIZE = 6;

	public static final int ENERGY_PER_TICK = 1;

	public static final int SILK_SLOT = 0;
	public static final int LEAVES_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;

	public static final String TAG_SILKWORM_TIMER = "silkworm_effect_time";
	public int silkwormTimer = 0;

	public static final int DATA_SIZE = 6;
	protected final ContainerData data =
			new ContainerData() {
				@Override
				public int get(int index) {
					return switch (index) {
					case 0 -> storage.getEnergyStored();
					case 1 -> storage.getMaxEnergyStored();
					case 2 -> timer;
					case 3 -> calcTime() / 4;
					case 4 -> silkwormTimer;
					case 5 -> calcTime();
					default -> -1;
					};
				}

				@Override
				public void set(int index, int value) {}

				@Override
				public int getCount() {
					return DATA_SIZE;
				}
			};

	public AutoSilkerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.AUTO_SILKER.get(), pos, state, "tiles.title.silker", INV_SIZE);
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, AutoBlockEntity self) {
		Preconditions.checkNotNull(this.level);
		if (!this.level.isClientSide) {
			if (!storage.canExtractAmount(ENERGY_PER_TICK) || tileInv.isSlotFull(OUTPUT_SLOT)) {
				resetTimer();
				return;
			}

			boolean hasSilk = !this.tileInv.isSlotEmpty(SILK_SLOT);
			boolean hasLeaves = !this.tileInv.isSlotEmpty(LEAVES_SLOT);

			if (!hasSilk || !hasLeaves) {
				resetTimer();
				return;
			}

			this.incrementTimer();
			this.incrementSilkwormTime();
			this.storage.decreaseEnergy(ENERGY_PER_TICK);

			if (this.isDone()) {
				int count = this.hasUpgrade(ModItems.BONUS_UPGRADE) && this.level.random.nextFloat() < (.25f * this.getCountOf(ModItems.BONUS_UPGRADE)) ? 2 : 1;
				this.tileInv.extractItem(LEAVES_SLOT, 1, false);
				this.tileInv.insertItem(OUTPUT_SLOT, new ItemStack(Items.STRING, count), false);
				this.resetTimer();
			}
			if (this.isSilkwormDone()) {
				this.tileInv.extractItem(SILK_SLOT, 1, false);
				this.resetSilkwormTimer();
			}
		}
	}

	public void incrementSilkwormTime() {
		this.silkwormTimer++;
		this.setChanged();
	}

	public boolean isSilkwormDone() {
		return this.silkwormTimer >= this.calcTime();
	}

	public void resetSilkwormTimer() {
		this.silkwormTimer = 0;
		this.setChanged();
	}

	@Override
	public boolean isDone() {
		return timer >= calcTime() / 4;
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.silkwormTimer = tag.getInt(TAG_SILKWORM_TIMER);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag nbt) {
		super.saveAdditional(nbt);
		nbt.putInt(TAG_SILKWORM_TIMER, this.silkwormTimer);
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
			@ParametersAreNonnullByDefault
			public int[] getSlotsForFace(Direction side) {
				if (side == Direction.DOWN)
					return new int[] { OUTPUT_SLOT };
				if (side == Direction.UP)
					return new int[] { SILK_SLOT, LEAVES_SLOT };
				return new int[0];
			}

			@Override
			public boolean canPlaceItemThroughFace(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
				if (direction == Direction.UP) {
					return index == SILK_SLOT && itemStackIn.getItem() == ExNihiloItems.SILKWORM.get()
							|| index == LEAVES_SLOT && itemStackIn.getItem() instanceof BlockItem && Block.byItem(itemStackIn.getItem()).defaultBlockState().is(BlockTags.LEAVES);
				}
				return false;
			}

			@Override
			@ParametersAreNonnullByDefault
			public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
				return index == OUTPUT_SLOT && direction == Direction.DOWN;
			}
		};
	}

	@Override
	public int getFinishTime() {
		return Configs.AUTO_SILKER_SPEED.get();
	}

	@Override
	public int getEnergyCapacity() {
		return Configs.AUTO_SILKER_ENERGY_CAPACITY.get();
	}

	@Override
	public List<ItemStack> getUpgradeSlots() {
		// Note: 'to' isn't inclusive
		return this.tileInv.getStackFromTo(3, 6);
	}

	@Nullable @Override
	@ParametersAreNonnullByDefault
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
		return new AutoSilkerMenu(windowId, playerInventory, this.tileInv, this.data);
	}
}
