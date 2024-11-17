package net.marcosantos.exnihiloauto.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import novamachina.novacore.data.loot.LootProvider;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModDataGen {

	public static class ModLootProvider extends LootProvider {
		protected ModLootProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
			super(output, List.of(new SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK)), provider);
		}
	}

	@SubscribeEvent
	static void gatherData(GatherDataEvent event) {
		var generator = event.getGenerator();
		var pack = generator.getPackOutput();
		var exFile = event.getExistingFileHelper();
		var lookup = event.getLookupProvider();

		generator.addProvider(event.includeServer(), new ModRecipeProvider(pack, lookup));
		generator.addProvider(event.includeServer(), new ModLootProvider(pack, lookup));
		generator.addProvider(event.includeServer(), new ModTags(pack, Registries.BLOCK, lookup, exFile));

		generator.addProvider(event.includeClient(), new ModItemModelProvider(pack, exFile));
		generator.addProvider(event.includeClient(), new ModBlockStateProvider(pack, exFile));
		generator.addProvider(event.includeClient(), new ModLanguageProvider(pack));
	}
}
