package lazy.exnihiloauto.data.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

//Modified version of: https://github.com/McJty/YouTubeModding14/blob/06097eee7db535d55c6cbe0bb4a523b07335fa33/src/main/java/com/mcjty/mytutorial/datagen/BaseLootTableProvider.java#L23
public abstract class BaseLootTableProvider extends LootTableProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final Map<Block, LootTable.Builder> blockLootTables = new HashMap<>();
    private final DataGenerator generator;

    public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

    protected abstract void createTables();

    public void lootTableWithOneOfItself(String regName, RegistryObject<Block> block) {
        this.blockLootTables.put(block.get(), LootTable.builder().addLootPool(new LootPool.Builder().name(regName).addEntry(ItemLootEntry.builder(block.get()))));
    }

    @Override
    public void act(@Nonnull DirectoryCache cache) {
        this.createTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        blockLootTables.forEach((block, builder) -> tables.put(block.getLootTable(), builder.setParameterSet(LootParameterSets.BLOCK).build()));

        this.writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }
}