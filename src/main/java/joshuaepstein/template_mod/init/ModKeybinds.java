package joshuaepstein.template_mod.init;

import com.mojang.blaze3d.platform.InputConstants;
import joshuaepstein.template_mod.Main;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ModKeybinds {
    public static KeyMapping templateKeybind;

    public static void register(final FMLClientSetupEvent event){
        templateKeybind = createKeyMapping("template_binding", 296);
    }

    private static KeyMapping createKeyMapping(String name){
        return createKeyMapping(name, InputConstants.UNKNOWN.getValue());
    }

    private static KeyMapping createKeyMapping(String name, int key) {
        KeyMapping keyBind = new KeyMapping("key." + Main.MOD_ID + "." + name, key, "key.category." + Main.MOD_ID);
        ClientRegistry.registerKeyBinding(keyBind);
        return keyBind;
    }
}
