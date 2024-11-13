package lazy.exnihiloauto.block;

import lazy.exnihiloauto.setup.ModTiles;

public class AutoSilkerBlock extends AutoBlock {

    public AutoSilkerBlock() {
        super(() -> ModTiles.AUTO_SILKER.get().create());
    }
}
