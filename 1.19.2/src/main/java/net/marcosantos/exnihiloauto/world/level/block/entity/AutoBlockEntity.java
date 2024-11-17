package net.marcosantos.exnihiloauto.world.level.block.entity;

import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.utils.EnergyData;
import net.marcosantos.exnihiloauto.utils.InvHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AutoBlockEntity extends BlockEntity implements MenuProvider {

	public static final String TAG_SIEVE_TIME = "SieveCurrentTime";
	protected int timer = 0;

	public final InvHandler tileInv = this.createInventory();
	public final LazyOptional<IItemHandlerModifiable>[] capInv = SidedInvWrapper.create(tileInv, Direction.UP, Direction.DOWN);

	public static final String TAG_ENERGY = "Power";
	public final EnergyData storage = new EnergyData(this.getEnergyCapacity(), true, false);
	public final LazyOptional<IEnergyStorage> capEnergyStorage = LazyOptional.of(() -> storage);

	private final String containerTitle;
	protected FakePlayer fakePlayer;

	public static final int DATA_SIZE = 4;
	protected final ContainerData data =
			new ContainerData() {
				@Override
				public int get(int index) {
					return switch (index) {
					case 0 -> storage.getEnergyStored();
					case 1 -> storage.getMaxEnergyStored();
					case 2 -> timer;
					case 3 -> calcTime();
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

	private final int invSize;

	public AutoBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state, String containerTitle, int invSize) {
		super(tileEntityTypeIn, pos, state);
		this.containerTitle = containerTitle;
		this.invSize = invSize;
	}

	public abstract void tick(Level level, BlockPos pos, BlockState state, AutoBlockEntity self);

	public abstract InvHandler createInventory();

	public abstract int getFinishTime();

	public abstract int getEnergyCapacity();

	public void incrementTimer() {
		this.timer++;
		this.setChanged();
	}

	public void resetTimer() {
		this.timer = 0;
		this.setChanged();
	}

	public boolean isDone() {
		return this.timer >= this.calcTime();
	}

	public FakePlayer createFakePlayer(ServerLevel world, String name) {
		return new FakePlayer(world, new GameProfile(UUID.randomUUID(), name));
	}

	public void setFakePlayer(ServerLevel world, String name) {
		if (this.fakePlayer != null)
			return;
		this.fakePlayer = this.createFakePlayer(world, name);
	}

	public abstract List<ItemStack> getUpgradeSlots();

	public boolean hasUpgrade(ItemStack stack) {
		return this.getUpgradeSlots().stream()
				.map(ItemStack::getItem)
				.anyMatch(item -> item == stack.getItem());
	}

	public boolean hasUpgrade(RegistryObject<Item> upgradeObj) {
		return this.hasUpgrade(new ItemStack(upgradeObj.get()));
	}

	public int getCountOf(RegistryObject<Item> upgradeObj) {
		return (int) this.getUpgradeSlots().stream()
				.map(ItemStack::getItem)
				.filter(i -> i == upgradeObj.get())
				.count();
	}

	@Override
	@ParametersAreNonnullByDefault
	protected void saveAdditional(@Nonnull CompoundTag nbt) {
		super.saveAdditional(nbt);
		nbt.putInt(TAG_SIEVE_TIME, this.timer);
		ContainerHelper.saveAllItems(nbt, tileInv.stacks);
		nbt.put(TAG_ENERGY, this.storage.writeTag());
	}

	@Override
	@ParametersAreNonnullByDefault
	public void load(@Nonnull CompoundTag tag) {
		super.load(tag);
		this.timer = tag.getInt(TAG_SIEVE_TIME);
		this.tileInv.setSize(invSize);
		ContainerHelper.loadAllItems(tag, tileInv.stacks);
		this.storage.readTag(tag.getCompound(TAG_ENERGY));
	}

	@Override
	@Nonnull
	public Component getDisplayName() {
		return Component.translatable(this.containerTitle);
	}

	public int calcTime() {
		int speedBonus = this.hasUpgrade(ModItems.SPEED_UPGRADE) ? getCountOf(ModItems.SPEED_UPGRADE) * 20 : 0;
		return this.getFinishTime() - speedBonus;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (!isRemoved() && side != null) {
			if (cap == ForgeCapabilities.ENERGY && Direction.Plane.HORIZONTAL.test(side)) {
				return this.capEnergyStorage.cast();
			} else if (cap == ForgeCapabilities.ITEM_HANDLER) {
				switch (side) {
				case UP:
					return capInv[0].cast();
				case DOWN:
					return capInv[1].cast();
				}
			}
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		for (var cap : capInv) {
			cap.invalidate();
		}
		capEnergyStorage.invalidate();
	}
}
