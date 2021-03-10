package lazy.exnihiloauto.utils;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.ModItems;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class BlockItemRegObj<T extends Block> {

    @Getter
    private RegistryObject<T> block;
    @Getter
    private final RegistryObject<BlockItem> blockItem;

    public BlockItemRegObj(String regName, Supplier<T> blockSupplier) {
        this.block = this.createBlock(regName, blockSupplier);
        this.blockItem = this.createBlockItem(regName, this.block);
    }

    private RegistryObject<T> createBlock(String regName, Supplier<T> blockSupplier) {
        this.block = ModBlocks.BLOCKS.register(regName, blockSupplier);
        return this.block;
    }

    private RegistryObject<BlockItem> createBlockItem(String regName, RegistryObject<T> block) {
        return ModItems.ITEMS.register(regName, () -> new BlockItem(block.get(), new Item.Properties().group(Ref.GROUP)));
    }
}
