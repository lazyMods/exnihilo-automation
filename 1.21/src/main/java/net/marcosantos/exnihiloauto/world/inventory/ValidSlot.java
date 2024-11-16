package net.marcosantos.exnihiloauto.world.inventory;

import java.util.function.Predicate;
import javax.annotation.Nonnull;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ValidSlot extends Slot {

	private final Predicate<ItemStack> stackPredicate;

	public ValidSlot(Container inventoryIn, int index, int xPosition, int yPosition, Predicate<ItemStack> stackPredicate) {
		super(inventoryIn, index, xPosition, yPosition);
		this.stackPredicate = stackPredicate;
	}

	@Override
	public boolean mayPlace(@Nonnull ItemStack stack) {
		return this.stackPredicate.test(stack);
	}
}
