package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import joshuaepstein.template_mod.config.TooltipConfig;

public class ModConfigs {
    public static TooltipConfig TOOLTIP;

    public static void register(){
        TOOLTIP = new TooltipConfig().readConfig();

        Main.LOGGER.info("Template Mod | Configs are loaded successfully!");
    }
}
