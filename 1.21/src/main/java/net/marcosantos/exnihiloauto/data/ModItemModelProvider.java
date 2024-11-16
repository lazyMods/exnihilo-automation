package net.marcosantos.exnihiloauto.data;

import java.util.Objects;
import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, ExNihiloAuto.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.parentBlock(ModBlocks.AUTO_SIEVE);
		this.parentBlock(ModBlocks.AUTO_HAMMER);
		this.parentBlock(ModBlocks.AUTO_SILKER);

		this.zeroLayered(ModItems.REINFORCED_UPGRADE, "item/generated", "item/reinforcement_upgrade");
		this.zeroLayered(ModItems.SPEED_UPGRADE, "item/generated", "item/speed_upgrade");
		this.zeroLayered(ModItems.BONUS_UPGRADE, "item/generated", "item/bonus_upgrade");

		this.registerCompressedModels();
	}

	private void registerCompressedModels() {
		this.parentBlock(ModBlocks.COMPRESSED_COBBLE);
		this.parentBlock(ModBlocks.HIGHLY_COMPRESSED_COBBLE);
		this.parentBlock(ModBlocks.ATOMIC_COMPRESSED_COBBLE);
		this.parentBlock(ModBlocks.COMPRESSED_GRAVEL);
		this.parentBlock(ModBlocks.HIGHLY_COMPRESSED_GRAVEL);
		this.parentBlock(ModBlocks.ATOMIC_COMPRESSED_GRAVEL);
		this.parentBlock(ModBlocks.COMPRESSED_SAND);
		this.parentBlock(ModBlocks.HIGHLY_COMPRESSED_SAND);
		this.parentBlock(ModBlocks.ATOMIC_COMPRESSED_SAND);
		this.parentBlock(ModBlocks.COMPRESSED_DUST);
		this.parentBlock(ModBlocks.HIGHLY_COMPRESSED_DUST);
		this.parentBlock(ModBlocks.ATOMIC_COMPRESSED_DUST);
	}

	private <T extends Block> void parentBlock(DeferredBlock<T> blockRegistryObject) {
		var regName = Objects.requireNonNull(blockRegistryObject.getId());
		this.getBuilder(regName.toString()).parent(new ModelFile.UncheckedModelFile(this.modLoc("block/".concat(regName.getPath()))));
	}

	private <T extends Item> void zeroLayered(DeferredItem<T> item, String parent, String texturePath) {
		this.getBuilder(Objects.requireNonNull(item.getId()).toString()).parent(new ModelFile.UncheckedModelFile(parent)).texture("layer0", texturePath);
	}
}
