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

    public static void init() {
        createHammer("wooden_reinforced_hammer", ItemTier.WOOD, EnumHammer.WOOD, "Wooden", 228);
        createHammer("stone_reinforced_hammer", ItemTier.STONE, EnumHammer.STONE, "Stone", 356);
        createHammer("iron_reinforced_hammer", ItemTier.IRON, EnumHammer.IRON, "Iron", 612);
        createHammer("golden_reinforced_hammer", ItemTier.GOLD, EnumHammer.GOLD, "Golden", 164);
        createHammer("diamond_reinforced_hammer", ItemTier.DIAMOND, EnumHammer.DIAMOND, "Diamond", 4196);
        createHammer("netherite_reinforced_hammer", ItemTier.NETHERITE, EnumHammer.NETHERITE, "Netherite", 8292);
    }

    private static void createHammer(String regName, ItemTier tier, EnumHammer base, String displayName, int durability) {
        HAMMERS.add(ModItems.ITEMS.register(regName, () -> new ReinforcedHammerItem(tier, base, displayName, durability)));
    }
}
