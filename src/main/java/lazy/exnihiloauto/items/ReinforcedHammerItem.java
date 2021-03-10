package lazy.exnihiloauto.items;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.block.CompressedBlock;
import lazy.exnihiloauto.setup.ModToolTypes;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import novamachina.exnihilosequentia.common.item.tools.hammer.EnumHammer;

import java.util.Set;

public class ReinforcedHammerItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = CompressedBlocks.getCompressedSet();

    private final String displayName;
    @Getter
    private final EnumHammer base;

    public ReinforcedHammerItem(IItemTier tier, EnumHammer base, String displayName, int maxDamage) {
        super(0.5F, 0.5F, tier, EFFECTIVE_ON, new Item.Properties()
                .defaultMaxDamage(maxDamage)
                .group(Ref.GROUP)
                .addToolType(ModToolTypes.REINFORCED_HAMMER, tier.getHarvestLevel())
        );
        this.displayName = displayName;
        this.base = base;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        if (blockIn.getBlock() instanceof CompressedBlock) {
            if (CompressedBlocks.COMPRESSED_BLOCKS.contains(blockIn.getBlock())) {
                return true;
            }
        }
        return super.canHarvestBlock(blockIn);
    }

    public String getDisplayName() {
        return String.format("%s Reinforced Hammer", this.displayName);
    }
}
