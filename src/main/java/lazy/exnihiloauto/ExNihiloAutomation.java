package lazy.exnihiloauto;

import lazy.exnihiloauto.client.screen.AutoHammerScreen;
import lazy.exnihiloauto.client.screen.AutoSieveScreen;
import lazy.exnihiloauto.client.screen.AutoSilkerScreen;
import lazy.exnihiloauto.setup.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Ref.MOD_ID)
public class ExNihiloAutomation {

    public ExNihiloAutomation() {
        ModBlocks.init();
        ModItems.init();
        ModTiles.init();
        ModContainers.init();
        ModToolTypes.init();
        Configs.registerAndLoadConfig();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupClient(FMLClientSetupEvent event) {
        ScreenManager.register(ModContainers.AUTO_SIEVE.get(), AutoSieveScreen::new);
        ScreenManager.register(ModContainers.AUTO_HAMMER.get(), AutoHammerScreen::new);
        ScreenManager.register(ModContainers.AUTO_SILKER.get(), AutoSilkerScreen::new);
    }
}
