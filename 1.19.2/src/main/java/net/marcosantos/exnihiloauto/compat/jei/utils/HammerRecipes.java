package net.marcosantos.exnihiloauto.compat.jei.utils;

import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import novamachina.exnihilosequentia.api.tag.ExNihiloTags;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;

import java.util.ArrayList;
import java.util.List;

public class HammerRecipes {

	public static List<TwoToOneRecipe> getRecipes() {
		List<TwoToOneRecipe> recipes = new ArrayList<>();

		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.COBBLESTONE), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(Items.GRAVEL)));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.GRAVEL), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(Items.SAND)));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.SAND), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ExNihiloItems.DUST.get())));

		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.COBBLESTONE), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.COMPRESSED_COBBLE.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.COBBLESTONE), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.HIGHLY_COMPRESSED_COBBLE.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.COBBLESTONE), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.ATOMIC_COMPRESSED_COBBLE.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.GRAVEL), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.COMPRESSED_GRAVEL.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.GRAVEL), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.HIGHLY_COMPRESSED_GRAVEL.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.GRAVEL), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.ATOMIC_COMPRESSED_GRAVEL.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.SAND), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.COMPRESSED_SAND.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.SAND), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.HIGHLY_COMPRESSED_SAND.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(Items.SAND), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.ATOMIC_COMPRESSED_SAND.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(ExNihiloItems.DUST.get()), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.COMPRESSED_DUST.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(ExNihiloItems.DUST.get()), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.HIGHLY_COMPRESSED_DUST.get())));
		recipes.add(new TwoToOneRecipe(Ingredient.of(ExNihiloItems.DUST.get()), Ingredient.of(ExNihiloTags.HAMMER), Ingredient.of(ModBlocks.ATOMIC_COMPRESSED_DUST.get())));

		return recipes;
	}

}
