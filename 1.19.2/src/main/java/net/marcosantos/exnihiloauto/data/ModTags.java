package net.marcosantos.exnihiloauto.data;

import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModTags extends TagsProvider<Block> {

	protected ModTags(DataGenerator p_126546_, Registry<Block> p_126547_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_126546_, p_126547_, ExNihiloAuto.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
				ModBlocks.COMPRESSED_COBBLE.get(),
				ModBlocks.HIGHLY_COMPRESSED_COBBLE.get(),
				ModBlocks.ATOMIC_COMPRESSED_COBBLE.get()
		);

		tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
				ModBlocks.COMPRESSED_SAND.get(),
				ModBlocks.HIGHLY_COMPRESSED_SAND.get(),
				ModBlocks.ATOMIC_COMPRESSED_SAND.get(),

				ModBlocks.COMPRESSED_DUST.get(),
				ModBlocks.HIGHLY_COMPRESSED_DUST.get(),
				ModBlocks.ATOMIC_COMPRESSED_DUST.get(),

				ModBlocks.COMPRESSED_GRAVEL.get(),
				ModBlocks.HIGHLY_COMPRESSED_GRAVEL.get(),
				ModBlocks.ATOMIC_COMPRESSED_GRAVEL.get()
		);
	}
}
