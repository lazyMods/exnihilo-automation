package lazy.exnihiloauto.utils;

import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import novamachina.exnihilosequentia.api.ExNihiloRegistries;
import novamachina.exnihilosequentia.common.item.mesh.EnumMesh;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExNihiloUtils {

    public static List<ItemStack> getWithMeshChance(Item itemProvider, EnumMesh currentMeshType, Random rand) {
        var drops = new ArrayList<ItemStack>();
        ExNihiloRegistries.SIEVE_REGISTRY.getDrops(itemProvider, currentMeshType, false)
                .forEach((entry -> entry.getRolls().forEach(meshWithChance -> {
                    if(rand.nextFloat() <= meshWithChance.getChance()) {
                        drops.add(entry.getDrop());
                    }
                })))
        ;
        return drops;
    }

    public static ItemStack getHammeredOutput(Block block) {
        return ExNihiloRegistries.HAMMER_REGISTRY.getResult(block).get(0).getStack();
    }
}
