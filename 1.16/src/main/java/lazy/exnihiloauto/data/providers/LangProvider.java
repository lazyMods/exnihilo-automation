package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.block.compressed.CompressedBlock;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.ModItems;
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

        //Book
        this.add("docs.name", "Ex Nihilo: Automation");
        this.add("docs.landing_text", "Things you need to know.");
        this.add("docs.subtitle", "You actually read the tooltips. Amazing! *Claps*");

        this.add(ModBlocks.AUTO_HAMMER.get(), "Automatic Hammer");
        this.add("tiles.title.hammer", "Auto Hammer");
        this.add(ModBlocks.AUTO_SIEVE.get(), "Automatic Sieve");
        this.add("tiles.title.sieve", "Auto Sieve");
        this.add(ModBlocks.AUTO_SILKER.get(), "Automatic Silker");
        this.add("tiles.title.silker", "Auto Silker");

        this.add("tiles.energy", "Energy: ");
        this.add("tiles.openbookentry", "Open Documentation Entry");
        this.add("silker.leaves", "Leaves");
        this.add("silker.silk_worm", "Silk Worm");
        this.add("silker.silkworm_timer", "Silk Worm Work Timer");
        this.add("sieve.input", "Input(Siftable Block)");
        this.add("sieve.mesh", "Mesh");
        this.add("hammer.input", "Input(Hammarable Block)");
        this.add("hammer.hammer", "Hammer");

        this.add(ModItems.REINFORCED_UPGRADE.get(), "\u00a79Reinforcement Upgrade");
        this.add(ModItems.SPEED_UPGRADE.get(), "\u00a79Speed Upgrade");
        this.add(ModItems.BONUS_UPGRADE.get(), "\u00a79Bonus Drop Upgrade");

        this.add("upgrade.bonus.effect", "\u00a76Effect:\u00a7r Bonus chance of getting a double output.");
        this.add("upgrade.bonus.apply_to", "\u00a7eApply to:\u00a7r Auto Hammer, Auto Silker.");
        this.add("upgrade.speed.effect", "\u00a76Effect:\u00a7r Pretty much explanatory.");
        this.add("upgrade.speed.apply_to", "\u00a7eApply to:\u00a7r All.");
        this.add("upgrade.reinforcement.effect", "\u00a76Effect:\u00a7r Ability to hammer the compressed blocks.");
        this.add("upgrade.reinforcement.apply_to", "\u00a7eApply to:\u00a7r Auto Hammer.");

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
