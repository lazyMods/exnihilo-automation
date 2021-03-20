package lazy.exnihiloauto.items;

import com.google.common.collect.Sets;
import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.block.compressed.CompressedBlock;
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

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(
            CompressedBlocks.COMPRESSED_COBBLE.getBlock().get(),
            CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock().get(),
            CompressedBlocks.ATOMIC_COMPRESSION_COBBLE.getBlock().get(),
            CompressedBlocks.COMPRESSED_GRAVEL.getBlock().get(),
            CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock().get(),
            CompressedBlocks.ATOMIC_COMPRESSION_GRAVEL.getBlock().get(),
            CompressedBlocks.COMPRESSED_SAND.getBlock().get(),
            CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock().get(),
            CompressedBlocks.ATOMIC_COMPRESSION_SAND.getBlock().get(),
            CompressedBlocks.COMPRESSED_DUST.getBlock().get(),
            CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock().get(),
            CompressedBlocks.ATOMIC_COMPRESSION_SAND.getBlock().get()
    );

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
    
    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        if (blockIn.getBlock() instanceof CompressedBlock) {
            if (EFFECTIVE_ON.contains(blockIn.getBlock())) {
                return true;
            }
        }
        return super.canHarvestBlock(blockIn);
    }

    public String getDisplayName() {
        return String.format("%s Reinforced Hammer", this.displayName);
    }
}
