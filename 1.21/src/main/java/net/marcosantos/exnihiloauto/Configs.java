package net.marcosantos.exnihiloauto;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Configs {

	public static final ModConfigSpec COMMON;

	public static final Map<String, Map<String, ModConfigSpec.ConfigValue>> machineConfigs = Maps.newHashMap();

	public static final ModConfigSpec.IntValue AUTO_HAMMER_SPEED;
	public static final ModConfigSpec.IntValue AUTO_HAMMER_ENERGY_CAPACITY;
	public static final ModConfigSpec.IntValue AUTO_SIEVE_SPEED;
	public static final ModConfigSpec.IntValue AUTO_SIEVE_ENERGY_CAPACITY;
	public static final ModConfigSpec.IntValue AUTO_SILKER_SPEED;
	public static final ModConfigSpec.IntValue AUTO_SILKER_ENERGY_CAPACITY;

	static {
		var builder = new ModConfigSpec.Builder();

		builder.push(ExNihiloAuto.MODID);
		AUTO_HAMMER_SPEED = builder.comment("Speed - Time it takes to handle the job.")
				.defineInRange("auto_sieve_job_time", 100, 1, Integer.MAX_VALUE);
		AUTO_HAMMER_ENERGY_CAPACITY = builder.comment("Energy Capacity.")
				.defineInRange("auto_sieve_energy_capacity", 10000, 1000, Integer.MAX_VALUE);
		AUTO_SIEVE_SPEED = builder.comment("Speed - Time it takes to handle the job.")
				.defineInRange("auto_hammer_job_time", 100, 1, Integer.MAX_VALUE);
		AUTO_SIEVE_ENERGY_CAPACITY = builder.comment("Energy Capacity.")
				.defineInRange("auto_hammer_energy_capacity", 10000, 1000, Integer.MAX_VALUE);
		AUTO_SILKER_SPEED = builder.comment("Speed - Time it takes to handle the job.")
				.defineInRange("auto_silker_job_time", 100, 1, Integer.MAX_VALUE);
		AUTO_SILKER_ENERGY_CAPACITY = builder.comment("Energy Capacity.")
				.defineInRange("auto_silker_energy_capacity", 10000, 1000, Integer.MAX_VALUE);
		builder.pop();

		COMMON = builder.build();

		new MachineConfigEntry("autohammer", "speed", AUTO_HAMMER_SPEED);
		new MachineConfigEntry("autohammer", "energy_capacity", AUTO_HAMMER_ENERGY_CAPACITY);
		new MachineConfigEntry("autosieve", "speed", AUTO_SIEVE_SPEED);
		new MachineConfigEntry("autosieve", "energy_capacity", AUTO_SIEVE_ENERGY_CAPACITY);
		new MachineConfigEntry("autosilker", "speed", AUTO_SILKER_SPEED);
		new MachineConfigEntry("autosilker", "energy_capacity", AUTO_SILKER_ENERGY_CAPACITY);
	}

	public static void registerConfig(ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, COMMON);
	}

	public record MachineConfigEntry(String machineType, String name, ModConfigSpec.ConfigValue configSpec) {

		public MachineConfigEntry(String machineType, String name, ModConfigSpec.ConfigValue configSpec) {
			this.machineType = machineType;
			this.name = name;
			this.configSpec = configSpec;

			if (!Configs.machineConfigs.containsKey(machineType)) {
				Map<String, ModConfigSpec.ConfigValue> map = new HashMap<>();
				map.put(name, configSpec);
				Configs.machineConfigs.put(machineType, map);
			} else {
				Map<String, ModConfigSpec.ConfigValue> machineTypeConfigs = Configs.machineConfigs.get(this.machineType);
				machineTypeConfigs.put(name, configSpec);
			}
		}
	}
}
