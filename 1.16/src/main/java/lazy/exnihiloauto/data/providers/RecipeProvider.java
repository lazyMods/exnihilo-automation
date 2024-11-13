package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.block.compressed.CompressedBlock;
import lazy.exnihiloauto.items.ReinforcedHammerItem;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.ModItems;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import lazy.exnihiloauto.setup.other.ReinforcedHammers;
import lombok.var;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import novamachina.exnihilosequentia.api.ExNihiloTags;
import novamachina.exnihilosequentia.common.init.ExNihiloBlocks;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;
import novamachina.exnihilosequentia.common.item.tools.crook.EnumCrook;
import novamachina.exnihilosequentia.common.item.tools.hammer.EnumHammer;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.RecipeProvider {

    public RecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlocks.AUTO_SIEVE.get())
                .pattern("iri")
                .pattern("rsr")
                .pattern("iri")
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('s', ExNihiloTags.SIEVE)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("canCraft", InventoryChangeTrigger.Instance.hasItems(
                        Items.IRON_INGOT,
                        Items.REDSTONE,
                        ExNihiloItems.SIEVE_OAK.get())
                )
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.AUTO_HAMMER.get())
                .pattern("iri")
                .pattern("rhr")
                .pattern("iri")
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('h', EnumHammer.DIAMOND.getRegistryObject().get())
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("canCraft", InventoryChangeTrigger.Instance.hasItems(
                        Items.IRON_INGOT,
                        Items.REDSTONE,
                        EnumHammer.STONE.getRegistryObject().get())
                )
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.AUTO_SILKER.get())
                .pattern("iri")
                .pattern("rcr")
                .pattern("iri")
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('c', EnumCrook.DIAMOND.getRegistryObject().get())
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("canCraft", InventoryChangeTrigger.Instance.hasItems(
                        Items.IRON_INGOT,
                        Items.REDSTONE,
                        EnumCrook.STONE.getRegistryObject().get())
                )
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.SPEED_UPGRADE.get())
                .pattern("iri")
                .pattern("rcr")
                .pattern("iri")
                .define('r', Items.SUGAR)
                .define('c', Tags.Items.DUSTS_REDSTONE)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("canCraft", InventoryChangeTrigger.Instance.hasItems(
                        Items.IRON_INGOT,
                        Items.REDSTONE,
                        Items.SUGAR)
                )
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.REINFORCED_UPGRADE.get())
                .pattern("iri")
                .pattern("rcr")
                .pattern("iri")
                .define('r', Items.OBSIDIAN)
                .define('c', Tags.Items.DUSTS_REDSTONE)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("canCraft", InventoryChangeTrigger.Instance.hasItems(
                        Items.IRON_INGOT,
                        Items.REDSTONE,
                        Items.OBSIDIAN)
                )
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.BONUS_UPGRADE.get())
                .pattern("iri")
                .pattern("rcr")
                .pattern("iri")
                .define('r', Items.EMERALD)
                .define('c', Tags.Items.DUSTS_REDSTONE)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("canCraft", InventoryChangeTrigger.Instance.hasItems(
                        Items.IRON_INGOT,
                        Items.REDSTONE,
                        Items.EMERALD)
                )
                .save(consumer);


        this.createCompressedBlockRecipe(consumer);
        ReinforcedHammers.HAMMERS.forEach(itemObj -> this.createSmithingRecipe(itemObj.get().getBase(), itemObj, consumer));
    }


    private void createCompressedBlockRecipe(Consumer<IFinishedRecipe> c) {
        this.compressedBlockRecipes(CompressedBlocks.COMPRESSED_COBBLE.getBlock(), Blocks.COBBLESTONE, c);
        this.compressedBlockRecipes(CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock(), CompressedBlocks.COMPRESSED_COBBLE.getBlock().get(), c);
        this.compressedBlockRecipes(CompressedBlocks.ATOMIC_COMPRESSION_COBBLE.getBlock(), CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock().get(), c);
        this.compressedBlockRecipes(CompressedBlocks.COMPRESSED_GRAVEL.getBlock(), Blocks.GRAVEL, c);
        this.compressedBlockRecipes(CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock(), CompressedBlocks.COMPRESSED_GRAVEL.getBlock().get(), c);
        this.compressedBlockRecipes(CompressedBlocks.ATOMIC_COMPRESSION_GRAVEL.getBlock(), CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock().get(), c);
        this.compressedBlockRecipes(CompressedBlocks.COMPRESSED_SAND.getBlock(), Blocks.SAND, c);
        this.compressedBlockRecipes(CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock(), CompressedBlocks.COMPRESSED_SAND.getBlock().get(), c);
        this.compressedBlockRecipes(CompressedBlocks.ATOMIC_COMPRESSION_SAND.getBlock(), CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock().get(), c);
        this.compressedBlockRecipes(CompressedBlocks.COMPRESSED_DUST.getBlock(), ExNihiloBlocks.DUST.get(), c);
        this.compressedBlockRecipes(CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock(), CompressedBlocks.COMPRESSED_DUST.getBlock().get(), c);
        this.compressedBlockRecipes(CompressedBlocks.ATOMIC_COMPRESSION_DUST.getBlock(), CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock().get(), c);
    }

    private void compressedBlockRecipes(RegistryObject<CompressedBlock> block, Block compressed, Consumer<IFinishedRecipe> consumer) {
        var compressedBlock = block.get();
        ShapedRecipeBuilder.shaped(compressedBlock).pattern("aaa").pattern("aaa").pattern("aaa")
                .define('a', compressed)
                .unlockedBy("canCraft", InventoryChangeTrigger.Instance.hasItems(compressed))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(compressed, 9)
                .requires(compressedBlock)
                .unlockedBy("can", InventoryChangeTrigger.Instance.hasItems(compressedBlock))
                .save(consumer, "decompress_".concat(Objects.requireNonNull(compressedBlock.getRegistryName(), "Compressed block is null!").getPath()));
    }

    private void createSmithingRecipe(EnumHammer baseHammer, RegistryObject<ReinforcedHammerItem> hammer, Consumer<IFinishedRecipe> consumer) {
        SmithingRecipeBuilder.smithing(
                Ingredient.of(baseHammer.getRegistryObject().get()),
                Ingredient.of(Tags.Items.OBSIDIAN),
                hammer.get())
                .unlocks("has_diamond_hammer", InventoryChangeTrigger.Instance.hasItems(baseHammer.getRegistryObject().get()))
                .save(consumer, this.modLoc(Objects.requireNonNull(hammer.get().getRegistryName()).getPath()));
    }

    private ResourceLocation modLoc(String path) {
        return new ResourceLocation(Ref.MOD_ID, path);
    }
}
