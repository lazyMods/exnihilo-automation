package lazy.exnihiloauto.block;

import lazy.exnihiloauto.setup.ModTiles;

public class AutoSieveBlock extends AutoBlock {

    public AutoSieveBlock() {
        super(() -> ModTiles.AUTO_SIEVE.get().create());
    }
}
