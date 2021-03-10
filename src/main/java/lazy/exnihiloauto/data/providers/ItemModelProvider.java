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
        CompressedBlocks.COMPRESSED_BLOCKS.forEach(this::parentBlock);
        ReinforcedHammers.HAMMERS.forEach(this::handheld);
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
