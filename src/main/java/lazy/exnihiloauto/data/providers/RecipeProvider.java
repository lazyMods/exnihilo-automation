package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.setup.ModBlocks;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import novamachina.exnihilosequentia.api.ExNihiloRegistries;
import novamachina.exnihilosequentia.api.ExNihiloTags;
import novamachina.exnihilosequentia.common.init.ExNihiloBlocks;
import novamachina.exnihilosequentia.common.init.ExNihiloItems;
import novamachina.exnihilosequentia.common.item.tools.hammer.EnumHammer;

import javax.annotation.Nonnull;
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
    }
}
