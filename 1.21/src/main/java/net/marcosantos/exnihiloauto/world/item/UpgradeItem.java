package net.marcosantos.exnihiloauto.world.item;

import java.util.List;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoBlockEntity;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoHammerBlockEntity;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoSilkerBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;

public abstract class UpgradeItem extends Item {

	public UpgradeItem() {
		super(new Properties().stacksTo(1));
	}

	public abstract boolean canApplyOn(Class<? extends AutoBlockEntity> toApply);

	public static class Speed extends UpgradeItem {
		@Override
		public boolean canApplyOn(Class<? extends AutoBlockEntity> toApply) {
			return true;
		}

		@Override
		@ParametersAreNonnullByDefault
		public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
			tooltipComponents.add(Component.translatable("upgrade.speed.effect"));
			tooltipComponents.add(Component.translatable("upgrade.speed.apply_to"));
		}
	}

	public static class Reinforcement extends UpgradeItem {

		@Override
		public boolean canApplyOn(Class<? extends AutoBlockEntity> toApply) {
			return toApply == AutoHammerBlockEntity.class;
		}

		@Override
		@ParametersAreNonnullByDefault
		public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
			tooltipComponents.add(Component.translatable("upgrade.reinforcement.effect"));
			tooltipComponents.add(Component.translatable("upgrade.reinforcement.apply_to"));
		}
	}

	public static class BonusDrop extends UpgradeItem {

		@Override
		public boolean canApplyOn(Class<? extends AutoBlockEntity> toApply) {
			return toApply == AutoHammerBlockEntity.class || toApply == AutoSilkerBlockEntity.class;
		}

		@Override
		@ParametersAreNonnullByDefault
		public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
			tooltipComponents.add(Component.translatable("upgrade.bonus.effect"));
			tooltipComponents.add(Component.translatable("upgrade.bonus.apply_to"));
		}
	}
}
