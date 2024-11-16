package net.marcosantos.exnihiloauto.data;

import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.world.level.block.CompressedBlock;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import novamachina.exnihilosequentia.ExNihiloSequentia;
import novamachina.exnihilosequentia.data.recipes.CrushingRecipeBuilder;
import novamachina.exnihilosequentia.tags.ExNihiloTags;
import novamachina.exnihilosequentia.world.item.EXNItems;
import novamachina.exnihilosequentia.world.level.block.EXNBlocks;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(@Nonnull RecipeOutput output) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AUTO_SIEVE.get())
				.pattern("iri")
				.pattern("rsr")
				.pattern("iri")
				.define('r', Tags.Items.DUSTS_REDSTONE)
				.define('s', ExNihiloTags.SIEVE)
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						EXNBlocks.OAK_SIEVE.asItem())
				)
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AUTO_HAMMER.get())
				.pattern("iri")
				.pattern("rhr")
				.pattern("iri")
				.define('r', Tags.Items.DUSTS_REDSTONE)
				.define('h', EXNItems.HAMMER_DIAMOND)
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						EXNItems.HAMMER_STONE.asItem())
				)
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AUTO_SILKER.get())
				.pattern("iri")
				.pattern("rcr")
				.pattern("iri")
				.define('r', Tags.Items.DUSTS_REDSTONE)
				.define('c', EXNItems.CROOK_DIAMOND)
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(
						Items.IRON_INGOT,
						Items.REDSTONE,
						EXNItems.CROOK_STONE.asItem())
				)
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPEED_UPGRADE.get())
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

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REINFORCED_UPGRADE.get())
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

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONUS_UPGRADE.get())
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

	private void createCrushingRecipes(RecipeOutput c) {
		this.createCrushingRecipe(ModBlocks.COMPRESSED_COBBLE, Blocks.COBBLESTONE, c);
		this.createCrushingRecipe(ModBlocks.HIGHLY_COMPRESSED_COBBLE, Blocks.COBBLESTONE, c);
		this.createCrushingRecipe(ModBlocks.ATOMIC_COMPRESSED_COBBLE, Blocks.COBBLESTONE, c);
		this.createCrushingRecipe(ModBlocks.COMPRESSED_GRAVEL, Blocks.GRAVEL, c);
		this.createCrushingRecipe(ModBlocks.HIGHLY_COMPRESSED_GRAVEL, Blocks.GRAVEL, c);
		this.createCrushingRecipe(ModBlocks.ATOMIC_COMPRESSED_GRAVEL, Blocks.GRAVEL, c);
		this.createCrushingRecipe(ModBlocks.COMPRESSED_SAND, Blocks.SAND, c);
		this.createCrushingRecipe(ModBlocks.HIGHLY_COMPRESSED_SAND, Blocks.SAND, c);
		this.createCrushingRecipe(ModBlocks.ATOMIC_COMPRESSED_SAND, Blocks.SAND, c);
		this.createCrushingRecipe(ModBlocks.COMPRESSED_DUST, EXNBlocks.DUST.block(), c);
		this.createCrushingRecipe(ModBlocks.HIGHLY_COMPRESSED_DUST, EXNBlocks.DUST.block(), c);
		this.createCrushingRecipe(ModBlocks.ATOMIC_COMPRESSED_DUST, EXNBlocks.DUST.block(), c);
	}

	private ResourceLocation crushingLoc(@Nonnull final String id) {
		return ResourceLocation.fromNamespaceAndPath(
				ExNihiloSequentia.MOD_ID, "crushing/ens_" + id);
	}

	private void createCompressedBlockRecipe(RecipeOutput c) {
		this.compressedBlockRecipes(ModBlocks.COMPRESSED_COBBLE, Blocks.COBBLESTONE, c);
		this.compressedBlockRecipes(ModBlocks.HIGHLY_COMPRESSED_COBBLE, ModBlocks.COMPRESSED_COBBLE.get(), c);
		this.compressedBlockRecipes(ModBlocks.ATOMIC_COMPRESSED_COBBLE, ModBlocks.HIGHLY_COMPRESSED_COBBLE.get(), c);
		this.compressedBlockRecipes(ModBlocks.COMPRESSED_GRAVEL, Blocks.GRAVEL, c);
		this.compressedBlockRecipes(ModBlocks.HIGHLY_COMPRESSED_GRAVEL, ModBlocks.COMPRESSED_GRAVEL.get(), c);
		this.compressedBlockRecipes(ModBlocks.ATOMIC_COMPRESSED_GRAVEL, ModBlocks.HIGHLY_COMPRESSED_GRAVEL.get(), c);
		this.compressedBlockRecipes(ModBlocks.COMPRESSED_SAND, Blocks.SAND, c);
		this.compressedBlockRecipes(ModBlocks.HIGHLY_COMPRESSED_SAND, ModBlocks.COMPRESSED_SAND.get(), c);
		this.compressedBlockRecipes(ModBlocks.ATOMIC_COMPRESSED_SAND, ModBlocks.HIGHLY_COMPRESSED_SAND.get(), c);
		this.compressedBlockRecipes(ModBlocks.COMPRESSED_DUST, EXNBlocks.DUST.block(), c);
		this.compressedBlockRecipes(ModBlocks.HIGHLY_COMPRESSED_DUST, ModBlocks.COMPRESSED_DUST.get(), c);
		this.compressedBlockRecipes(ModBlocks.ATOMIC_COMPRESSED_DUST, ModBlocks.HIGHLY_COMPRESSED_DUST.get(), c);
	}

	private void compressedBlockRecipes(DeferredBlock<CompressedBlock> block, Block compressed, RecipeOutput consumer) {
		var compressedBlock = block.get();
		var path = block.getId().getPath();
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, compressedBlock).pattern("aaa").pattern("aaa").pattern("aaa")
				.define('a', compressed)
				.unlockedBy("canCraft", InventoryChangeTrigger.TriggerInstance.hasItems(compressed))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, compressed, 9)
				.requires(compressedBlock)
				.unlockedBy("can", InventoryChangeTrigger.TriggerInstance.hasItems(compressedBlock))
				.save(consumer, "decompress_".concat(path));
	}

	private void createCrushingRecipe(DeferredBlock<CompressedBlock> block, Block result, RecipeOutput output) {
		var builder = CrushingRecipeBuilder.crushing(block);
		switch (block.get().tier) {
		case COMPRESSED -> builder.addDrop(new ItemStack(result), 9, 1);
		case HIGHLY_COMPRESSED -> builder.addDrop(new ItemStack(result), 81, 1);
		case ATOMIC_COMPRESSION -> {
			for (int i = 0; i < 7; i++) {
				builder.addDrop(new ItemStack(result), 99, 1);
			}
			builder.addDrop(new ItemStack(result), 36, 1);
		}
		}
		builder.build(output, crushingLoc(block.getId().getPath()));
	}
}
