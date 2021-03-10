package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import lazy.exnihiloauto.setup.other.ReinforcedHammers;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {

    public LangProvider(DataGenerator gen) {
        super(gen, Ref.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(ModBlocks.AUTO_HAMMER.get(), "Automatic Hammer");
        this.add(ModBlocks.AUTO_SIEVE.get(), "Automatic Sieve");
        CompressedBlocks.COMPRESSED_BLOCKS.forEach(blockObj -> this.add(blockObj.get(), blockObj.get().getDisplayName()));
        ReinforcedHammers.HAMMERS.forEach(itemObj -> this.add(itemObj.get(), itemObj.get().getDisplayName()));
    }
}
