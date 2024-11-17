package net.marcosantos.exnihiloauto.data;

import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.world.level.block.CompressedBlock;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(PackOutput gen) {
		super(gen, ExNihiloAuto.MODID, "en_us");
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
		this.add("sieve.input", "Siftable Block");
		this.add("sieve.mesh", "Mesh");
		this.add("hammer.input", "Hammarable Block");
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

		this.createCompressedBlockTrans(ModBlocks.COMPRESSED_COBBLE, "Cobblestone");
		this.createCompressedBlockTrans(ModBlocks.HIGHLY_COMPRESSED_COBBLE, "Cobblestone");
		this.createCompressedBlockTrans(ModBlocks.ATOMIC_COMPRESSED_COBBLE, "Cobblestone");
		this.createCompressedBlockTrans(ModBlocks.COMPRESSED_GRAVEL, "Gravel");
		this.createCompressedBlockTrans(ModBlocks.HIGHLY_COMPRESSED_GRAVEL, "Gravel");
		this.createCompressedBlockTrans(ModBlocks.ATOMIC_COMPRESSED_GRAVEL, "Gravel");
		this.createCompressedBlockTrans(ModBlocks.COMPRESSED_SAND, "Sand");
		this.createCompressedBlockTrans(ModBlocks.HIGHLY_COMPRESSED_SAND, "Sand");
		this.createCompressedBlockTrans(ModBlocks.ATOMIC_COMPRESSED_SAND, "Sand");
		this.createCompressedBlockTrans(ModBlocks.COMPRESSED_DUST, "Dust");
		this.createCompressedBlockTrans(ModBlocks.HIGHLY_COMPRESSED_DUST, "Dust");
		this.createCompressedBlockTrans(ModBlocks.ATOMIC_COMPRESSED_DUST, "Dust");
	}

	private void createCompressedBlockTrans(DeferredBlock<CompressedBlock> obj, String compressed) {
		this.add(obj.get(), obj.get().tier.name + " " + compressed);
	}
}
