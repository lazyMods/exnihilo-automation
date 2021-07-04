package lazy.exnihiloauto.compat.jei.utils;

import net.minecraft.item.crafting.Ingredient;

// Two Input to one output recipe.
public class TwoToOneRecipe {

    public final Ingredient primaryInput, secondaryInput, output;

    public TwoToOneRecipe(Ingredient primaryInput, Ingredient secondaryInput, Ingredient output) {
        this.primaryInput = primaryInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
    }
}
