package joshuaepstein.template_mod.init;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModScreens {

    public static void register(final FMLClientSetupEvent event){
//        MenuScreens.register(ModContainers.TEMPLATE_CONTAINER, TemplateScreen::new);
    }

    public static void registerOverlays() {
//        MinecraftForge.EVENT_BUS.register(TemplateOverlay.class);
    }

}
