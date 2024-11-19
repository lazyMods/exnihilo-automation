package net.marcosantos.exnihiloauto.world.inventory;

import net.marcosantos.exnihiloauto.registries.ModMenus;
import net.marcosantos.exnihiloauto.world.item.UpgradeItem;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoSilkerBlockEntity;
import net.minecraft.tags.BlockTags;
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
import novamachina.exnihilosequentia.world.item.EXNItems;

import javax.annotation.Nonnull;

public class AutoSilkerMenu extends AbstractContainerMenu {

	private final ContainerData data;

	public AutoSilkerMenu(int windowID, Inventory inventory, Container tileInv, ContainerData data) {
		super(ModMenus.AUTO_SILKER.get(), windowID);

		this.data = data;

		this.addSlot(new ValidSlot(tileInv, 0, 54, 34, stack -> stack.getItem() == EXNItems.SILKWORM.asItem()));

		this.addSlot(new ValidSlot(tileInv, 1, 98, 34, stack -> stack.getItem() instanceof BlockItem && Block.byItem(stack.getItem()).defaultBlockState().is(BlockTags.LEAVES)));

		this.addSlot(new Slot(tileInv, 2, 134, 34));

		for (int i = 0; i < 3; i++) {
			this.addSlot(new UpgradeSlot(tileInv, 3 + i, 182, 6 + i * 18, AutoSilkerBlockEntity.class));
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

	public AutoSilkerMenu(int windowID, Inventory inventory) {
		this(windowID, inventory, new SimpleContainer(AutoSilkerBlockEntity.INV_SIZE), new SimpleContainerData(AutoSilkerBlockEntity.DATA_SIZE));
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
			boolean isLeaves = stackInSlot.getItem() instanceof BlockItem && Block.byItem(stackInSlot.getItem()).defaultBlockState().is(BlockTags.LEAVES);
			if (index == 2) {
				if (!this.moveItemStackTo(stackInSlot, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stackInSlot, itemstack);
			} else if (index != 0 && index != 1 && index != 3 && index != 4 && index != 5) {
				if (stackInSlot.getItem() == EXNItems.SILKWORM.asItem()) {
					if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (isLeaves) {
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
}