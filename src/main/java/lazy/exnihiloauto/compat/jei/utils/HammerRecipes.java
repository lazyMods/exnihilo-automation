package lazy.exnihiloauto.compat.jei.utils;

import lazy.exnihiloauto.setup.ModTags;
import lazy.exnihiloauto.utils.EnumCompressedBlocks;
import lombok.val;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import novamachina.exnihilosequentia.api.ExNihiloTags;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;

import java.util.ArrayList;
import java.util.List;

public class HammerRecipes {

    public static List<TwoToOneRecipe> getRecipes() {
        List<TwoToOneRecipe> recipes = new ArrayList<>();

        for (EnumCompressedBlocks value : EnumCompressedBlocks.values()) {
            recipes.add(fromCompressedBlock(value));
        }

        recipes.add(new TwoToOneRecipe(Ingredient.of(Items.COBBLESTONE), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(Items.GRAVEL)));
        recipes.add(new TwoToOneRecipe(Ingredient.of(Items.GRAVEL), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(Items.SAND)));
        recipes.add(new TwoToOneRecipe(Ingredient.of(Items.SAND), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ExNihiloItems.DUST.get())));

        return recipes;
    }

    private static TwoToOneRecipe fromCompressedBlock(EnumCompressedBlocks compressedBlocks) {
        val compressed = compressedBlocks.compressed.getBlock();
        val actual = compressedBlocks.actualBlock.getBlock();
        return new TwoToOneRecipe(Ingredient.of(ModTags.REINFORCED_HAMMERS), Ingredient.of(actual), Ingredient.of(new ItemStack(compressed, 9)));
    }
}
