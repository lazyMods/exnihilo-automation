package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.block.compressed.CompressedBlock;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import lazy.exnihiloauto.setup.other.ReinforcedHammers;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class LangProvider extends LanguageProvider {

    public LangProvider(DataGenerator gen) {
        super(gen, Ref.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.exnihiloauto", "Ex Nihilo: Automation");
        this.add(ModBlocks.AUTO_HAMMER.get(), "Automatic Hammer");
        this.add(ModBlocks.AUTO_SIEVE.get(), "Automatic Sieve");
        this.add(ModBlocks.AUTO_SILKER.get(), "Automatic Sieve");

        this.add("compressed.cobblestone", "Cobblestone");
        this.add("compressed.gravel", "Gravel");
        this.add("compressed.sand", "Sand");
        this.add("compressed.dust", "Dust");

        this.createCompressedBlockTrans(CompressedBlocks.COMPRESSED_COBBLE.getBlock(), "Cobblestone");
        this.createCompressedBlockTrans(CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock(), "Cobblestone");
        this.createCompressedBlockTrans(CompressedBlocks.ATOMIC_COMPRESSION_COBBLE.getBlock(), "Cobblestone");
        this.createCompressedBlockTrans(CompressedBlocks.COMPRESSED_GRAVEL.getBlock(), "Gravel");
        this.createCompressedBlockTrans(CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock(), "Gravel");
        this.createCompressedBlockTrans(CompressedBlocks.ATOMIC_COMPRESSION_GRAVEL.getBlock(), "Gravel");
        this.createCompressedBlockTrans(CompressedBlocks.COMPRESSED_SAND.getBlock(), "Sand");
        this.createCompressedBlockTrans(CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock(), "Sand");
        this.createCompressedBlockTrans(CompressedBlocks.ATOMIC_COMPRESSION_SAND.getBlock(), "Sand");
        this.createCompressedBlockTrans(CompressedBlocks.COMPRESSED_DUST.getBlock(), "Dust");
        this.createCompressedBlockTrans(CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock(), "Dust");
        this.createCompressedBlockTrans(CompressedBlocks.ATOMIC_COMPRESSION_DUST.getBlock(), "Dust");

        ReinforcedHammers.HAMMERS.forEach(itemObj -> this.add(itemObj.get(), itemObj.get().getDisplayName()));
    }

    private void createCompressedBlockTrans(RegistryObject<CompressedBlock> obj, String compressed) {
        this.add(obj.get(), obj.get().getTier().name + compressed);
    }
}
