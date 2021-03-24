package lazy.exnihiloauto.items;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.tiles.AutoTileEntity;
import net.minecraft.item.Item;

public abstract class UpgradeItem extends Item {

    public UpgradeItem() {
        super(new Properties().maxStackSize(1).group(Ref.GROUP));
    }

    public abstract boolean canApplyOn(Class<? extends AutoTileEntity> toApply);
}
