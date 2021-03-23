package lazy.exnihiloauto.inventory.container;

import lazy.exnihiloauto.inventory.slot.UpgradeSlot;
import lazy.exnihiloauto.inventory.slot.ValidSlot;
import lazy.exnihiloauto.items.ReinforcedHammerItem;
import lazy.exnihiloauto.setup.ModContainers;
import lazy.exnihiloauto.setup.ModItems;
import lazy.exnihiloauto.tiles.AutoHammerTile;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import novamachina.exnihilosequentia.api.ExNihiloRegistries;
import novamachina.exnihilosequentia.common.item.tools.hammer.HammerBaseItem;

import javax.annotation.Nonnull;

public class AutoHammerContainer extends Container {

    private final IIntArray data;
    private final IInventory tileInv;

    public AutoHammerContainer(int windowID, PlayerInventory inventory, IInventory tileInv, IIntArray data) {
        super(ModContainers.AUTO_HAMMER.get(), windowID);

        this.data = data;
        this.tileInv = tileInv;

        this.addSlot(new ValidSlot(tileInv, 0, 54, 34, stack ->
                this.hasUpgrade(new ItemStack(ModItems.REINFORCED_UPGRADE.get())) ? stack.getItem() instanceof ReinforcedHammerItem :
                        stack.getItem() instanceof HammerBaseItem));

        this.addSlot(new ValidSlot(tileInv, 1, 98, 34, stack -> {
            if (stack.getItem() instanceof BlockItem && ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(Block.getBlockFromItem(stack.getItem())))
                return true;
            return stack.getItem() instanceof BlockItem && hasUpgrade(new ItemStack(ModItems.REINFORCED_UPGRADE.get()));
        }));

        this.addSlot(new Slot(tileInv, 2, 134, 34));

        for (int i = 0; i < 3; i++) {
            this.addSlot(new UpgradeSlot(tileInv, 3 + i, 182, 6 + i * 18, AutoHammerTile.class));
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }

        this.trackIntArray(this.data);
    }

    public AutoHammerContainer(int windowID, PlayerInventory inventory) {
        this(windowID, inventory, new Inventory(AutoHammerTile.INV_SIZE), new IntArray(AutoHammerTile.DATA_SIZE));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    @Nonnull
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index) {
        var itemstack = ItemStack.EMPTY;
        var slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            var stackInSlot = slot.getStack();
            itemstack = stackInSlot.copy();
            boolean isHammerable = stackInSlot.getItem() instanceof BlockItem
                    && ExNihiloRegistries.HAMMER_REGISTRY.isHammerable(Block.getBlockFromItem(stackInSlot.getItem()));

            if (index == 2) {
                if (!this.mergeItemStack(stackInSlot, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stackInSlot, itemstack);
            } else if (index != 1 && index != 0) {
                if (stackInSlot.getItem() instanceof HammerBaseItem) {
                    if (!this.mergeItemStack(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isHammerable) {
                    if (!this.mergeItemStack(stackInSlot, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(stackInSlot, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(stackInSlot, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stackInSlot, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stackInSlot);
        }

        return itemstack;
    }

    public IIntArray getData() {
        return this.data;
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return true;
    }

    public boolean hasUpgrade(ItemStack stack) {
        return this.tileInv.getStackInSlot(AutoHammerTile.INV_SIZE - 3).getItem() == stack.getItem()
                || this.tileInv.getStackInSlot(AutoHammerTile.INV_SIZE - 2).getItem() == stack.getItem()
                || this.tileInv.getStackInSlot(AutoHammerTile.INV_SIZE - 1).getItem() == stack.getItem();
    }
}
