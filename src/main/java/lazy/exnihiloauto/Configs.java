package lazy.exnihiloauto;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import lombok.val;
import lombok.var;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class Configs {

    public static ForgeConfigSpec COMMON;

    public static ForgeConfigSpec.IntValue AUTO_HAMMER_SPEED;
    public static ForgeConfigSpec.IntValue AUTO_HAMMER_ENERGY_CAPACITY;

    public static ForgeConfigSpec.IntValue AUTO_SIEVE_SPEED;
    public static ForgeConfigSpec.IntValue AUTO_SIEVE_ENERGY_CAPACITY;

    static {
        var builder = new ForgeConfigSpec.Builder();

        builder.push(Ref.MOD_ID);
        AUTO_HAMMER_SPEED = builder.comment("Speed - Time it takes to handle the job.").defineInRange("auto_sieve_job_time", 100, 1, Integer.MAX_VALUE);
        AUTO_HAMMER_ENERGY_CAPACITY = builder.comment("Energy Capacity.").defineInRange("auto_sieve_energy_capacity", 10000, 1000, Integer.MAX_VALUE);
        AUTO_SIEVE_SPEED = builder.comment("Speed - Time it takes to handle the job.").defineInRange("auto_hammer_job_time", 100, 1, Integer.MAX_VALUE);
        AUTO_SIEVE_ENERGY_CAPACITY = builder.comment("Energy Capacity.").defineInRange("auto_hammer_energy_capacity", 10000, 1000, Integer.MAX_VALUE);
        builder.pop();

        COMMON = builder.build();
    }

    public static void registerAndLoadConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON);
        val config = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(Ref.MOD_ID.concat("-common.toml"))).sync().writingMode(WritingMode.REPLACE).build();
        config.load();
        COMMON.setConfig(config);
    }
}
