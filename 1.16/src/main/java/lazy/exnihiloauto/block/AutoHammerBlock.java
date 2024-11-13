package lazy.exnihiloauto.block;

import lazy.exnihiloauto.setup.ModTiles;

public class AutoHammerBlock extends AutoBlock {

    public AutoHammerBlock() {
        super(() -> ModTiles.AUTO_HAMMER.get().create());
    }
}
