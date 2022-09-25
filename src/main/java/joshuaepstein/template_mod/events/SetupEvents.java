package joshuaepstein.template_mod.events;

import joshuaepstein.template_mod.Main;
import joshuaepstein.template_mod.init.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEvents {

    @SubscribeEvent
    public static void setupClient(final FMLClientSetupEvent event){
        Main.LOGGER.info("Template Mod | Setup Client");
        ModScreens.register(event);
        ModScreens.registerOverlays();
        ModKeybinds.register(event);
        ModBlocks.registerBlockEntityRenderers();
        MinecraftForge.EVENT_BUS.register(InputEvents.class);
    }

    @SubscribeEvent
    public static void setupCommon(final FMLCommonSetupEvent event){
        Main.LOGGER.info("Template Mod | Setup Common");
        ModConfigs.register();
        ModNetwork.initialize();
    }

    @SubscribeEvent
    public static void setupDedicatedServer(final FMLDedicatedServerSetupEvent event){
        Main.LOGGER.info("Template Mod | Setup Dedicated");
    }

}
