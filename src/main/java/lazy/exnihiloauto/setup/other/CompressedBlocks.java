package lazy.exnihiloauto.setup.other;

import lazy.exnihiloauto.block.CompressedBlock;
import lazy.exnihiloauto.utils.BlockItemRegObj;
import lazy.exnihiloauto.utils.CompressionTier;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import novamachina.exnihilosequentia.common.init.ExNihiloBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("FieldCanBeLocal")
public class CompressedBlocks {

    public static List<RegistryObject<CompressedBlock>> COMPRESSED_BLOCKS = new ArrayList<>();

    private static BlockItemRegObj<CompressedBlock> COMPRESSED_COBBLE, HIGHLY_COMPRESSED_COBBLE, ATOMIC_COMPRESSION_COBBLE;
    private static BlockItemRegObj<CompressedBlock> COMPRESSED_GRAVEL, HIGHLY_COMPRESSED_GRAVEL, ATOMIC_COMPRESSION_GRAVEL;
    private static BlockItemRegObj<CompressedBlock> COMPRESSED_SAND, HIGHLY_COMPRESSED_SAND, ATOMIC_COMPRESSION_SAND;
    private static BlockItemRegObj<CompressedBlock> COMPRESSED_DUST, HIGHLY_COMPRESSED_DUST, ATOMIC_COMPRESSION_DUST;

    public static void init() {

        var cobbleName = new ItemStack(Blocks.COBBLESTONE).getDisplayName().getString();
        COMPRESSED_COBBLE = new BlockItemRegObj<>("compressed_cobble",
                () -> new CompressedBlock(Blocks.COBBLESTONE, cobbleName, CompressionTier.COMPRESSED));
        HIGHLY_COMPRESSED_COBBLE = new BlockItemRegObj<>("highly_compressed_cobble",
                () -> new CompressedBlock(COMPRESSED_COBBLE.getBlock().get(), cobbleName, CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSION_COBBLE = new BlockItemRegObj<>("atomic_compressed_cobble",
                () -> new CompressedBlock(HIGHLY_COMPRESSED_COBBLE.getBlock().get(), cobbleName, CompressionTier.ATOMIC_COMPRESSION));

        var gravelName = new ItemStack(Blocks.GRAVEL).getDisplayName().getString();
        COMPRESSED_GRAVEL = new BlockItemRegObj<>("compressed_gravel",
                () -> new CompressedBlock(Blocks.GRAVEL, gravelName, CompressionTier.COMPRESSED));
        HIGHLY_COMPRESSED_GRAVEL = new BlockItemRegObj<>("highly_compressed_gravel",
                () -> new CompressedBlock(COMPRESSED_GRAVEL.getBlock().get(), gravelName, CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSION_GRAVEL = new BlockItemRegObj<>("atomic_compressed_gravel",
                () -> new CompressedBlock(HIGHLY_COMPRESSED_GRAVEL.getBlock().get(), gravelName, CompressionTier.ATOMIC_COMPRESSION));

        var sandName = new ItemStack(Blocks.SAND).getDisplayName().getString();
        COMPRESSED_SAND = new BlockItemRegObj<>("compressed_sand",
                () -> new CompressedBlock(Blocks.SAND, sandName, CompressionTier.COMPRESSED));
        HIGHLY_COMPRESSED_SAND = new BlockItemRegObj<>("highly_compressed_sand",
                () -> new CompressedBlock(COMPRESSED_SAND.getBlock().get(), sandName, CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSION_SAND = new BlockItemRegObj<>("atomic_compressed_sand",
                () -> new CompressedBlock(HIGHLY_COMPRESSED_SAND.getBlock().get(), sandName, CompressionTier.ATOMIC_COMPRESSION));

        var dustName = "Dust";
        COMPRESSED_DUST = new BlockItemRegObj<>("compressed_dust",
                () -> new CompressedBlock(ExNihiloBlocks.DUST.get(), dustName, CompressionTier.COMPRESSED));
        HIGHLY_COMPRESSED_DUST = new BlockItemRegObj<>("highly_compressed_dust",
                () -> new CompressedBlock(COMPRESSED_DUST.getBlock().get(), dustName, CompressionTier.HIGHLY_COMPRESSED));
        ATOMIC_COMPRESSION_DUST = new BlockItemRegObj<>("atomic_compressed_dust",
                () -> new CompressedBlock(HIGHLY_COMPRESSED_DUST.getBlock().get(), dustName, CompressionTier.ATOMIC_COMPRESSION));

        COMPRESSED_BLOCKS.add(COMPRESSED_COBBLE.getBlock());
        COMPRESSED_BLOCKS.add(HIGHLY_COMPRESSED_COBBLE.getBlock());
        COMPRESSED_BLOCKS.add(ATOMIC_COMPRESSION_COBBLE.getBlock());
        COMPRESSED_BLOCKS.add(COMPRESSED_GRAVEL.getBlock());
        COMPRESSED_BLOCKS.add(HIGHLY_COMPRESSED_GRAVEL.getBlock());
        COMPRESSED_BLOCKS.add(ATOMIC_COMPRESSION_GRAVEL.getBlock());
        COMPRESSED_BLOCKS.add(COMPRESSED_SAND.getBlock());
        COMPRESSED_BLOCKS.add(HIGHLY_COMPRESSED_SAND.getBlock());
        COMPRESSED_BLOCKS.add(ATOMIC_COMPRESSION_SAND.getBlock());
        COMPRESSED_BLOCKS.add(COMPRESSED_DUST.getBlock());
        COMPRESSED_BLOCKS.add(HIGHLY_COMPRESSED_DUST.getBlock());
        COMPRESSED_BLOCKS.add(ATOMIC_COMPRESSION_DUST.getBlock());
    }

    public static Set<Block> getCompressedSet() {
        return COMPRESSED_BLOCKS.stream().map(RegistryObject::get).collect(Collectors.toSet());
    }
}
