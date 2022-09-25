package joshuaepstein.template_mod.events;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemTooltip(ItemTooltipEvent event) {
        if ((Minecraft.getInstance()).player != null && (Minecraft.getInstance()).player.isCreative())
            return;
        for (int i = 0; i < event.getToolTip().size(); i++) {
            Component txt = event.getToolTip().get(i);
//            if (txt.getString().contains("something here"))
//                event.getToolTip().set(i, (new TextComponent("replacing with here")).setStyle(txt.getStyle()));
        }
    }
}
