package lazy.exnihiloauto.compat.jei.autosilker;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;

import java.util.Collections;
import java.util.List;

public class AutoSilkerRecipe {

    public final Ingredient primaryInput, secondaryInput, output;

    public static final List<AutoSilkerRecipe> RECIPES = Collections.singletonList(
            new AutoSilkerRecipe(Ingredient.of(ItemTags.LEAVES), Ingredient.of(ExNihiloItems.SILKWORM.get()), Ingredient.of(Tags.Items.STRING))
    );

    public AutoSilkerRecipe(Ingredient primaryInput, Ingredient secondaryInput, Ingredient output) {
        this.primaryInput = primaryInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
    }
}
