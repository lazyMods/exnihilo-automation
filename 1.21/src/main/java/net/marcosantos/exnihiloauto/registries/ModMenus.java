package net.marcosantos.exnihiloauto.registries;

import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.world.inventory.AutoHammerMenu;
import net.marcosantos.exnihiloauto.world.inventory.AutoSieveMenu;
import net.marcosantos.exnihiloauto.world.inventory.AutoSilkerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

	public static final DeferredRegister<MenuType<?>> MENUS =
			DeferredRegister.create(Registries.MENU, ExNihiloAuto.MODID);

	public static final DeferredHolder<MenuType<?>, MenuType<AutoSieveMenu>> AUTO_SIEVE =
			MENUS.register("auto_sieve", () -> IMenuTypeExtension.create((windowId, inv, data) -> new AutoSieveMenu(windowId, inv)));

	public static final DeferredHolder<MenuType<?>, MenuType<AutoHammerMenu>> AUTO_HAMMER =
			MENUS.register("auto_hammer", () -> IMenuTypeExtension.create((windowId, inv, data) -> new AutoHammerMenu(windowId, inv)));

	public static final DeferredHolder<MenuType<?>, MenuType<AutoSilkerMenu>> AUTO_SILKER =
			MENUS.register("auto_silker", () -> IMenuTypeExtension.create((windowId, inv, data) -> new AutoSilkerMenu(windowId, inv)));

	public static void init(IEventBus bus) {
		MENUS.register(bus);
	}
}
