package net.marcosantos.exnihiloauto.registries;

import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.world.inventory.AutoHammerMenu;
import net.marcosantos.exnihiloauto.world.inventory.AutoSieveMenu;
import net.marcosantos.exnihiloauto.world.inventory.AutoSilkerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {

	public static final DeferredRegister<MenuType<?>> MENUS =
			DeferredRegister.create(ForgeRegistries.MENU_TYPES, ExNihiloAuto.MODID);

	public static final RegistryObject<MenuType<AutoSieveMenu>> AUTO_SIEVE =
			MENUS.register("auto_sieve", () -> IForgeMenuType.create((windowId, inv, data) -> new AutoSieveMenu(windowId, inv)));

	public static final RegistryObject<MenuType<AutoHammerMenu>> AUTO_HAMMER =
			MENUS.register("auto_hammer", () -> IForgeMenuType.create((windowId, inv, data) -> new AutoHammerMenu(windowId, inv)));

	public static final RegistryObject<MenuType<AutoSilkerMenu>> AUTO_SILKER =
			MENUS.register("auto_silker", () -> IForgeMenuType.create((windowId, inv, data) -> new AutoSilkerMenu(windowId, inv)));

	public static void init(IEventBus bus) {
		MENUS.register(bus);
	}
}
