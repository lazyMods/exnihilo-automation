package net.marcosantos.exnihiloauto.data;

import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.minecraft.data.DataGenerator;

public class ModBlockLootProvider extends BaseLootTableProvider {

	public ModBlockLootProvider(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected void createTables() {
		this.lootTableWithOneOfItself(ModBlocks.AUTO_HAMMER);
		this.lootTableWithOneOfItself(ModBlocks.AUTO_SIEVE);
		this.lootTableWithOneOfItself(ModBlocks.AUTO_SILKER);

		lootTableWithOneOfItself(ModBlocks.COMPRESSED_COBBLE);
		lootTableWithOneOfItself(ModBlocks.HIGHLY_COMPRESSED_COBBLE);
		lootTableWithOneOfItself(ModBlocks.ATOMIC_COMPRESSED_COBBLE);
		lootTableWithOneOfItself(ModBlocks.COMPRESSED_GRAVEL);
		lootTableWithOneOfItself(ModBlocks.HIGHLY_COMPRESSED_GRAVEL);
		lootTableWithOneOfItself(ModBlocks.ATOMIC_COMPRESSED_GRAVEL);
		lootTableWithOneOfItself(ModBlocks.COMPRESSED_SAND);
		lootTableWithOneOfItself(ModBlocks.HIGHLY_COMPRESSED_SAND);
		lootTableWithOneOfItself(ModBlocks.ATOMIC_COMPRESSED_SAND);
		lootTableWithOneOfItself(ModBlocks.COMPRESSED_DUST);
		lootTableWithOneOfItself(ModBlocks.HIGHLY_COMPRESSED_DUST);
		lootTableWithOneOfItself(ModBlocks.ATOMIC_COMPRESSED_DUST);
	}
}
