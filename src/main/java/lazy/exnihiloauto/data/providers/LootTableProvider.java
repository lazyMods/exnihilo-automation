package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.setup.ModBlocks;
import net.minecraft.data.DataGenerator;

public class LootTableProvider extends BaseLootTableProvider {

    public LootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void createTables() {
        this.lootTableWithOneOfItself("auto_hammer", ModBlocks.AUTO_HAMMER);
        this.lootTableWithOneOfItself("auto_sieve", ModBlocks.AUTO_SIEVE);
    }
}
