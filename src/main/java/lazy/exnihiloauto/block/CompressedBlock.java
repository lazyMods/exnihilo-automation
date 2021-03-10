package lazy.exnihiloauto.block;

import lazy.exnihiloauto.setup.ModToolTypes;
import lazy.exnihiloauto.utils.CompressionTier;
import lombok.Getter;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CompressedBlock extends Block {

    @Getter
    private final CompressionTier tier;
    @Getter
    private final Block compressedBlock;
    private final String compressedBlockName;

    public CompressedBlock(Block compressedBlock, String compressedBlockName, CompressionTier tier) {
        super(Properties.from(compressedBlock).setRequiresTool().harvestTool(ModToolTypes.REINFORCED_HAMMER));
        this.compressedBlockName = compressedBlockName;
        this.tier = tier;
        this.compressedBlock = compressedBlock;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        var blockName = new ItemStack(compressedBlock).getDisplayName().getString();
        var toDisplay = String.format("Contains %s of %s.", this.tier.tierAmt, blockName);
        tooltip.add(new StringTextComponent(toDisplay));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public String getDisplayName() {
        return String.format("%s%s", this.tier.name, this.compressedBlockName);
    }
}
