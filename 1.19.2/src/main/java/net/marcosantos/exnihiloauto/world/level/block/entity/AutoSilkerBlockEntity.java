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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;
import org.jetbrains.annotations.NotNull;

public class AutoSilkerBlockEntity extends AutoBlockEntity {

	public static final int INV_SIZE = 6;
	protected final LazyOptional<IItemHandlerModifiable>[] invCap = SidedInvWrapper.create(tileInv, Direction.UP, Direction.DOWN, Direction.SOUTH);

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
					case 3 -> calcTime();
					case 4 -> silkwormTimer;
					case 5 -> calcTime() / 4;
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
			boolean hasSilk = !this.tileInv.isSlotEmpty(0);
			boolean hasLeaves = !this.tileInv.isSlotEmpty(1);
			if (hasSilk && hasLeaves) {
				if (!this.tileInv.isSlotFull(2)) {
					if (this.storage.canExtractAmount(1)) {
						this.incrementTimer();
						this.incrementSilkwormTime();
						this.storage.decreaseEnergy(1);
					}
					if (this.isDone()) {
						this.tileInv.extractItem(0, 1, false);
						this.resetTimer();
					}
					if (this.isSilkwormDone()) {
						int count = this.hasUpgrade(ModItems.BONUS_UPGRADE) && this.level.random.nextFloat() < (.25f * this.getCountOf(ModItems.BONUS_UPGRADE)) ? 2 : 1;
						this.tileInv.insertItem(2, new ItemStack(Items.STRING, count), false);
						this.tileInv.extractItem(1, 1, false);
						this.resetSilkwormTimer();
					}
				}
			}
		}
	}

	public void incrementSilkwormTime() {
		this.silkwormTimer++;
		this.setChanged();
	}

	public boolean isSilkwormDone() {
		return this.silkwormTimer >= this.calcTime() / 4;
	}

	public void resetSilkwormTimer() {
		this.silkwormTimer = 0;
		this.setChanged();
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
			public int[] insertSlots() {
				return new int[] { 1, 2 };
			}

			@Override
			public boolean canInsertOn(int slot) {
				return slot == 0 || slot == 1;
			}

			@Override
			@Nonnull
			@ParametersAreNonnullByDefault
			public int[] getSlotsForFace(Direction side) {
				if (side == Direction.DOWN)
					return new int[] { 2 };
				if (side == Direction.UP)
					return new int[] { 1 };
				if (side == Direction.SOUTH)
					return new int[] { 0 };
				return new int[0];
			}

			@Override
			public boolean canPlaceItemThroughFace(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
				if (direction == Direction.UP || direction == Direction.SOUTH) {
					if (index == 0 && itemStackIn.getItem() == ExNihiloItems.SILKWORM.get())
						return true;
				}
				return index == 1 && itemStackIn.getItem() instanceof BlockItem && Block.byItem(itemStackIn.getItem()).defaultBlockState().is(BlockTags.LEAVES);
			}

			@Override
			@ParametersAreNonnullByDefault
			public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
				return direction == Direction.DOWN && index == 2;
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
		return this.tileInv.getStackFromTo(3, 5);
	}

	@Nullable @Override
	@ParametersAreNonnullByDefault
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
		return new AutoSilkerMenu(windowId, playerInventory, this.tileInv, this.data);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
		if (!isRemoved() && side != null) {
			if (cap == ForgeCapabilities.ENERGY && Direction.Plane.HORIZONTAL.test(side)) {
				return capEnergyStorage.cast();
			} else if (cap == ForgeCapabilities.ITEM_HANDLER) {
				switch (side) {
				case UP:
					return invCap[0].cast();
				case DOWN:
					return invCap[1].cast();
				case SOUTH:
					return invCap[2].cast();
				}
			}
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		for (var cap : invCap) {
			cap.invalidate();
		}
		capEnergyStorage.invalidate();
	}
}
