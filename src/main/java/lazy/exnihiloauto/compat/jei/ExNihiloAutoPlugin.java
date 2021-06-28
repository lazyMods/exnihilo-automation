package lazy.exnihiloauto.compat.jei;

import lazy.exnihiloauto.Ref;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class ExNihiloAutoPlugin implements IModPlugin {

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Ref.MOD_ID, "jei_plugin");
    }
}
