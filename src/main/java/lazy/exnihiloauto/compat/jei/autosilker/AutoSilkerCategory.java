package lazy.exnihiloauto.compat.jei.autosilker;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.setup.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class AutoSilkerCategory implements IRecipeCategory<AutoSilkerRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Ref.MOD_ID, "auto_silker");

    private final IDrawable background;
    private final IDrawable icon;

    public AutoSilkerCategory(IGuiHelper helper) {
        background = helper.createDrawable(new ResourceLocation(Ref.MOD_ID, "textures/gui/jei.png"), 0, 0, 98, 24);
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.AUTO_SILKER.get()));
    }

    @Override
    @Nonnull
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    @Nonnull
    public Class<? extends AutoSilkerRecipe> getRecipeClass() {
        return AutoSilkerRecipe.class;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return new TranslationTextComponent("block.exnihiloauto.auto_silker").getString();
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
    public void setIngredients(AutoSilkerRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Arrays.asList(recipe.primaryInput, recipe.secondaryInput));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.output.getItems()[0]);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AutoSilkerRecipe recipe, @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 0, 0);
        recipeLayout.getItemStacks().init(1, true, 44, 0);
        recipeLayout.getItemStacks().init(2, false, 80, 0);
        recipeLayout.getItemStacks().set(0, recipe.primaryInput.getItems()[0]);
        recipeLayout.getItemStacks().set(1, recipe.secondaryInput.getItems()[0]);
        recipeLayout.getItemStacks().set(2, recipe.output.getItems()[0]);
    }
}
