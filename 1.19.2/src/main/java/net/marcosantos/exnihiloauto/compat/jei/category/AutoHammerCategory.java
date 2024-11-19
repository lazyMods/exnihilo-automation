package net.marcosantos.exnihiloauto.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.compat.jei.utils.HammerRecipes;
import net.marcosantos.exnihiloauto.compat.jei.utils.TwoToOneRecipe;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class AutoHammerCategory implements IRecipeCategory<TwoToOneRecipe> {

	public static final RecipeType<TwoToOneRecipe> RECIPE_TYPE = new RecipeType<>(new ResourceLocation(ExNihiloAuto.MODID, "auto_hammer"), TwoToOneRecipe.class);
	public static final List<TwoToOneRecipe> RECIPES = HammerRecipes.getRecipes();

	private final IDrawable background;
	private final IDrawable icon;

	public AutoHammerCategory(IGuiHelper helper) {
		background = helper.createDrawable(new ResourceLocation(ExNihiloAuto.MODID, "textures/gui/jei.png"), 0, 0, 98, 24);
		icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.AUTO_HAMMER.get()));
	}

	@Override
	@Nonnull
	public RecipeType<TwoToOneRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

	@Override
	@Nonnull
	public Component getTitle() {
		return Component.translatable("block.exnihiloauto.auto_hammer");
	}

	@Override
	@Nonnull
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	@Nonnull
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, TwoToOneRecipe twoToOneRecipe, @Nonnull IFocusGroup iFocusGroup) {
		iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addItemStack(twoToOneRecipe.primaryInput.getItems()[0]);
		iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 45, 1).addItemStack(twoToOneRecipe.secondaryInput.getItems()[0]);
		iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 81, 1).addItemStack(twoToOneRecipe.output.getItems()[0]);
	}
}
