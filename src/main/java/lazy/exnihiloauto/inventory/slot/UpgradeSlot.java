package lazy.exnihiloauto.inventory.slot;

import lazy.exnihiloauto.items.UpgradeItem;
import lazy.exnihiloauto.tiles.AutoTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class UpgradeSlot extends Slot {

    private final Class<? extends AutoTileEntity> tileClass;

    public UpgradeSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, Class<? extends AutoTileEntity> tileClass) {
        super(inventoryIn, index, xPosition, yPosition);
        this.tileClass = tileClass;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof UpgradeItem && ((UpgradeItem) stack.getItem()).canApplyOn(this.tileClass);
    }
}
