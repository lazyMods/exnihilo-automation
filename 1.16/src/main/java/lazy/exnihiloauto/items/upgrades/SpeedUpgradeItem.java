package lazy.exnihiloauto.items.upgrades;

import lazy.exnihiloauto.items.UpgradeItem;
import lazy.exnihiloauto.tiles.AutoTileEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class SpeedUpgradeItem extends UpgradeItem {

    @Override
    public boolean canApplyOn(Class<? extends AutoTileEntity> toApply) {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("upgrade.speed.effect"));
        tooltip.add(new TranslationTextComponent("upgrade.speed.apply_to"));
    }
}
