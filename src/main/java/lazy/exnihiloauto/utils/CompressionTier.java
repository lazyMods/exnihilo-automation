package lazy.exnihiloauto.utils;

import javax.annotation.Nullable;

public enum CompressionTier {

    COMPRESSED(9, "Compressed ", null),
    HIGHLY_COMPRESSED(81, "Highly Compressed ", COMPRESSED),
    ATOMIC_COMPRESSION(729, "Atomic Compressed ", HIGHLY_COMPRESSED);

    public int tierAmt;
    public String name;
    @Nullable
    public CompressionTier tierBelow;

    CompressionTier(int tierAmt, String name, @Nullable CompressionTier tierBelow) {
        this.tierAmt = tierAmt;
        this.name = name;
        this.tierBelow = tierBelow;
    }
}
