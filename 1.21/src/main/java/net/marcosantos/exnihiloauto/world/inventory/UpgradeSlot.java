package net.marcosantos.exnihiloauto.world.inventory;

import net.marcosantos.exnihiloauto.world.item.UpgradeItem;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class UpgradeSlot extends Slot {

	private final Class<? extends AutoBlockEntity> tileClass;

	public UpgradeSlot(Container inventoryIn, int index, int xPosition, int yPosition, Class<? extends AutoBlockEntity> tileClass) {
		super(inventoryIn, index, xPosition, yPosition);
		this.tileClass = tileClass;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return stack.getItem() instanceof UpgradeItem && ((UpgradeItem) stack.getItem()).canApplyOn(this.tileClass);
	}
}
