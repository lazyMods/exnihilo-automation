package net.marcosantos.exnihiloauto.utils;

import java.util.Arrays;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.world.level.block.CompressedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import novamachina.exnihilosequentia.common.init.ExNihiloBlocks;

public enum EnumCompressedBlocks {
	COMPRESSED_COBBLE(ModBlocks.COMPRESSED_COBBLE.get(), Blocks.COBBLESTONE),
	HIGHLY_COMPRESSED_COBBLE(ModBlocks.HIGHLY_COMPRESSED_COBBLE.get(), ModBlocks.COMPRESSED_COBBLE.get()),
	ATOMIC_COMPRESSED_COBBLE(ModBlocks.ATOMIC_COMPRESSED_COBBLE.get(), ModBlocks.HIGHLY_COMPRESSED_COBBLE.get()),
	COMPRESSED_GRAVEL(ModBlocks.COMPRESSED_GRAVEL.get(), Blocks.GRAVEL), HIGHLY_COMPRESSED_GRAVEL(ModBlocks.HIGHLY_COMPRESSED_GRAVEL.get(), ModBlocks.COMPRESSED_GRAVEL.get()),
	ATOMIC_COMPRESSED_GRAVEL(ModBlocks.ATOMIC_COMPRESSED_GRAVEL.get(), ModBlocks.HIGHLY_COMPRESSED_GRAVEL.get()),
	COMPRESSED_SAND(ModBlocks.COMPRESSED_SAND.get(), Blocks.SAND),
	HIGHLY_COMPRESSED_SAND(ModBlocks.HIGHLY_COMPRESSED_SAND.get(), ModBlocks.COMPRESSED_SAND.get()),
	ATOMIC_COMPRESSED_SAND(ModBlocks.ATOMIC_COMPRESSED_SAND.get(), ModBlocks.HIGHLY_COMPRESSED_SAND.get()),
	COMPRESSED_DUST(ModBlocks.COMPRESSED_DUST.get(), ExNihiloBlocks.DUST.get()),
	HIGHLY_COMPRESSED_DUST(ModBlocks.HIGHLY_COMPRESSED_DUST.get(), ModBlocks.COMPRESSED_DUST.get()),
	ATOMIC_COMPRESSED_DUST(ModBlocks.ATOMIC_COMPRESSED_DUST.get(), ModBlocks.HIGHLY_COMPRESSED_DUST.get());

	public final Block compressed;
	public final Block actualBlock;

	EnumCompressedBlocks(Block actualBlock, Block compressed) {
		this.compressed = compressed;
		this.actualBlock = actualBlock;
	}

	public static Block getCompressed(CompressedBlock block) {
		return Arrays.stream(values())
				.filter(v -> v.actualBlock == block)
				.findFirst()
				.orElseThrow(NullPointerException::new).compressed;
	}
}
