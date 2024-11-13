package lazy.exnihiloauto.setup;

import net.minecraftforge.common.ToolType;

public class ModToolTypes {

    public static ToolType REINFORCED_HAMMER;

    public static void init() {
        REINFORCED_HAMMER = ToolType.get("reinforced_hammer");
    }
}
