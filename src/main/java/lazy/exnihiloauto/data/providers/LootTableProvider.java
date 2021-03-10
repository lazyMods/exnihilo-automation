package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.block.CompressedBlock;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import lombok.var;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

public class LootTableProvider extends BaseLootTableProvider {

    public LootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void createTables() {
        this.lootTableWithOneOfItself("auto_hammer", ModBlocks.AUTO_HAMMER);
        this.lootTableWithOneOfItself("auto_sieve", ModBlocks.AUTO_SIEVE);
        for (RegistryObject<CompressedBlock> compressedBlock : CompressedBlocks.COMPRESSED_BLOCKS) {
            var registryName = Objects.requireNonNull(compressedBlock.get().getRegistryName());
            this.compressedBlockTables(registryName.toString(), compressedBlock, compressedBlock.get().getCompressedBlock());
        }
    }
}
