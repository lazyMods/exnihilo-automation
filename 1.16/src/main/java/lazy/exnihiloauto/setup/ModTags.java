package lazy.exnihiloauto.setup;

import lazy.exnihiloauto.Ref;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    public static ITag.INamedTag<Item> REINFORCED_HAMMERS;

    public static void init() {
        REINFORCED_HAMMERS = ItemTags.createOptional(new ResourceLocation(Ref.MOD_ID, "reinforced_hammers"));
    }
}
