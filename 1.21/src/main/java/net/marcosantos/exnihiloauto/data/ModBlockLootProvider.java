package net.marcosantos.exnihiloauto.data;

import net.marcosantos.exnihiloauto.registries.ModBlocks;
import net.marcosantos.exnihiloauto.registries.ModItems;
import net.marcosantos.exnihiloauto.world.level.block.CompressedBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
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
				blockDef(ModBlocks.AUTO_SILKER, ModItems.AUTO_SILKER),
				blockDef(ModBlocks.COMPRESSED_COBBLE, ModItems.COMPRESSED_COBBLE),
				blockDef(ModBlocks.HIGHLY_COMPRESSED_COBBLE, ModItems.HIGHLY_COMPRESSED_COBBLE),
				blockDef(ModBlocks.ATOMIC_COMPRESSED_COBBLE, ModItems.ATOMIC_COMPRESSED_COBBLE),
				blockDef(ModBlocks.COMPRESSED_GRAVEL, ModItems.COMPRESSED_GRAVEL),
				blockDef(ModBlocks.HIGHLY_COMPRESSED_GRAVEL, ModItems.HIGHLY_COMPRESSED_GRAVEL),
				blockDef(ModBlocks.ATOMIC_COMPRESSED_GRAVEL, ModItems.ATOMIC_COMPRESSED_GRAVEL),
				blockDef(ModBlocks.COMPRESSED_SAND, ModItems.COMPRESSED_SAND),
				blockDef(ModBlocks.HIGHLY_COMPRESSED_SAND, ModItems.HIGHLY_COMPRESSED_SAND),
				blockDef(ModBlocks.ATOMIC_COMPRESSED_SAND, ModItems.ATOMIC_COMPRESSED_SAND),
				blockDef(ModBlocks.COMPRESSED_DUST, ModItems.COMPRESSED_DUST),
				blockDef(ModBlocks.HIGHLY_COMPRESSED_DUST, ModItems.HIGHLY_COMPRESSED_DUST),
				blockDef(ModBlocks.ATOMIC_COMPRESSED_DUST, ModItems.ATOMIC_COMPRESSED_DUST)
		);
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
