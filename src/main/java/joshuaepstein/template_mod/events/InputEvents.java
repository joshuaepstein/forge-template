package joshuaepstein.template_mod.events;

import joshuaepstein.template_mod.init.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class InputEvents {
    private static boolean isShiftDown;

    public static boolean isShiftDown() {
        return isShiftDown;
    }

    @SubscribeEvent
    public static void onShiftKey(InputEvent.KeyInputEvent event) {
        if (event.getKey() == 340)
            if (event.getAction() == 1) {
                isShiftDown = true;
            } else if (event.getAction() == 0) {
                isShiftDown = false;
            }
    }

    @SubscribeEvent
    public static void onKey(InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;
        onInput(minecraft, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouse(InputEvent.MouseInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;
        onInput(minecraft, event.getButton(), event.getAction());
    }

    private static void onInput(Minecraft minecraft, int key, int action) {
        if (minecraft.screen != null || key == -1)
            return;
//        if (ModKeybinds.templateKeybind.getKey().getValue() == key) {
//            if (action != 1)
//                return;
//            ModNetwork.CHANNEL.sendToServer(new OpenRandomItemScreenMessage());
//        }
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;
        double scrollDelta = event.getScrollDelta();
//        if (ModKeybinds.abilityKey.isDown()) {
//            if (minecraft.screen == null)
//                if (scrollDelta < 0.0D) {
//                    ModNetwork.CHANNEL.sendToServer(new AbilityKeyMessage(false, false, false, true));
//                } else {
//                    ModNetwork.CHANNEL.sendToServer(new AbilityKeyMessage(false, false, true, false));
//                }
//            event.setCanceled(true);
//        }
    }
}
