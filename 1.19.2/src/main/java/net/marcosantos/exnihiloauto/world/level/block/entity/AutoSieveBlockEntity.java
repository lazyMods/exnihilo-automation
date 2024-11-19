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

	public static final int ENERGY_PER_TICK = 1;

	public static final int INPUT_SLOT = 0;
	public static final int MESH_SLOT = 1;

	public static final int[] OUTPUT_SLOTS = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

	private List<ItemStack> sieveDrops;

	public AutoSieveBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.AUTO_SIEVE.get(), pos, state, "tiles.title.sieve", INV_SIZE);
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, AutoBlockEntity self) {
		if (!level.isClientSide) {

			if (!storage.canExtractAmount(ENERGY_PER_TICK)) {
				resetTimer();
				return;
			}

			setFakePlayer((ServerLevel) level, "FakeSiever");

			boolean hasInput = !tileInv.isSlotEmpty(INPUT_SLOT);
			boolean hasMesh = !tileInv.isSlotEmpty(MESH_SLOT);

			if (!hasInput || !hasMesh) {
				resetTimer();
				return;
			}

			incrementTimer();
			storage.decreaseEnergy(ENERGY_PER_TICK);

			if (isDone()) {

				if (sieveDrops == null || sieveDrops.isEmpty()) {
					var input = tileInv.getBlockItem(INPUT_SLOT);
					var mesh = (MeshItem) tileInv.get(MESH_SLOT);
					sieveDrops = EXNUtils.getWithMeshChance(input, mesh.getType(), level.random.nextFloat());
				}

				if (tileInv.tryInsertMany(sieveDrops)) {

					if (Config.enableMeshDurability()) {
						tileInv.getItem(MESH_SLOT).hurtAndBreak(1, fakePlayer, (item) -> {});
					}

					tileInv.extractItem(INPUT_SLOT, 1, false);

					resetTimer();
					sieveDrops.clear();
				}
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
			public int[] outputSlots() {
				return OUTPUT_SLOTS;
			}

			@Override
			public boolean isOutputSlot(int slot) {
				return slot > 1 && slot < 14;
			}

			@Override
			@ParametersAreNonnullByDefault
			public int @NotNull [] getSlotsForFace(Direction side) {
				if (side == Direction.DOWN)
					return OUTPUT_SLOTS;
				else if (side == Direction.UP)
					return new int[] { INPUT_SLOT };
				return new int[0];
			}

			@Override
			@ParametersAreNonnullByDefault
			public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
				return direction == Direction.UP && i == INPUT_SLOT;
			}

			@Override
			@ParametersAreNonnullByDefault
			public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
				return direction == Direction.DOWN && i > 1 && i < 14;
			}
		};
	}

	@Override
	public List<ItemStack> getUpgradeSlots() {
		// Note: 'to' isn't inclusive
		return tileInv.getStackFromTo(14, 17);
	}

	@Override
	@ParametersAreNonnullByDefault
	public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
		return new AutoSieveMenu(id, inventory, tileInv, data);
	}
}
