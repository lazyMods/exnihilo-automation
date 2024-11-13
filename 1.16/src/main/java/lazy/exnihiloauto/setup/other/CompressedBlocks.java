package lazy.exnihiloauto.setup.other;

import lazy.exnihiloauto.block.compressed.*;
import lazy.exnihiloauto.utils.BlockItemRegObj;
import lazy.exnihiloauto.utils.CompressionTier;

@SuppressWarnings("FieldCanBeLocal")
public class CompressedBlocks {
    public static BlockItemRegObj<CompressedBlock> COMPRESSED_COBBLE, HIGHLY_COMPRESSED_COBBLE, ATOMIC_COMPRESSION_COBBLE;
    public static BlockItemRegObj<CompressedBlock> COMPRESSED_GRAVEL, HIGHLY_COMPRESSED_GRAVEL, ATOMIC_COMPRESSION_GRAVEL;
    public static BlockItemRegObj<CompressedBlock> COMPRESSED_SAND, HIGHLY_COMPRESSED_SAND, ATOMIC_COMPRESSION_SAND;
    public static BlockItemRegObj<CompressedBlock> COMPRESSED_DUST, HIGHLY_COMPRESSED_DUST, ATOMIC_COMPRESSION_DUST;

    public static void init() {
        COMPRESSED_COBBLE = new BlockItemRegObj<>("compressed_cobble", () -> new CompressedCobbleBlock(CompressionTier.COMPRESSED));
        HIGHLY_COMPRESSED_COBBLE = new BlockItemRegObj<>("highly_compressed_cobble", () -> new CompressedCobbleBlock(CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSION_COBBLE = new BlockItemRegObj<>("atomic_compressed_cobble", () -> new CompressedCobbleBlock(CompressionTier.ATOMIC_COMPRESSION));

        COMPRESSED_GRAVEL = new BlockItemRegObj<>("compressed_gravel", () -> new CompressedGravelBlock(CompressionTier.COMPRESSED));
        HIGHLY_COMPRESSED_GRAVEL = new BlockItemRegObj<>("highly_compressed_gravel", () -> new CompressedGravelBlock(CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSION_GRAVEL = new BlockItemRegObj<>("atomic_compressed_gravel", () -> new CompressedGravelBlock(CompressionTier.ATOMIC_COMPRESSION));

        COMPRESSED_SAND = new BlockItemRegObj<>("compressed_sand", () -> new CompressedSandBlock(CompressionTier.COMPRESSED));
        HIGHLY_COMPRESSED_SAND = new BlockItemRegObj<>("highly_compressed_sand", () -> new CompressedSandBlock(CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSION_SAND = new BlockItemRegObj<>("atomic_compressed_sand", () -> new CompressedSandBlock(CompressionTier.ATOMIC_COMPRESSION));

        COMPRESSED_DUST = new BlockItemRegObj<>("compressed_dust", () -> new CompressedDustBlock(CompressionTier.COMPRESSED));
        HIGHLY_COMPRESSED_DUST = new BlockItemRegObj<>("highly_compressed_dust", () -> new CompressedDustBlock(CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSION_DUST = new BlockItemRegObj<>("atomic_compressed_dust", () -> new CompressedDustBlock(CompressionTier.ATOMIC_COMPRESSION));
    }
}
