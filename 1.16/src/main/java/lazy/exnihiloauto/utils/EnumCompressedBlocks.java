package lazy.exnihiloauto.utils;

import lazy.exnihiloauto.block.compressed.CompressedBlock;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import novamachina.exnihilosequentia.common.init.ExNihiloBlocks;

import java.util.Arrays;

public enum EnumCompressedBlocks {

    COMPRESSED_COBBLE(CompressedBlocks.COMPRESSED_COBBLE.getBlock().get(), Blocks.COBBLESTONE),
    HIGHLY_COMPRESSED_COBBLE(CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock().get(), CompressedBlocks.COMPRESSED_COBBLE.getBlock().get()),
    ATOMIC_COMPRESSED_COBBLE(CompressedBlocks.ATOMIC_COMPRESSION_COBBLE.getBlock().get(), CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock().get()),
    COMPRESSED_GRAVEL(CompressedBlocks.COMPRESSED_GRAVEL.getBlock().get(), Blocks.GRAVEL),
    HIGHLY_COMPRESSED_GRAVEL(CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock().get(), CompressedBlocks.COMPRESSED_GRAVEL.getBlock().get()),
    ATOMIC_COMPRESSED_GRAVEL(CompressedBlocks.ATOMIC_COMPRESSION_GRAVEL.getBlock().get(), CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock().get()),
    COMPRESSED_SAND(CompressedBlocks.COMPRESSED_SAND.getBlock().get(), Blocks.SAND),
    HIGHLY_COMPRESSED_SAND(CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock().get(), CompressedBlocks.COMPRESSED_SAND.getBlock().get()),
    ATOMIC_COMPRESSED_SAND(CompressedBlocks.ATOMIC_COMPRESSION_SAND.getBlock().get(), CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock().get()),
    COMPRESSED_DUST(CompressedBlocks.COMPRESSED_DUST.getBlock().get(), ExNihiloBlocks.DUST.get()),
    HIGHLY_COMPRESSED_DUST(CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock().get(), CompressedBlocks.COMPRESSED_DUST.getBlock().get()),
    ATOMIC_COMPRESSED_DUST(CompressedBlocks.ATOMIC_COMPRESSION_DUST.getBlock().get(), CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock().get());

    public Block compressed;
    public Block actualBlock;

    EnumCompressedBlocks(Block actualBlock, Block compressed) {
        this.compressed = compressed;
        this.actualBlock = actualBlock;
    }

    public static Block getCompressed(CompressedBlock block){
        return Arrays.stream(values()).filter(v -> v.actualBlock == block).findFirst().orElseThrow(NullPointerException::new).compressed;
    }
}
