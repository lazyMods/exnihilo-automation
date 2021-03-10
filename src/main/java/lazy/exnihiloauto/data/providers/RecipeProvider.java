package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.block.CompressedBlock;
import lazy.exnihiloauto.items.ReinforcedHammerItem;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import lazy.exnihiloauto.setup.other.ReinforcedHammers;
import lombok.var;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import novamachina.exnihilosequentia.common.init.ExNihiloBlocks;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;
import novamachina.exnihilosequentia.common.item.tools.hammer.EnumHammer;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.RecipeProvider {

    public RecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.AUTO_SIEVE.get())
                .patternLine("iri")
                .patternLine("rsr")
                .patternLine("iri")
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .key('s', ExNihiloBlocks.SIEVE.get())
                .key('i', Tags.Items.INGOTS_IRON)
                .addCriterion("canCraft", InventoryChangeTrigger.Instance.forItems(
                        Items.IRON_INGOT,
                        Items.REDSTONE,
                        ExNihiloItems.SIEVE.get())
                )
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.AUTO_HAMMER.get())
                .patternLine("iri")
                .patternLine("rhr")
                .patternLine("iri")
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .key('h', EnumHammer.DIAMOND.getRegistryObject().get())
                .key('i', Tags.Items.INGOTS_IRON)
                .addCriterion("canCraft", InventoryChangeTrigger.Instance.forItems(
                        Items.IRON_INGOT,
                        Items.REDSTONE,
                        EnumHammer.STONE.getRegistryObject().get())
                )
                .build(consumer);

        CompressedBlocks.COMPRESSED_BLOCKS.forEach(b -> this.compressedBlockRecipes(b, consumer));
        ReinforcedHammers.HAMMERS.forEach(itemObj -> this.createSmithingRecipe(itemObj.get().getBase(), itemObj, consumer));
    }

    private void compressedBlockRecipes(RegistryObject<CompressedBlock> block, Consumer<IFinishedRecipe> consumer) {
        var compressedBlock = block.get();
        ShapedRecipeBuilder.shapedRecipe(compressedBlock).patternLine("aaa").patternLine("aaa").patternLine("aaa")
                .key('a', compressedBlock.getCompressedBlock())
                .addCriterion("canCraft", InventoryChangeTrigger.Instance.forItems(compressedBlock.getCompressedBlock()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(compressedBlock.getCompressedBlock(), 9)
                .addIngredient(compressedBlock)
                .addCriterion("can", InventoryChangeTrigger.Instance.forItems(compressedBlock))
                .build(consumer, "decompress_".concat(Objects.requireNonNull(compressedBlock.getRegistryName(), "Compressed block is null!").getPath()));
    }

    private void createSmithingRecipe(EnumHammer baseHammer, RegistryObject<ReinforcedHammerItem> hammer, Consumer<IFinishedRecipe> consumer) {
        SmithingRecipeBuilder.smithingRecipe(
                Ingredient.fromItems(baseHammer.getRegistryObject().get()),
                Ingredient.fromTag(Tags.Items.OBSIDIAN),
                hammer.get())
                .addCriterion("has_diamond_hammer", InventoryChangeTrigger.Instance.forItems(baseHammer.getRegistryObject().get()))
                .build(consumer, this.modLoc(Objects.requireNonNull(hammer.get().getRegistryName()).getPath()));
    }

    private ResourceLocation modLoc(String path) {
        return new ResourceLocation(Ref.MOD_ID, path);
    }
}
