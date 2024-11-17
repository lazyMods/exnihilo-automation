package net.marcosantos.exnihiloauto;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Maps;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.HashMap;
import java.util.Map;

public class Configs {

	public static ForgeConfigSpec COMMON;

	public static Map<String, Map<String, ForgeConfigSpec.ConfigValue>> machineConfigs = Maps.newHashMap();

	public static ForgeConfigSpec.IntValue AUTO_HAMMER_SPEED;
	public static ForgeConfigSpec.IntValue AUTO_HAMMER_ENERGY_CAPACITY;

	public static ForgeConfigSpec.IntValue AUTO_SIEVE_SPEED;
	public static ForgeConfigSpec.IntValue AUTO_SIEVE_ENERGY_CAPACITY;

	public static ForgeConfigSpec.IntValue AUTO_SILKER_SPEED;
	public static ForgeConfigSpec.IntValue AUTO_SILKER_ENERGY_CAPACITY;

	static {
		var builder = new ForgeConfigSpec.Builder();

		builder.push(ExNihiloAuto.MODID);
		AUTO_HAMMER_SPEED = builder.comment("Speed - Time it takes to handle the job.").defineInRange("auto_sieve_job_time", 100, 1, Integer.MAX_VALUE);
		AUTO_HAMMER_ENERGY_CAPACITY = builder.comment("Energy Capacity.").defineInRange("auto_sieve_energy_capacity", 10000, 1000, Integer.MAX_VALUE);
		AUTO_SIEVE_SPEED = builder.comment("Speed - Time it takes to handle the job.").defineInRange("auto_hammer_job_time", 100, 1, Integer.MAX_VALUE);
		AUTO_SIEVE_ENERGY_CAPACITY = builder.comment("Energy Capacity.").defineInRange("auto_hammer_energy_capacity", 10000, 1000, Integer.MAX_VALUE);
		AUTO_SILKER_SPEED = builder.comment("Speed - Time it takes to handle the job.").defineInRange("auto_silker_job_time", 100, 1, Integer.MAX_VALUE);
		AUTO_SILKER_ENERGY_CAPACITY = builder.comment("Energy Capacity.").defineInRange("auto_silker_energy_capacity", 10000, 1000, Integer.MAX_VALUE);
		builder.pop();

		COMMON = builder.build();

		new MachineConfigEntry("autohammer", "speed", AUTO_HAMMER_SPEED);
		new MachineConfigEntry("autohammer", "energy_capacity", AUTO_HAMMER_ENERGY_CAPACITY);
		new MachineConfigEntry("autosieve", "speed", AUTO_SIEVE_SPEED);
		new MachineConfigEntry("autosieve", "energy_capacity", AUTO_SIEVE_ENERGY_CAPACITY);
		new MachineConfigEntry("autosilker", "speed", AUTO_SILKER_SPEED);
		new MachineConfigEntry("autosilker", "energy_capacity", AUTO_SILKER_ENERGY_CAPACITY);
	}

	public static void registerAndLoadConfig() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON);
		var config = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(ExNihiloAuto.MODID.concat("-common.toml"))).sync().writingMode(WritingMode.REPLACE).build();
		config.load();
		COMMON.setConfig(config);
	}

	@SuppressWarnings("rawtypes")
	public static class MachineConfigEntry {

		public String machineType;
		public String name;
		public ForgeConfigSpec.ConfigValue configSpec;

		public MachineConfigEntry(String machineType, String name, ForgeConfigSpec.ConfigValue configSpec) {
			this.machineType = machineType;
			this.name = name;
			this.configSpec = configSpec;

			if (!Configs.machineConfigs.containsKey(machineType)) {
				Map<String, ForgeConfigSpec.ConfigValue> map = new HashMap<>();
				map.put(name, configSpec);
				Configs.machineConfigs.put(machineType, map);
			} else {
				Map<String, ForgeConfigSpec.ConfigValue> machineTypeConfigs = Configs.machineConfigs.get(this.machineType);
				machineTypeConfigs.put(name, configSpec);
			}
		}
	}
}
