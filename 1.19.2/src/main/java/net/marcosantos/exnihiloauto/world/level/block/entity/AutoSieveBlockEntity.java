package net.marcosantos.exnihiloauto.world.level.block.entity;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.marcosantos.exnihiloauto.Configs;
import net.marcosantos.exnihiloauto.registries.ModBlockEntities;
import net.marcosantos.exnihiloauto.utils.EXNUtils;
import net.marcosantos.exnihiloauto.utils.InvHandler;
import net.marcosantos.exnihiloauto.world.inventory.AutoSieveMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import novamachina.exnihilosequentia.common.item.MeshItem;
import novamachina.exnihilosequentia.common.utility.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoSieveBlockEntity extends AutoBlockEntity {

	public static final int INV_SIZE = 17;
	private List<ItemStack> sieveDrops;

	public AutoSieveBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.AUTO_SIEVE.get(), pos, state, "tiles.title.sieve", INV_SIZE);
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, AutoBlockEntity self) {
		if (!level.isClientSide) {
			if (!self.storage.canExtractAmount(1))
				return;
			self.setFakePlayer((ServerLevel) level, "FakeSiever");
			boolean hasSiftable = !self.tileInv.isSlotEmpty(0);
			boolean hasMesh = !self.tileInv.isSlotEmpty(1);
			if (hasSiftable && hasMesh) {
				self.incrementTimer();
				self.storage.decreaseEnergy(1);
				if (self.isDone()) {
					var siftable = self.tileInv.get(0);
					var meshItem = (MeshItem) self.tileInv.get(1);
					if (sieveDrops == null || sieveDrops.isEmpty()) {
						sieveDrops = EXNUtils.getWithMeshChance(siftable, meshItem.getType(), level.random.nextFloat());
					}

					if (self.tileInv.tryInsertMany(sieveDrops)) {
						if (Config.enableMeshDurability())
							self.tileInv.getItem(2).hurtAndBreak(1, self.fakePlayer, (item) -> {});

						self.tileInv.extractItem(0, 1, false);
						self.resetTimer();
						sieveDrops.clear();
					}
				}
			} else {
				self.resetTimer();
			}
		}
	}

	@Override
	public int getFinishTime() {
		return Configs.AUTO_SIEVE_SPEED.get();
	}

	@Override
	public int getEnergyCapacity() {
		return Configs.AUTO_SIEVE_ENERGY_CAPACITY.get();
	}

	@Override
	public InvHandler createInventory() {
		return new InvHandler(INV_SIZE) {
			@Override
			public int[] insertSlots() {
				return new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
			}

			@Override
			public boolean canInsertOn(int slot) {
				return slot > 1;
			}

			@Override
			@ParametersAreNonnullByDefault
			public int @NotNull [] getSlotsForFace(Direction side) {
				if (side == Direction.DOWN)
					return new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
				else if (side == Direction.UP)
					return new int[] { 0 };
				return new int[0];
			}

			@Override
			@ParametersAreNonnullByDefault
			public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
				return direction == Direction.UP && i == 0;
			}

			@Override
			@ParametersAreNonnullByDefault
			public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
				return direction == Direction.DOWN && i > 1;
			}
		};
	}

	@Override
	public List<ItemStack> getUpgradeSlots() {
		return tileInv.getStackFromTo(13, 15);
	}

	@Override
	@ParametersAreNonnullByDefault
	public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
		return new AutoSieveMenu(id, inventory, tileInv, data);
	}
}
