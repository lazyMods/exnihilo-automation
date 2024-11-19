package net.marcosantos.exnihiloauto.compat.jei.category;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import novamachina.exnihilosequentia.common.compat.jei.sieve.AbstractSieveRecipeCategory;
import novamachina.exnihilosequentia.common.compat.jei.sieve.JEISieveRecipe;

import javax.annotation.Nonnull;

public class AutoSieveCategory extends AbstractSieveRecipeCategory {

	public static final RecipeType<JEISieveRecipe> RECIPE_TYPE = new RecipeType<>(new ResourceLocation(ExNihiloAuto.MODID, "auto_sieve"), JEISieveRecipe.class);

	private final IDrawable icon;

	public AutoSieveCategory(IGuiHelper helper) {
		super(helper, false);
		icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.AUTO_HAMMER.get()));
	}

	@Override
	@Nonnull
	public RecipeType<JEISieveRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	@Nonnull
	public Component getTitle() {
		return Component.translatable("block.exnihiloauto.auto_sieve");
	}
}
