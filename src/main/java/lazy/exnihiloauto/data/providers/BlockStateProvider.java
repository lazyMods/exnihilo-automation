package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ref.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(ModBlocks.AUTO_SIEVE.get(), this.models().cube(
                Objects.requireNonNull(ModBlocks.AUTO_SIEVE.get().getRegistryName()).toString(),
                this.modLoc("block/auto_bottom"),
                this.modLoc("block/auto_top"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side")
        ));
        this.simpleBlock(ModBlocks.AUTO_HAMMER.get(), this.models().cube(
                Objects.requireNonNull(ModBlocks.AUTO_HAMMER.get().getRegistryName()).toString(),
                this.modLoc("block/auto_bottom"),
                this.modLoc("block/auto_top"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side")
        ));

        CompressedBlocks.COMPRESSED_BLOCKS.forEach(b -> this.simpleBlock(b.get(), this.cubeAll(b.get())));
    }
}
