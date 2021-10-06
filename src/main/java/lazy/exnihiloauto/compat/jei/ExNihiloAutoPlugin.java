package lazy.exnihiloauto.compat.jei;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.compat.jei.category.AutoHammerCategory;
import lazy.exnihiloauto.compat.jei.category.AutoSilkerCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class ExNihiloAutoPlugin implements IModPlugin {

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Ref.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AutoSilkerCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AutoHammerCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(AutoSilkerCategory.RECIPES, AutoSilkerCategory.UID);
        registration.addRecipes(AutoHammerCategory.RECIPES, AutoHammerCategory.UID);
    }
}
