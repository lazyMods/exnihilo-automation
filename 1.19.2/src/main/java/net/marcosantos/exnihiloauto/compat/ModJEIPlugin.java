package net.marcosantos.exnihiloauto.compat;

import mezz.jei.api.JeiPlugin;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.compat.jei.category.AutoHammerCategory;
import net.marcosantos.exnihiloauto.compat.jei.category.AutoSieveCategory;
import net.marcosantos.exnihiloauto.compat.jei.category.AutoSilkerCategory;
import net.minecraft.resources.ResourceLocation;
import novamachina.exnihilosequentia.common.registries.ExNihiloRegistries;

import javax.annotation.Nonnull;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {

	@Override
	@Nonnull
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(ExNihiloAuto.MODID, "jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new AutoSilkerCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new AutoHammerCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new AutoSieveCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(AutoHammerCategory.RECIPE_TYPE, AutoHammerCategory.RECIPES);
		registration.addRecipes(AutoSilkerCategory.RECIPE_TYPE, AutoSilkerCategory.RECIPES);
		registration.addRecipes(AutoSieveCategory.RECIPE_TYPE, ExNihiloRegistries.SIEVE_REGISTRY.getDryRecipeList());
	}
}
