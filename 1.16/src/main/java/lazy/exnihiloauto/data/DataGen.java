package lazy.exnihiloauto.data;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.data.providers.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Ref.MOD_ID)
public class DataGen {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if (event.includeClient()) {
            generator.addProvider(new LangProvider(generator));
            generator.addProvider(new BlockStateProvider(generator, existingFileHelper));
            generator.addProvider(new ItemModelProvider(generator, existingFileHelper));

        }
        if(event.includeServer()){
            generator.addProvider(new LootTableProvider(generator));
            generator.addProvider(new RecipeProvider(generator));
        }
    }
}
