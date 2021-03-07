package lazy.exnihiloauto.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class ValidSlot extends Slot {

    private final Predicate<ItemStack> stackPredicate;

    public ValidSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, Predicate<ItemStack> stackPredicate) {
        super(inventoryIn, index, xPosition, yPosition);
        this.stackPredicate = stackPredicate;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return this.stackPredicate.test(stack);
    }
}
