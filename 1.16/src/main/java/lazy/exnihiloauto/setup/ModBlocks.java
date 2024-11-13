package lazy.exnihiloauto.setup;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.block.AutoHammerBlock;
import lazy.exnihiloauto.block.AutoSieveBlock;
import lazy.exnihiloauto.block.AutoSilkerBlock;
import lazy.exnihiloauto.setup.other.CompressedBlocks;
import lazy.exnihiloauto.tiles.AutoSilkerTile;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Ref.MOD_ID);

    public static RegistryObject<Block> AUTO_SIEVE = BLOCKS.register("auto_sieve", AutoSieveBlock::new);
    public static RegistryObject<Block> AUTO_HAMMER = BLOCKS.register("auto_hammer", AutoHammerBlock::new);
    public static RegistryObject<Block> AUTO_SILKER = BLOCKS.register("auto_silker", AutoSilkerBlock::new);

    public static void init() {
        CompressedBlocks.init();
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
