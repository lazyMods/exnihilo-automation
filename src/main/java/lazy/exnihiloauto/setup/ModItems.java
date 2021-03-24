package lazy.exnihiloauto.setup;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.items.upgrades.BonusDropUpgradeItem;
import lazy.exnihiloauto.items.upgrades.ReinforcementUpgradeItem;
import lazy.exnihiloauto.items.upgrades.SpeedUpgradeItem;
import lazy.exnihiloauto.setup.other.ReinforcedHammers;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.MOD_ID);

    public static RegistryObject<Item> AUTO_SIEVE = ITEMS.register("auto_sieve",
            () -> new BlockItem(ModBlocks.AUTO_SIEVE.get(), new Item.Properties().group(Ref.GROUP)));
    public static RegistryObject<Item> AUTO_HAMMER = ITEMS.register("auto_hammer",
            () -> new BlockItem(ModBlocks.AUTO_HAMMER.get(), new Item.Properties().group(Ref.GROUP)));
    public static RegistryObject<Item> AUTO_SILKER = ITEMS.register("auto_silker",
            () -> new BlockItem(ModBlocks.AUTO_SILKER.get(), new Item.Properties().group(Ref.GROUP)));

    public static RegistryObject<Item> REINFORCED_UPGRADE = ITEMS.register("reinforcement_upgrade", ReinforcementUpgradeItem::new);
    public static RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", SpeedUpgradeItem::new);
    public static RegistryObject<Item> BONUS_UPGRADE = ITEMS.register("bonus_upgrade", BonusDropUpgradeItem::new);

    public static void init() {
        ReinforcedHammers.init();
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
