package lazy.exnihiloauto;

import lazy.exnihiloauto.setup.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;

public class Ref {

    public static final String MOD_ID = "exnihiloauto";

    public static final ItemGroup GROUP = new ItemGroup("exnihiloauto") {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(ModItems.AUTO_HAMMER.get());
        }
    };
}
