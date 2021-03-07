package lazy.exnihiloauto.setup;

import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.inventory.container.AutoHammerContainer;
import lazy.exnihiloauto.inventory.container.AutoSieveContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Ref.MOD_ID);

    public static RegistryObject<ContainerType<AutoSieveContainer>> AUTO_SIEVE = CONTAINERS.register("auto_sieve",
            () -> IForgeContainerType.create(((windowId, inv, data) -> new AutoSieveContainer(windowId, inv))));
    public static RegistryObject<ContainerType<AutoHammerContainer>> AUTO_HAMMER = CONTAINERS.register("auto_hammer",
            () -> IForgeContainerType.create(((windowId, inv, data) -> new AutoHammerContainer(windowId, inv))));

    public static void init() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
