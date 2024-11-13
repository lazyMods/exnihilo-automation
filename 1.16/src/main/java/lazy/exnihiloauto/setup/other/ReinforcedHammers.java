package lazy.exnihiloauto.setup.other;

import lazy.exnihiloauto.items.ReinforcedHammerItem;
import lazy.exnihiloauto.setup.ModItems;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import novamachina.exnihilosequentia.common.item.tools.hammer.EnumHammer;

import java.util.ArrayList;
import java.util.List;

public class ReinforcedHammers {

    public static List<RegistryObject<ReinforcedHammerItem>> HAMMERS = new ArrayList<>();

    public static RegistryObject<ReinforcedHammerItem> WOODEN;
    public static RegistryObject<ReinforcedHammerItem> STONE;
    public static RegistryObject<ReinforcedHammerItem> IRON;
    public static RegistryObject<ReinforcedHammerItem> GOLDEN;
    public static RegistryObject<ReinforcedHammerItem> DIAMOND;
    public static RegistryObject<ReinforcedHammerItem> NETHERITE;

    public static void init() {
        WOODEN = createHammer("wooden_reinforced_hammer", ItemTier.WOOD, EnumHammer.WOOD, "Wooden", 228);
        STONE = createHammer("stone_reinforced_hammer", ItemTier.STONE, EnumHammer.STONE, "Stone", 356);
        IRON = createHammer("iron_reinforced_hammer", ItemTier.IRON, EnumHammer.IRON, "Iron", 612);
        GOLDEN = createHammer("golden_reinforced_hammer", ItemTier.GOLD, EnumHammer.GOLD, "Golden", 164);
        DIAMOND = createHammer("diamond_reinforced_hammer", ItemTier.DIAMOND, EnumHammer.DIAMOND, "Diamond", 4196);
        NETHERITE = createHammer("netherite_reinforced_hammer", ItemTier.NETHERITE, EnumHammer.NETHERITE, "Netherite", 8292);
    }

    private static RegistryObject<ReinforcedHammerItem> createHammer(String regName, ItemTier tier, EnumHammer base, String displayName, int durability) {
        RegistryObject<ReinforcedHammerItem> itemRegistryObject;
        HAMMERS.add(itemRegistryObject = ModItems.ITEMS.register(regName, () -> new ReinforcedHammerItem(tier, base, displayName, durability)));
        return itemRegistryObject;
    }
}
