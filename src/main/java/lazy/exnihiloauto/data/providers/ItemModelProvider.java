package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import lazy.exnihiloauto.setup.other.ReinforcedHammers;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ref.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.parentBlock(ModBlocks.AUTO_SIEVE);
        this.parentBlock(ModBlocks.AUTO_HAMMER);
        this.parentBlock(ModBlocks.AUTO_SILKER);
        this.registerCompressedModels();
        ReinforcedHammers.HAMMERS.forEach(this::handheld);
    }

    private void registerCompressedModels() {
        this.parentBlock(CompressedBlocks.COMPRESSED_COBBLE.getBlock());
        this.parentBlock(CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock());
        this.parentBlock(CompressedBlocks.ATOMIC_COMPRESSION_COBBLE.getBlock());
        this.parentBlock(CompressedBlocks.COMPRESSED_GRAVEL.getBlock());
        this.parentBlock(CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock());
        this.parentBlock(CompressedBlocks.ATOMIC_COMPRESSION_GRAVEL.getBlock());
        this.parentBlock(CompressedBlocks.COMPRESSED_SAND.getBlock());
        this.parentBlock(CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock());
        this.parentBlock(CompressedBlocks.ATOMIC_COMPRESSION_SAND.getBlock());
        this.parentBlock(CompressedBlocks.COMPRESSED_DUST.getBlock());
        this.parentBlock(CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock());
        this.parentBlock(CompressedBlocks.ATOMIC_COMPRESSION_DUST.getBlock());
    }

    private <T extends Block> void parentBlock(RegistryObject<T> blockRegistryObject) {
        var regName = Objects.requireNonNull(blockRegistryObject.get().getRegistryName());
        this.getBuilder(regName.toString()).parent(new ModelFile.UncheckedModelFile(this.modLoc("block/".concat(regName.getPath()))));
    }

    private <T extends Item> void handheld(RegistryObject<T> item) {
        this.zeroLayered(item, "item/handheld", "items/".concat(Objects.requireNonNull(item.get().getRegistryName()).getPath()));
    }

    private <T extends Item> void zeroLayered(RegistryObject<T> item, String parent, String texturePath) {
        this.getBuilder(Objects.requireNonNull(item.get().getRegistryName()).toString()).parent(new ModelFile.UncheckedModelFile(parent)).texture("layer0", texturePath);
    }
}
