package lazy.exnihiloauto.block.compressed;

import lazy.exnihiloauto.utils.CompressionTier;
import lombok.var;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CompressedDustBlock extends CompressedBlock {

    public CompressedDustBlock(CompressionTier tier) {
        super(Properties.of(Material.SAND).sound(SoundType.WOOL).strength(0.7F), tier);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        var toDisplay = String.format("Contains %s of %s.", this.getTier().tierAmt, new TranslationTextComponent("compressed.dust").getString());
        tooltip.add(new StringTextComponent(toDisplay));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
