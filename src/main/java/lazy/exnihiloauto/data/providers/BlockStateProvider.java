package lazy.exnihiloauto.data.providers;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.setup.ModBlocks;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {

    public BlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Ref.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.registerAutoModels();
        this.registerCompressedModels();
    }

    private void registerAutoModels(){
        this.modAutoBlock(ModBlocks.AUTO_SIEVE);
        this.modAutoBlock(ModBlocks.AUTO_HAMMER);
        this.modAutoBlock(ModBlocks.AUTO_SILKER);
    }

    private void registerCompressedModels() {
        this.simplerBlock(CompressedBlocks.COMPRESSED_COBBLE.getBlock());
        this.simplerBlock(CompressedBlocks.HIGHLY_COMPRESSED_COBBLE.getBlock());
        this.simplerBlock(CompressedBlocks.ATOMIC_COMPRESSION_COBBLE.getBlock());
        this.simplerBlock(CompressedBlocks.COMPRESSED_GRAVEL.getBlock());
        this.simplerBlock(CompressedBlocks.HIGHLY_COMPRESSED_GRAVEL.getBlock());
        this.simplerBlock(CompressedBlocks.ATOMIC_COMPRESSION_GRAVEL.getBlock());
        this.simplerBlock(CompressedBlocks.COMPRESSED_SAND.getBlock());
        this.simplerBlock(CompressedBlocks.HIGHLY_COMPRESSED_SAND.getBlock());
        this.simplerBlock(CompressedBlocks.ATOMIC_COMPRESSION_SAND.getBlock());
        this.simplerBlock(CompressedBlocks.COMPRESSED_DUST.getBlock());
        this.simplerBlock(CompressedBlocks.HIGHLY_COMPRESSED_DUST.getBlock());
        this.simplerBlock(CompressedBlocks.ATOMIC_COMPRESSION_DUST.getBlock());
    }

    private <T extends Block> void simplerBlock(RegistryObject<T> registryObject) {
        this.simpleBlock(registryObject.get(), this.cubeAll(registryObject.get()));
    }

    private <T extends Block> void modAutoBlock(RegistryObject<T> registryObject) {
        this.simpleBlock(registryObject.get(), this.models().cube(
                Objects.requireNonNull(registryObject.get().getRegistryName()).toString(),
                this.modLoc("block/auto_bottom"),
                this.modLoc("block/auto_top"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side"),
                this.modLoc("block/auto_side")
        ));
    }
}
