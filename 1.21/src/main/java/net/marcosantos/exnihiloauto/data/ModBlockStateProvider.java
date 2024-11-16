package net.marcosantos.exnihiloauto.data;

import java.util.Objects;
import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, ExNihiloAuto.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.registerAutoModels();
		this.registerCompressedModels();
	}

	private void registerAutoModels() {
		this.modAutoBlock(ModBlocks.AUTO_SIEVE);
		this.modAutoBlock(ModBlocks.AUTO_HAMMER);
		this.modAutoBlock(ModBlocks.AUTO_SILKER);
	}

	private void registerCompressedModels() {
		this.simplerBlock(ModBlocks.COMPRESSED_COBBLE);
		this.simplerBlock(ModBlocks.HIGHLY_COMPRESSED_COBBLE);
		this.simplerBlock(ModBlocks.ATOMIC_COMPRESSED_COBBLE);
		this.simplerBlock(ModBlocks.COMPRESSED_GRAVEL);
		this.simplerBlock(ModBlocks.HIGHLY_COMPRESSED_GRAVEL);
		this.simplerBlock(ModBlocks.ATOMIC_COMPRESSED_GRAVEL);
		this.simplerBlock(ModBlocks.COMPRESSED_SAND);
		this.simplerBlock(ModBlocks.HIGHLY_COMPRESSED_SAND);
		this.simplerBlock(ModBlocks.ATOMIC_COMPRESSED_SAND);
		this.simplerBlock(ModBlocks.COMPRESSED_DUST);
		this.simplerBlock(ModBlocks.HIGHLY_COMPRESSED_DUST);
		this.simplerBlock(ModBlocks.ATOMIC_COMPRESSED_DUST);
	}

	private <T extends Block> void simplerBlock(DeferredBlock<T> registryObject) {
		this.simpleBlock(registryObject.get(), this.cubeAll(registryObject.get()));
	}

	private <T extends Block> void modAutoBlock(DeferredBlock<T> registryObject) {
		this.simpleBlock(
				registryObject.get(),
				this.models()
						.cube(Objects.requireNonNull(registryObject.getId()).toString(),
								this.modLoc("block/auto_bottom"),
								this.modLoc("block/auto_top"),
								this.modLoc("block/auto_side"),
								this.modLoc("block/auto_silker_insert"),
								this.modLoc("block/auto_side"),
								this.modLoc("block/auto_side"))
						.texture("particle", "minecraft:block/stone"));
	}
}
