package net.marcosantos.exnihiloauto.registries;

import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.world.level.block.AutoBlock;
import net.marcosantos.exnihiloauto.world.level.block.CompressedBlock;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoHammerBlockEntity;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoSieveBlockEntity;
import net.marcosantos.exnihiloauto.world.level.block.entity.AutoSilkerBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExNihiloAuto.MODID);

	public static final RegistryObject<Block> AUTO_SIEVE =
			BLOCKS.register("auto_sieve", () -> new AutoBlock(AutoSieveBlockEntity::new));
	public static final RegistryObject<Block> AUTO_HAMMER =
			BLOCKS.register("auto_hammer", () -> new AutoBlock(AutoHammerBlockEntity::new));
	public static final RegistryObject<Block> AUTO_SILKER =
			BLOCKS.register("auto_silker", () -> new AutoBlock(AutoSilkerBlockEntity::new));

	public static final RegistryObject<CompressedBlock> COMPRESSED_COBBLE =
			BLOCKS.register(
					"compressed_cobble", () -> new CompressedBlock.Cobble(CompressedBlock.Tier.COMPRESSED));
	public static final RegistryObject<CompressedBlock> HIGHLY_COMPRESSED_COBBLE =
			BLOCKS.register(
					"highly_compressed_cobble",
					() -> new CompressedBlock.Cobble(CompressedBlock.Tier.HIGHLY_COMPRESSED));
	public static final RegistryObject<CompressedBlock> ATOMIC_COMPRESSED_COBBLE =
			BLOCKS.register(
					"atomic_compressed_cobble",
					() -> new CompressedBlock.Cobble(CompressedBlock.Tier.ATOMIC_COMPRESSION));

	public static final RegistryObject<CompressedBlock> COMPRESSED_DUST =
			BLOCKS.register(
					"compressed_dust", () -> new CompressedBlock.Dust(CompressedBlock.Tier.COMPRESSED));
	public static final RegistryObject<CompressedBlock> HIGHLY_COMPRESSED_DUST =
			BLOCKS.register(
					"highly_compressed_dust",
					() -> new CompressedBlock.Dust(CompressedBlock.Tier.HIGHLY_COMPRESSED));
	public static final RegistryObject<CompressedBlock> ATOMIC_COMPRESSED_DUST =
			BLOCKS.register(
					"atomic_compressed_dust",
					() -> new CompressedBlock.Dust(CompressedBlock.Tier.ATOMIC_COMPRESSION));

	public static final RegistryObject<CompressedBlock> COMPRESSED_GRAVEL =
			BLOCKS.register(
					"compressed_gravel", () -> new CompressedBlock.Gravel(CompressedBlock.Tier.COMPRESSED));
	public static final RegistryObject<CompressedBlock> HIGHLY_COMPRESSED_GRAVEL =
			BLOCKS.register(
					"highly_compressed_gravel",
					() -> new CompressedBlock.Gravel(CompressedBlock.Tier.HIGHLY_COMPRESSED));
	public static final RegistryObject<CompressedBlock> ATOMIC_COMPRESSED_GRAVEL =
			BLOCKS.register(
					"atomic_compressed_gravel",
					() -> new CompressedBlock.Gravel(CompressedBlock.Tier.ATOMIC_COMPRESSION));

	public static final RegistryObject<CompressedBlock> COMPRESSED_SAND =
			BLOCKS.register(
					"compressed_sand", () -> new CompressedBlock.Sand(CompressedBlock.Tier.COMPRESSED));
	public static final RegistryObject<CompressedBlock> HIGHLY_COMPRESSED_SAND =
			BLOCKS.register(
					"highly_compressed_sand",
					() -> new CompressedBlock.Sand(CompressedBlock.Tier.HIGHLY_COMPRESSED));
	public static final RegistryObject<CompressedBlock> ATOMIC_COMPRESSED_SAND =
			BLOCKS.register(
					"atomic_compressed_sand",
					() -> new CompressedBlock.Sand(CompressedBlock.Tier.ATOMIC_COMPRESSION));

	public static void init(IEventBus bus) {
		BLOCKS.register(bus);
	}
}
