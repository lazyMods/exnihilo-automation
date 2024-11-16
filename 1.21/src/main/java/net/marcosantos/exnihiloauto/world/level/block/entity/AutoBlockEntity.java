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
import net.minecraft.core.HolderLookup;
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
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.registries.DeferredItem;

public abstract class AutoBlockEntity extends BlockEntity implements MenuProvider {

	public static final String TAG_SIEVE_TIME = "SieveCurrentTime";
	protected int timer = 0;

	public final InvHandler tileInv = this.createInventory();
	public static final String TAG_ENERGY = "Power";
	public final EnergyData storage = new EnergyData(this.getEnergyCapacity(), true, false);

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

	public boolean hasUpgrade(DeferredItem<Item> upgradeObj) {
		return this.hasUpgrade(new ItemStack(upgradeObj.get()));
	}

	public int getCountOf(DeferredItem<Item> upgradeObj) {
		return (int) this.getUpgradeSlots().stream()
				.map(ItemStack::getItem)
				.filter(i -> i == upgradeObj.get())
				.count();
	}

	@Override
	@ParametersAreNonnullByDefault
	protected void saveAdditional(@Nonnull CompoundTag nbt, @Nonnull HolderLookup.Provider registries) {
		super.saveAdditional(nbt, registries);
		nbt.putInt(TAG_SIEVE_TIME, this.timer);
		ContainerHelper.saveAllItems(nbt, tileInv.stacks, registries);
		nbt.put(TAG_ENERGY, this.storage.writeTag());
	}

	@Override
	@ParametersAreNonnullByDefault
	protected void loadAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.timer = tag.getInt(TAG_SIEVE_TIME);
		this.tileInv.setSize(invSize);
		ContainerHelper.loadAllItems(tag, tileInv.stacks, registries);
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
}
