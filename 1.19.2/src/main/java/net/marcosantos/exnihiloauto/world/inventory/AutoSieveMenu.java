package net.marcosantos.exnihiloauto.world.inventory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.marcosantos.exnihiloauto.registries.ModMenus;
import net.marcosantos.exnihiloauto.world.item.UpgradeItem;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoBlockEntity;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoSieveBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import novamachina.exnihilosequentia.common.item.MeshItem;
import novamachina.exnihilosequentia.common.item.mesh.MeshType;
import novamachina.exnihilosequentia.common.registries.ExNihiloRegistries;

public class AutoSieveMenu extends AbstractContainerMenu {

	private final ContainerData data;

	public AutoSieveMenu(int windowID, Inventory inventory, Container tileInv, ContainerData data) {
		super(ModMenus.AUTO_SIEVE.get(), windowID);

		this.data = data;

		this.addSlot(new ValidSlot(tileInv, 0, 35, 34, this::isSiftable));

		this.addSlot(new ValidSlot(tileInv, 1, 71, 34, stack -> stack.getItem() instanceof MeshItem));

		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 4; ++k) {
				this.addSlot(new Slot(tileInv, k + j * 4 + 2, 98 + k * 18, 16 + j * 18));
			}
		}

		for (int i = 0; i < 3; i++) {
			this.addSlot(new UpgradeSlot(tileInv, 14 + i, 182, 6 + i * 18, AutoSieveBlockEntity.class));
		}

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}

		this.addDataSlots(this.data);
	}

	public AutoSieveMenu(int windowID, Inventory inventory) {
		this(windowID, inventory, new SimpleContainer(AutoSieveBlockEntity.INV_SIZE), new SimpleContainerData(AutoBlockEntity.DATA_SIZE));
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	@Nonnull
	@ParametersAreNonnullByDefault
	public ItemStack quickMoveStack(Player player, int index) {
		var itemstack = ItemStack.EMPTY;
		var slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			var stackInSlot = slot.getItem();
			itemstack = stackInSlot.copy();

			if (index >= 3 && index < 14) {
				if (!this.moveItemStackTo(stackInSlot, 14, 50, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stackInSlot, itemstack);
			} else if (index != 2 && index != 1 && index != 0 && index != 14 && index != 15 && index != 16) {
				if (this.isSiftable(stackInSlot)) {
					if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (stackInSlot.getItem() instanceof MeshItem) {
					if (!this.moveItemStackTo(stackInSlot, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (stackInSlot.getItem() instanceof UpgradeItem) {
					if (!this.moveItemStackTo(stackInSlot, 14, 17, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 16 && index < 41) {
					if (!this.moveItemStackTo(stackInSlot, 41, 50, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 41 && index < 50 && !this.moveItemStackTo(stackInSlot, 14, 41, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stackInSlot, 17, 50, false)) {
				return ItemStack.EMPTY;
			}

			if (stackInSlot.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (stackInSlot.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, stackInSlot);
		}

		return itemstack;
	}

	private boolean isSiftable(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem) {
			boolean isSiftable = false;
			for (MeshType type : MeshType.values()) {
				if (ExNihiloRegistries.SIEVE_REGISTRY.isBlockSiftable(Block.byItem(stack.getItem()), type, false)) {
					isSiftable = true;
					break;
				}
			}
			return isSiftable;
		}
		return false;
	}

	public ContainerData getData() {
		return this.data;
	}

	@Override
	public boolean stillValid(@Nonnull Player playerIn) {
		return true;
	}
}
