package net.marcosantos.exnihiloauto.registries;

import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.world.item.UpgradeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExNihiloAuto.MODID);

	public static final RegistryObject<BlockItem> AUTO_SIEVE = ITEMS.register("auto_sieve",
			() -> new BlockItem(ModBlocks.AUTO_SIEVE.get(), new Item.Properties().tab(ExNihiloAuto.TAB)));

	public static final RegistryObject<BlockItem> AUTO_HAMMER = ITEMS.register("auto_hammer",
			() -> new BlockItem(ModBlocks.AUTO_HAMMER.get(), new Item.Properties().tab(ExNihiloAuto.TAB)));

	public static final RegistryObject<BlockItem> AUTO_SILKER = ITEMS.register("auto_silker",
			() -> new BlockItem(ModBlocks.AUTO_SILKER.get(), new Item.Properties().tab(ExNihiloAuto.TAB)));

	public static final RegistryObject<BlockItem> COMPRESSED_COBBLE = ITEMS.register("compressed_cobble", () -> new BlockItem(ModBlocks.COMPRESSED_COBBLE.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));
	public static final RegistryObject<BlockItem> HIGHLY_COMPRESSED_COBBLE = ITEMS.register("highly_compressed_cobble", () -> new BlockItem(ModBlocks.HIGHLY_COMPRESSED_COBBLE.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));
	public static final RegistryObject<BlockItem> ATOMIC_COMPRESSED_COBBLE = ITEMS.register("atomic_compressed_cobble", () -> new BlockItem(ModBlocks.ATOMIC_COMPRESSED_COBBLE.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));

	public static final RegistryObject<BlockItem> COMPRESSED_DUST = ITEMS.register("compressed_dust", () -> new BlockItem(ModBlocks.COMPRESSED_DUST.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));
	public static final RegistryObject<BlockItem> HIGHLY_COMPRESSED_DUST = ITEMS.register("highly_compressed_dust", () -> new BlockItem(ModBlocks.HIGHLY_COMPRESSED_DUST.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));
	public static final RegistryObject<BlockItem> ATOMIC_COMPRESSED_DUST = ITEMS.register("atomic_compressed_dust", () -> new BlockItem(ModBlocks.ATOMIC_COMPRESSED_DUST.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));

	public static final RegistryObject<BlockItem> COMPRESSED_GRAVEL = ITEMS.register("compressed_gravel", () -> new BlockItem(ModBlocks.COMPRESSED_GRAVEL.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));
	public static final RegistryObject<BlockItem> HIGHLY_COMPRESSED_GRAVEL = ITEMS.register("highly_compressed_gravel", () -> new BlockItem(ModBlocks.HIGHLY_COMPRESSED_GRAVEL.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));
	public static final RegistryObject<BlockItem> ATOMIC_COMPRESSED_GRAVEL = ITEMS.register("atomic_compressed_gravel", () -> new BlockItem(ModBlocks.ATOMIC_COMPRESSED_GRAVEL.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));

	public static final RegistryObject<BlockItem> COMPRESSED_SAND = ITEMS.register("compressed_sand", () -> new BlockItem(ModBlocks.COMPRESSED_SAND.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));
	public static final RegistryObject<BlockItem> HIGHLY_COMPRESSED_SAND = ITEMS.register("highly_compressed_sand", () -> new BlockItem(ModBlocks.HIGHLY_COMPRESSED_SAND.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));
	public static final RegistryObject<BlockItem> ATOMIC_COMPRESSED_SAND = ITEMS.register("atomic_compressed_sand", () -> new BlockItem(ModBlocks.ATOMIC_COMPRESSED_SAND.get(), new Item.Properties().stacksTo(64).tab(ExNihiloAuto.TAB)));

	public static final RegistryObject<Item> REINFORCED_UPGRADE = ITEMS.register("reinforcement_upgrade", UpgradeItem.Reinforcement::new);
	public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", UpgradeItem.Speed::new);
	public static final RegistryObject<Item> BONUS_UPGRADE = ITEMS.register("bonus_upgrade", UpgradeItem.BonusDrop::new);

	public static void init(IEventBus bus) {
		ITEMS.register(bus);
	}
}
