package lazy.exnihiloauto.block.compressed;

import lazy.exnihiloauto.setup.ModToolTypes;
import lazy.exnihiloauto.utils.CompressionTier;
import lombok.Getter;
import net.minecraft.block.Block;

public abstract class CompressedBlock extends Block {

    @Getter
    private final CompressionTier tier;

    public CompressedBlock(Properties properties, CompressionTier tier) {
        super(properties.requiresCorrectToolForDrops().harvestTool(ModToolTypes.REINFORCED_HAMMER));
        this.tier = tier;
    }
}
