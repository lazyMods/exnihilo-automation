package lazy.exnihiloauto.setup;

import lazy.exnihiloauto.block.CompressedBlock;
import lazy.exnihiloauto.utils.BlockItemRegObj;
import lazy.exnihiloauto.utils.CompressionTier;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class CompressedBlocksSetup {

    public static List<RegistryObject<CompressedBlock>> COMPRESSED_BLOCKS = new ArrayList<>();

    public static BlockItemRegObj<CompressedBlock> COMPRESSED_COBBLE;
    public static BlockItemRegObj<CompressedBlock> HIGHLY_COBBLE;
    public static BlockItemRegObj<CompressedBlock> ATOMIC_COMPRESSED_COBBLE;

    public static void init() {
        COMPRESSED_COBBLE = new BlockItemRegObj<>("compressed_cobble", () -> new CompressedBlock(Blocks.COBBLESTONE, CompressionTier.COMPRESSED));
        HIGHLY_COBBLE = new BlockItemRegObj<>("highly_compressed_cobble", () -> new CompressedBlock(Blocks.COBBLESTONE, CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSED_COBBLE = new BlockItemRegObj<>("atomic_compressed_cobble", () -> new CompressedBlock(Blocks.COBBLESTONE, CompressionTier.ATOMIC_COMPRESSION));

        COMPRESSED_BLOCKS.add(COMPRESSED_COBBLE.getBlock());
        COMPRESSED_BLOCKS.add(HIGHLY_COBBLE.getBlock());
        COMPRESSED_BLOCKS.add(ATOMIC_COMPRESSED_COBBLE.getBlock());
    }

}
