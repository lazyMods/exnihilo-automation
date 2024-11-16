package net.marcosantos.exnihiloauto.data;

import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.world.level.block.CompressedBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import novamachina.exnihilosequentia.world.level.block.EXNBlocks;
import novamachina.novacore.data.loot.table.BlockLootTables;
import novamachina.novacore.world.level.block.BlockDefinition;

public class ModBlockLootProvider extends BlockLootTables {

	protected ModBlockLootProvider(HolderLookup.Provider provider) {
		super(provider);
	}

	@Override
	protected void generate() {

		this.add(this::createSingleItemTable,
				blockDef(ModBlocks.AUTO_HAMMER, ModItems.AUTO_HAMMER),
				blockDef(ModBlocks.AUTO_SIEVE, ModItems.AUTO_SIEVE),
				blockDef(ModBlocks.AUTO_SILKER, ModItems.AUTO_SILKER)
		);
		this.compressedBlockTables(ModBlocks.COMPRESSED_COBBLE, Blocks.COBBLESTONE, ModItems.COMPRESSED_COBBLE);
		this.compressedBlockTables(ModBlocks.HIGHLY_COMPRESSED_COBBLE, Blocks.COBBLESTONE, ModItems.HIGHLY_COMPRESSED_COBBLE);
		this.compressedBlockTables(ModBlocks.ATOMIC_COMPRESSED_COBBLE, Blocks.COBBLESTONE, ModItems.ATOMIC_COMPRESSED_COBBLE);
		this.compressedBlockTables(ModBlocks.COMPRESSED_GRAVEL, Blocks.GRAVEL, ModItems.COMPRESSED_GRAVEL);
		this.compressedBlockTables(ModBlocks.HIGHLY_COMPRESSED_GRAVEL, Blocks.GRAVEL, ModItems.HIGHLY_COMPRESSED_GRAVEL);
		this.compressedBlockTables(ModBlocks.ATOMIC_COMPRESSED_GRAVEL, Blocks.GRAVEL, ModItems.ATOMIC_COMPRESSED_GRAVEL);
		this.compressedBlockTables(ModBlocks.COMPRESSED_SAND, Blocks.SAND, ModItems.COMPRESSED_SAND);
		this.compressedBlockTables(ModBlocks.HIGHLY_COMPRESSED_SAND, Blocks.SAND, ModItems.HIGHLY_COMPRESSED_SAND);
		this.compressedBlockTables(ModBlocks.ATOMIC_COMPRESSED_SAND, Blocks.SAND, ModItems.ATOMIC_COMPRESSED_SAND);
		this.compressedBlockTables(ModBlocks.COMPRESSED_DUST, EXNBlocks.DUST.block(), ModItems.COMPRESSED_DUST);
		this.compressedBlockTables(ModBlocks.HIGHLY_COMPRESSED_DUST, EXNBlocks.DUST.block(), ModItems.HIGHLY_COMPRESSED_DUST);
		this.compressedBlockTables(ModBlocks.ATOMIC_COMPRESSED_DUST, EXNBlocks.DUST.block(), ModItems.ATOMIC_COMPRESSED_DUST);
	}

	private <T extends Block> BlockDefinition<T> blockDef(DeferredBlock<T> b, DeferredItem<BlockItem> i) {
		return new BlockDefinition<>("", b.getId(), b.get(), i.get());
	}

	public void compressedBlockTables(DeferredBlock<CompressedBlock> block, Block toDrop, DeferredItem<BlockItem> item) {
		add(b -> LootTable.lootTable().withPool(new LootPool.Builder().name(block.getRegisteredName())
				.add(LootItem.lootTableItem(toDrop)
						.apply(LimitCount.limitCount(IntRange.exact(block.get().tier.amt))))),
				blockDef(block, item));
	}

}
