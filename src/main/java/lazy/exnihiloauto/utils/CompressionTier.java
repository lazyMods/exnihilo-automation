package lazy.exnihiloauto.utils;

public enum CompressionTier {

    COMPRESSED(9, "Compressed "),
    HIGHLY_COMPRESSED(81, "Highly Compressed "),
    ATOMIC_COMPRESSION(729, "Atomic Compressed ");

    public int tierAmt;
    public String name;

    CompressionTier(int tierAmt, String name) {
        this.tierAmt = tierAmt;
        this.name = name;
    }
}
