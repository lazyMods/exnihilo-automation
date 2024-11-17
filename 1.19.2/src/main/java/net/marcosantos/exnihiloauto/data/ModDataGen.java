package net.marcosantos.exnihiloauto.data;

import net.minecraft.core.Registry;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGen {

	@SubscribeEvent
	static void gatherData(GatherDataEvent event) {
		var generator = event.getGenerator();
		var exFile = event.getExistingFileHelper();

		generator.addProvider(event.includeServer(), new ModRecipeProvider(generator));
		generator.addProvider(event.includeServer(), new ModBlockLootProvider(generator));
		generator.addProvider(event.includeServer(), new ModTags(generator, Registry.BLOCK, exFile));

		generator.addProvider(event.includeClient(), new ModItemModelProvider(generator, exFile));
		generator.addProvider(event.includeClient(), new ModBlockStateProvider(generator, exFile));
		generator.addProvider(event.includeClient(), new ModLanguageProvider(generator));
	}
}
