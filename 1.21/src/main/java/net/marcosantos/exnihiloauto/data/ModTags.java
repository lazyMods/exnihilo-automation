package net.marcosantos.exnihiloauto.data;

import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

public class ModTags extends TagsProvider<Block> {

    protected ModTags(PackOutput output, ResourceKey<? extends Registry<Block>> registryKey, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, registryKey, lookupProvider, ExNihiloAuto.MODID, existingFileHelper);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.COMPRESSED_COBBLE.getKey(),
                ModBlocks.HIGHLY_COMPRESSED_COBBLE.getKey(),
                ModBlocks.ATOMIC_COMPRESSED_COBBLE.getKey()
        );

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                ModBlocks.COMPRESSED_SAND.getKey(),
                ModBlocks.HIGHLY_COMPRESSED_SAND.getKey(),
                ModBlocks.ATOMIC_COMPRESSED_SAND.getKey(),

                ModBlocks.COMPRESSED_DUST.getKey(),
                ModBlocks.HIGHLY_COMPRESSED_DUST.getKey(),
                ModBlocks.ATOMIC_COMPRESSED_DUST.getKey(),

                ModBlocks.COMPRESSED_GRAVEL.getKey(),
                ModBlocks.HIGHLY_COMPRESSED_GRAVEL.getKey(),
                ModBlocks.ATOMIC_COMPRESSED_GRAVEL.getKey()
        );
    }
}
