package lazy.exnihiloauto.setup;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.tiles.AutoHammerTile;
import lazy.exnihiloauto.tiles.AutoSieveTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("ConstantConditions")
public class ModTiles {

    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Ref.MOD_ID);

    public static RegistryObject<TileEntityType<AutoSieveTile>> AUTO_SIEVE = TILES.register("auto_sieve",
            () -> TileEntityType.Builder.create(AutoSieveTile::new, ModBlocks.AUTO_SIEVE.get()).build(null));
    public static RegistryObject<TileEntityType<AutoHammerTile>> AUTO_HAMMER = TILES.register("auto_hammer",
            () -> TileEntityType.Builder.create(AutoHammerTile::new, ModBlocks.AUTO_HAMMER.get()).build(null));

    public static void init() {
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
