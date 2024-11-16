package net.marcosantos.exnihiloauto.world.inventory;

import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.registries.ModMenus;
import net.marcosantos.exnihiloauto.world.item.UpgradeItem;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoHammerBlockEntity;
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
import novamachina.exnihilosequentia.common.registries.ExNihiloRegistries;
import novamachina.exnihilosequentia.world.item.HammerItem;

import javax.annotation.Nonnull;

public class AutoHammerMenu extends AbstractContainerMenu {

	private final ContainerData data;
	private final Container tileInv;

	public AutoHammerMenu(int windowID, Inventory inventory, Container tileInv, ContainerData data) {
		super(ModMenus.AUTO_HAMMER.get(), windowID);

		this.data = data;
		this.tileInv = tileInv;

		this.addSlot(new ValidSlot(tileInv, 0, 54, 34, stack -> stack.getItem() instanceof HammerItem));

		this.addSlot(new ValidSlot(tileInv, 1, 98, 34, stack -> {
			if (stack.getItem() instanceof BlockItem && ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(Block.byItem(stack.getItem())))
				return true;
			return stack.getItem() instanceof BlockItem && hasUpgrade(new ItemStack(ModItems.REINFORCED_UPGRADE.get()));
		}));

		this.addSlot(new Slot(tileInv, 2, 134, 34));

		for (int i = 0; i < 3; i++) {
			this.addSlot(new UpgradeSlot(tileInv, 3 + i, 182, 6 + i * 18, AutoHammerBlockEntity.class));
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

	public AutoHammerMenu(int windowID, Inventory inventory) {
		this(windowID, inventory, new SimpleContainer(AutoHammerBlockEntity.INV_SIZE), new SimpleContainerData(AutoHammerBlockEntity.DATA_SIZE));
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	@Nonnull
	public ItemStack quickMoveStack(@Nonnull Player playerIn, int index) {
		var itemstack = ItemStack.EMPTY;
		var slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			var stackInSlot = slot.getItem();
			itemstack = stackInSlot.copy();
			boolean isHammerable = stackInSlot.getItem() instanceof BlockItem
					&& ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(Block.byItem(stackInSlot.getItem()));

			if (index == 2) {
				if (!this.moveItemStackTo(stackInSlot, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stackInSlot, itemstack);
			} else if (index != 0 && index != 1 && index != 3 && index != 4 && index != 5) {
				if (stackInSlot.getItem() instanceof HammerItem) {
					if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (isHammerable) {
					if (!this.moveItemStackTo(stackInSlot, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (stackInSlot.getItem() instanceof UpgradeItem) {
					if (!this.moveItemStackTo(stackInSlot, 3, 6, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 6 && index < 30) {
					if (!this.moveItemStackTo(stackInSlot, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !this.moveItemStackTo(stackInSlot, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stackInSlot, 6, 39, false)) {
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

			slot.onTake(playerIn, stackInSlot);
		}

		return itemstack;
	}

	public ContainerData getData() {
		return this.data;
	}

	@Override
	public boolean stillValid(@Nonnull Player playerIn) {
		return true;
	}

	public boolean hasUpgrade(ItemStack stack) {
		return this.tileInv.getItem(AutoHammerBlockEntity.INV_SIZE - 3).getItem() == stack.getItem()
				|| this.tileInv.getItem(AutoHammerBlockEntity.INV_SIZE - 2).getItem() == stack.getItem()
				|| this.tileInv.getItem(AutoHammerBlockEntity.INV_SIZE - 1).getItem() == stack.getItem();
	}
}
