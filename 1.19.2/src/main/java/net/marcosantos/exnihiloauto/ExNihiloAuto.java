package net.marcosantos.exnihiloauto;

import net.marcosantos.exnihiloauto.registries.ModBlockEntities;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.registries.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.marcosantos.exnihiloauto.client.gui.screens.inventory.AutoSieveScreen;
import net.marcosantos.exnihiloauto.client.gui.screens.inventory.AutoHammerScreen;
import net.marcosantos.exnihiloauto.client.gui.screens.inventory.AutoSilkerScreen;

import javax.annotation.Nonnull;

@Mod(ExNihiloAuto.MODID)
public class ExNihiloAuto {

	public static boolean HAS_PATCHOULI = false;
	public static final String PATCHOULIID = "patchouli";
	public static final String MODID = "exnihiloauto";

	public static final CreativeModeTab TAB = new CreativeModeTab("exnihiloauto") {
		@Override
		public @Nonnull ItemStack makeIcon() {
			return new ItemStack(ModItems.AUTO_HAMMER.get());
		}
	};

	public ExNihiloAuto() {
		var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		Configs.registerAndLoadConfig();

		ModBlocks.init(modEventBus);
		ModItems.init(modEventBus);
		ModMenus.init(modEventBus);
		ModBlockEntities.init(modEventBus);

		modEventBus.addListener(this::registerScreen);
		modEventBus.addListener(this::completeLoad);
	}

	private void completeLoad(FMLLoadCompleteEvent event) {
		HAS_PATCHOULI = ModList.get().isLoaded(ExNihiloAuto.PATCHOULIID);
	}

	void registerScreen(FMLClientSetupEvent event) {
		MenuScreens.register(ModMenus.AUTO_SIEVE.get(), AutoSieveScreen::new);
		MenuScreens.register(ModMenus.AUTO_HAMMER.get(), AutoHammerScreen::new);
		MenuScreens.register(ModMenus.AUTO_SILKER.get(), AutoSilkerScreen::new);
	}
}
