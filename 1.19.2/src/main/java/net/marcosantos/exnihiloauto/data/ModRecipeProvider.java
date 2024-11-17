package net.marcosantos.exnihiloauto.data;

import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.world.level.block.CompressedBlock;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;
import novamachina.exnihilosequentia.api.tag.ExNihiloTags;
import novamachina.exnihilosequentia.common.crafting.hammer.HammerRecipeBuilder;
import novamachina.exnihilosequentia.common.init.ExNihiloBlocks;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;
import novamachina.exnihilosequentia.common.utility.ExNihiloConstants;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> output) {
		ShapedRecipeBuilder.shaped(ModBlocks.AUTO_SIEVE.get())
				.pattern("iri")
				.pattern("rsr")
				.pattern("iri")
				.define('r', Tags.Items.DUSTS_REDSTONE)
				.define('s', ExNihiloTags.SIEVE)
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						ExNihiloItems.SIEVE_OAK.get())
				)
				.save(output);
		ShapedRecipeBuilder.shaped(ModBlocks.AUTO_HAMMER.get())
				.pattern("iri")
				.pattern("rhr")
				.pattern("iri")
				.define('r', Tags.Items.DUSTS_REDSTONE)
				.define('h', ExNihiloItems.HAMMER_DIAMOND.get())
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						ExNihiloItems.HAMMER_STONE.get())
				)
				.save(output);

		ShapedRecipeBuilder.shaped(ModBlocks.AUTO_SILKER.get())
				.pattern("iri")
				.pattern("rcr")
				.pattern("iri")
				.define('r', Tags.Items.DUSTS_REDSTONE)
				.define('c', ExNihiloItems.CROOK_DIAMOND.get())
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						ExNihiloItems.CROOK_STONE.get())
				)
				.save(output);

		ShapedRecipeBuilder.shaped(ModItems.SPEED_UPGRADE.get())
				.pattern("iri")
				.pattern("rcr")
				.pattern("iri")
				.define('r', Items.SUGAR)
				.define('c', Tags.Items.DUSTS_REDSTONE)
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						Items.SUGAR)
				)
				.save(output);

		ShapedRecipeBuilder.shaped(ModItems.REINFORCED_UPGRADE.get())
				.pattern("iri")
				.pattern("rcr")
				.pattern("iri")
				.define('r', Items.OBSIDIAN)
				.define('c', Tags.Items.DUSTS_REDSTONE)
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						Items.OBSIDIAN)
				)
				.save(output);

		ShapedRecipeBuilder.shaped(ModItems.BONUS_UPGRADE.get())
				.pattern("iri")
				.pattern("rcr")
				.pattern("iri")
				.define('r', Items.EMERALD)
				.define('c', Tags.Items.DUSTS_REDSTONE)
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						Items.EMERALD)
				)
				.save(output);

		this.createCompressedBlockRecipe(output);

		this.createCrushingRecipes(output);
	}

	private void createCrushingRecipes(Consumer<FinishedRecipe> c) {
		this.createCrushingRecipe(ModBlocks.COMPRESSED_COBBLE, Blocks.COBBLESTONE, c);
		this.createCrushingRecipe(ModBlocks.HIGHLY_COMPRESSED_COBBLE, Blocks.COBBLESTONE, c);
		this.createCrushingRecipe(ModBlocks.ATOMIC_COMPRESSED_COBBLE, Blocks.COBBLESTONE, c);
		this.createCrushingRecipe(ModBlocks.COMPRESSED_GRAVEL, Blocks.GRAVEL, c);
		this.createCrushingRecipe(ModBlocks.HIGHLY_COMPRESSED_GRAVEL, Blocks.GRAVEL, c);
		this.createCrushingRecipe(ModBlocks.ATOMIC_COMPRESSED_GRAVEL, Blocks.GRAVEL, c);
		this.createCrushingRecipe(ModBlocks.COMPRESSED_SAND, Blocks.SAND, c);
		this.createCrushingRecipe(ModBlocks.HIGHLY_COMPRESSED_SAND, Blocks.SAND, c);
		this.createCrushingRecipe(ModBlocks.ATOMIC_COMPRESSED_SAND, Blocks.SAND, c);
		this.createCrushingRecipe(ModBlocks.COMPRESSED_DUST, ExNihiloBlocks.DUST.get(), c);
		this.createCrushingRecipe(ModBlocks.HIGHLY_COMPRESSED_DUST, ExNihiloBlocks.DUST.get(), c);
		this.createCrushingRecipe(ModBlocks.ATOMIC_COMPRESSED_DUST, ExNihiloBlocks.DUST.get(), c);
	}

	private ResourceLocation crushingLoc(@Nonnull final String id) {
		return new ResourceLocation(
				ExNihiloConstants.ModIds.EX_NIHILO_SEQUENTIA, "crushing/ens_" + id);
	}

	private void createCompressedBlockRecipe(Consumer<FinishedRecipe> c) {
		this.compressedBlockRecipes(ModBlocks.COMPRESSED_COBBLE, Blocks.COBBLESTONE, c);
		this.compressedBlockRecipes(ModBlocks.HIGHLY_COMPRESSED_COBBLE, ModBlocks.COMPRESSED_COBBLE.get(), c);
		this.compressedBlockRecipes(ModBlocks.ATOMIC_COMPRESSED_COBBLE, ModBlocks.HIGHLY_COMPRESSED_COBBLE.get(), c);
		this.compressedBlockRecipes(ModBlocks.COMPRESSED_GRAVEL, Blocks.GRAVEL, c);
		this.compressedBlockRecipes(ModBlocks.HIGHLY_COMPRESSED_GRAVEL, ModBlocks.COMPRESSED_GRAVEL.get(), c);
		this.compressedBlockRecipes(ModBlocks.ATOMIC_COMPRESSED_GRAVEL, ModBlocks.HIGHLY_COMPRESSED_GRAVEL.get(), c);
		this.compressedBlockRecipes(ModBlocks.COMPRESSED_SAND, Blocks.SAND, c);
		this.compressedBlockRecipes(ModBlocks.HIGHLY_COMPRESSED_SAND, ModBlocks.COMPRESSED_SAND.get(), c);
		this.compressedBlockRecipes(ModBlocks.ATOMIC_COMPRESSED_SAND, ModBlocks.HIGHLY_COMPRESSED_SAND.get(), c);
		this.compressedBlockRecipes(ModBlocks.COMPRESSED_DUST, ExNihiloBlocks.DUST.get(), c);
		this.compressedBlockRecipes(ModBlocks.HIGHLY_COMPRESSED_DUST, ModBlocks.COMPRESSED_DUST.get(), c);
		this.compressedBlockRecipes(ModBlocks.ATOMIC_COMPRESSED_DUST, ModBlocks.HIGHLY_COMPRESSED_DUST.get(), c);
	}

	private void compressedBlockRecipes(RegistryObject<CompressedBlock> block, Block compressed, Consumer<FinishedRecipe> consumer) {
		var compressedBlock = block.get();
		var path = block.getId().getPath();
		ShapedRecipeBuilder.shaped(compressedBlock).pattern("aaa").pattern("aaa").pattern("aaa")
				.define('a', compressed)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(compressed))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(compressed, 9)
				.requires(compressedBlock)
				.unlockedBy("can", InventoryChangeTrigger.TriggerInstance.hasItems(compressedBlock))
				.save(consumer, "decompress_".concat(path));
	}

	private void createCrushingRecipe(RegistryObject<CompressedBlock> block, Block result, Consumer<FinishedRecipe> output) {
		var builder = HammerRecipeBuilder.builder();
		builder.input(Ingredient.of(block.get()));
		switch (block.get().tier) {
		case COMPRESSED -> builder.addDrop(result, 9, 1);
		case HIGHLY_COMPRESSED -> builder.addDrop(result, 81, 1);
		case ATOMIC_COMPRESSION -> {
			for (int i = 0; i < 7; i++) {
				builder.addDrop(result, 99, 1);
			}
			builder.addDrop(result, 36, 1);
		}
		}
		builder.build(output, crushingLoc(block.getId().getPath()));
	}
}
