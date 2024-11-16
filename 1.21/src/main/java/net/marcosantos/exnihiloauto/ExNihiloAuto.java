package net.marcosantos.exnihiloauto;

import net.marcosantos.exnihiloauto.client.gui.screens.inventory.AutoSieveScreen;
import net.marcosantos.exnihiloauto.client.gui.screens.inventory.AutoSilkerScreen;
import net.marcosantos.exnihiloauto.client.gui.screens.inventory.AutoHammerScreen;
import net.marcosantos.exnihiloauto.registries.ModBlockEntities;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.registries.ModMenus;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.registries.DeferredRegister;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.function.Supplier;

@Mod(ExNihiloAuto.MODID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class ExNihiloAuto {

	public static boolean HAS_PATCHOULI = false;
	public static final String PATCHOULIID = "patchouli";
	public static final String MODID = "exnihiloauto";

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExNihiloAuto.MODID);
	public static final Supplier<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("exnihiloauto", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.exnihiloauto"))
			.icon(() -> new ItemStack(ModItems.AUTO_HAMMER.get()))
			.displayItems((params, output) -> {
				output.accept(ModItems.AUTO_HAMMER.get());
				output.accept(ModItems.AUTO_SIEVE.get());
				output.accept(ModItems.AUTO_SILKER.get());

				output.accept(ModItems.REINFORCED_UPGRADE.get());
				output.accept(ModItems.SPEED_UPGRADE.get());
				output.accept(ModItems.BONUS_UPGRADE.get());

				output.accept(ModItems.COMPRESSED_COBBLE.get());
				output.accept(ModItems.HIGHLY_COMPRESSED_COBBLE.get());
				output.accept(ModItems.ATOMIC_COMPRESSED_COBBLE.get());

				output.accept(ModItems.COMPRESSED_DUST.get());
				output.accept(ModItems.HIGHLY_COMPRESSED_DUST.get());
				output.accept(ModItems.ATOMIC_COMPRESSED_DUST.get());

				output.accept(ModItems.COMPRESSED_GRAVEL.get());
				output.accept(ModItems.HIGHLY_COMPRESSED_GRAVEL.get());
				output.accept(ModItems.ATOMIC_COMPRESSED_GRAVEL.get());

				output.accept(ModItems.COMPRESSED_SAND.get());
				output.accept(ModItems.HIGHLY_COMPRESSED_SAND.get());
				output.accept(ModItems.ATOMIC_COMPRESSED_SAND.get());
			}).build());

	public ExNihiloAuto(IEventBus modEventBus, ModContainer modContainer) {
		Configs.registerConfig(modContainer);
		ModBlocks.init(modEventBus);
		ModItems.init(modEventBus);
		ModMenus.init(modEventBus);
		ModBlockEntities.init(modEventBus);

		CREATIVE_MODE_TABS.register(modEventBus);

		modEventBus.addListener(this::registerScreen);
		modEventBus.addListener(this::registerCapabilities);
		modEventBus.addListener(this::completeLoad);

	}

	@SubscribeEvent()
	private static void advancementEvent(AdvancementEvent.AdvancementProgressEvent ev) {
		// hack: this is required due to new minecraft custom_data function in datapack
		if (HAS_PATCHOULI) {
			if (ev.getAdvancement().id().toString().equals(ExNihiloAuto.MODID + ":grant_book_on_first_join")) {
				ev.getEntity().getInventory().add(PatchouliAPI.get().getBookStack(ResourceLocation.fromNamespaceAndPath(MODID, "docs")));
			}
		}
	}

	private void completeLoad(FMLLoadCompleteEvent event) {
		HAS_PATCHOULI = ModList.get().isLoaded(ExNihiloAuto.PATCHOULIID);
	}

	private void registerScreen(RegisterMenuScreensEvent event) {
		event.register(ModMenus.AUTO_SIEVE.get(), AutoSieveScreen::new);
		event.register(ModMenus.AUTO_HAMMER.get(), AutoHammerScreen::new);
		event.register(ModMenus.AUTO_SILKER.get(), AutoSilkerScreen::new);
	}

	private void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
				Capabilities.ItemHandler.BLOCK,
				ModBlockEntities.AUTO_SIEVE.get(),
				(be, side) -> new SidedInvWrapper(be.tileInv, side));
		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
				ModBlockEntities.AUTO_SIEVE.get(),
				(be, side) -> be.storage);
		event.registerBlockEntity(
				Capabilities.ItemHandler.BLOCK,
				ModBlockEntities.AUTO_HAMMER.get(),
				(be, side) -> new SidedInvWrapper(be.tileInv, side));
		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
				ModBlockEntities.AUTO_HAMMER.get(),
				(be, side) -> be.storage);
		event.registerBlockEntity(
				Capabilities.ItemHandler.BLOCK,
				ModBlockEntities.AUTO_SILKER.get(),
				(be, side) -> new SidedInvWrapper(be.tileInv, side));
		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
				ModBlockEntities.AUTO_SILKER.get(),
				(be, side) -> be.storage);
	}
}
