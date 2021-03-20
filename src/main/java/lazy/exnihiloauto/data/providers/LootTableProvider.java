package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import novamachina.exnihilosequentia.common.init.ExNihiloBlocks;

public class LootTableProvider extends BaseLootTableProvider {

    public LootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void createTables() {
        this.lootTableWithOneOfItself("auto_hammer", ModBlocks.AUTO_HAMMER);
        this.lootTableWithOneOfItself("auto_sieve", ModBlocks.AUTO_SIEVE);
        this.lootTableWithOneOfItself("auto_silker", ModBlocks.AUTO_SILKER);
        this.createCompressedBlocksTables();
    }

    private void createCompressedBlocksTables() {
        this.compressedBlockTables(CompressedBlocks.COMPRESSED_COBBLE.getBlock(), Blocks.COBBLESTONE);
        this.compressedBlockTables(CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock(), Blocks.COBBLESTONE);
        this.compressedBlockTables(CompressedBlocks.ATOMIC_COMPRESSION_COBBLE.getBlock(), Blocks.COBBLESTONE);
        this.compressedBlockTables(CompressedBlocks.COMPRESSED_GRAVEL.getBlock(), Blocks.GRAVEL);
        this.compressedBlockTables(CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock(), Blocks.GRAVEL);
        this.compressedBlockTables(CompressedBlocks.ATOMIC_COMPRESSION_GRAVEL.getBlock(), Blocks.GRAVEL);
        this.compressedBlockTables(CompressedBlocks.COMPRESSED_SAND.getBlock(), Blocks.SAND);
        this.compressedBlockTables(CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock(), Blocks.SAND);
        this.compressedBlockTables(CompressedBlocks.ATOMIC_COMPRESSION_SAND.getBlock(), Blocks.SAND);
        this.compressedBlockTables(CompressedBlocks.COMPRESSED_DUST.getBlock(), ExNihiloBlocks.DUST.get());
        this.compressedBlockTables(CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock(), ExNihiloBlocks.DUST.get());
        this.compressedBlockTables(CompressedBlocks.ATOMIC_COMPRESSION_DUST.getBlock(), ExNihiloBlocks.DUST.get());
    }
}
