package net.marcosantos.exnihiloauto.utils;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import novamachina.exnihilosequentia.common.registries.ExNihiloRegistries;
import novamachina.exnihilosequentia.world.item.MeshType;

import java.util.ArrayList;
import java.util.List;

public class EXNUtils {

	public static List<ItemStack> getWithMeshChance(ItemLike item, MeshType type, float chance) {
		var drops = new ArrayList<ItemStack>();
		ExNihiloRegistries.SIEVE_REGISTRY.getDrops(item, type, false)
				.forEach((e -> e.getRolls().forEach(meshWithChance -> {
					if (chance <= meshWithChance.getChance()) {
						drops.add(e.getDrop());
					}
				})));
		return drops;
	}

	public static ItemStack getHammeredOutput(Block block) {
		return ExNihiloRegistries.HAMMER_REGISTRY.getResult(block).getFirst().getStack();
	}
}
