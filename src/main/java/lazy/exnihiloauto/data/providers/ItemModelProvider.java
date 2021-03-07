package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.setup.ModBlocks;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
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
    }

    private void parentBlock(RegistryObject<Block> blockRegistryObject) {
        var regName = Objects.requireNonNull(blockRegistryObject.get().getRegistryName());
        this.getBuilder(regName.toString()).parent(new ModelFile.UncheckedModelFile(this.modLoc("block/".concat(regName.getPath()))));
    }
}
