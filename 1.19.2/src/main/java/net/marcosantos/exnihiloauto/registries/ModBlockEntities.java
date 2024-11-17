package net.marcosantos.exnihiloauto.registries;

import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoHammerBlockEntity;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoSieveBlockEntity;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoSilkerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
			DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExNihiloAuto.MODID);

	public static final RegistryObject<BlockEntityType<AutoSieveBlockEntity>> AUTO_SIEVE =
			BLOCK_ENTITIES.register(
					"auto_sieve",
					() -> BlockEntityType.Builder.of(AutoSieveBlockEntity::new, ModBlocks.AUTO_SIEVE.get())
							.build(null));

	public static final RegistryObject<BlockEntityType<AutoHammerBlockEntity>> AUTO_HAMMER =
			BLOCK_ENTITIES.register(
					"auto_hammer",
					() -> BlockEntityType.Builder.of(
							AutoHammerBlockEntity::new, ModBlocks.AUTO_HAMMER.get())
							.build(null));

	public static final RegistryObject<BlockEntityType<AutoSilkerBlockEntity>> AUTO_SILKER =
			BLOCK_ENTITIES.register(
					"auto_silker",
					() -> BlockEntityType.Builder.of(
							AutoSilkerBlockEntity::new, ModBlocks.AUTO_SILKER.get())
							.build(null));

	public static void init(IEventBus modEventBus) {
		BLOCK_ENTITIES.register(modEventBus);
	}
}
