package net.marcosantos.exnihiloauto.data;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseLootTableProvider extends LootTableProvider {

	protected final Map<Block, LootTable.Builder> blockLootTables = new HashMap<>();
	private final DataGenerator generator;

	public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
		this.generator = dataGeneratorIn;
	}

	protected abstract void createTables();

	public <T extends Block> void lootTableWithOneOfItself(RegistryObject<T> block) {
		this.blockLootTables.put(block.get(), LootTable.lootTable().withPool(new LootPool.Builder().name(block.getId().toString()).add(LootItem.lootTableItem(block.get()))));
	}

	@Override
	public void run(@Nonnull CachedOutput cache) {
		this.createTables();

		Map<ResourceLocation, LootTable> tables = new HashMap<>();
		blockLootTables.forEach((block, builder) -> tables.put(block.getLootTable(), builder.setParamSet(LootContextParamSets.BLOCK).build()));

		this.writeTables(cache, tables);
	}

	private void writeTables(CachedOutput cache, Map<ResourceLocation, LootTable> tables) {
		Path outputFolder = this.generator.getOutputFolder();
		tables.forEach((key, lootTable) -> {
			Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
			try {
				DataProvider.saveStable(cache, LootTables.serialize(lootTable), path);
			} catch (IOException e) {
				System.err.printf("Couldn't write loot table %s %s\n", path, e.getMessage());
			}
		});
	}
}
