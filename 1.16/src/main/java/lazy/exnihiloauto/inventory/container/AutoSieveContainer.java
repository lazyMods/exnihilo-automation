package lazy.exnihiloauto.inventory.container;

import lazy.exnihiloauto.inventory.slot.UpgradeSlot;
import lazy.exnihiloauto.inventory.slot.ValidSlot;
import lazy.exnihiloauto.setup.ModContainers;
import lazy.exnihiloauto.tiles.AutoSieveTile;
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
import novamachina.exnihilosequentia.common.item.mesh.EnumMesh;
import novamachina.exnihilosequentia.common.item.mesh.MeshItem;

import javax.annotation.Nonnull;

public class AutoSieveContainer extends Container {

    private final IIntArray data;

    public AutoSieveContainer(int windowID, PlayerInventory inventory, IInventory tileInv, IIntArray data) {
        super(ModContainers.AUTO_SIEVE.get(), windowID);

        this.data = data;

        this.addSlot(new ValidSlot(tileInv, 0, 35, 34, this::isSiftable));

        this.addSlot(new ValidSlot(tileInv, 1, 71, 34, stack -> stack.getItem() instanceof MeshItem));


        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 4; ++k) {
                this.addSlot(new Slot(tileInv, k + j * 4 + 2, 98 + k * 18, 16 + j * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            this.addSlot(new UpgradeSlot(tileInv, 14 + i, 182, 6 + i * 18, AutoSieveTile.class));
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

    public AutoSieveContainer(int windowID, PlayerInventory inventory) {
        this(windowID, inventory, new Inventory(AutoSieveTile.INV_SIZE), new IntArray(AutoSieveTile.DATA_SIZE));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    @Nonnull
    public ItemStack quickMoveStack(@Nonnull PlayerEntity playerIn, int index) {
        var itemstack = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            var stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index >= 3 && index <= 14) {
                if (!this.moveItemStackTo(stackInSlot, 14, 50, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stackInSlot, itemstack);
            } else if (index != 2 && index != 1 && index != 0) {
                if (this.isSiftable(stackInSlot)) {
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stackInSlot.getItem() instanceof MeshItem) {
                    if (!this.moveItemStackTo(stackInSlot, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 14 && index < 41) {
                    if (!this.moveItemStackTo(stackInSlot, 41, 50, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 41 && index < 50 && !this.moveItemStackTo(stackInSlot, 14, 41, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stackInSlot, 14, 50, false)) {
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

    private boolean isSiftable(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem) {
            boolean isSiftable = false;
            for (EnumMesh mesh : EnumMesh.values()) {
                if (ExNihiloRegistries.SIEVE_REGISTRY.isBlockSiftable(Block.byItem(stack.getItem()), mesh, false)) {
                    isSiftable = true;
                    break;
                }
            }
            return isSiftable;
        }
        return false;
    }

    public IIntArray getData() {
        return this.data;
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity playerIn) {
        return true;
    }
}
