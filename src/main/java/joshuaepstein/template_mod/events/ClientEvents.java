package joshuaepstein.template_mod.events;

import com.google.common.collect.Lists;
import joshuaepstein.template_mod.Main;
import joshuaepstein.template_mod.init.ModConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
	private static final ResourceLocation OVERLAY_ICONS = Main.id("textures/gui/overlay_icons.png");

	@SubscribeEvent
	public static void onColorHandlerRegister(ColorHandlerEvent.Item event) {
//		ModModels.registerItemColors(event.getItemColors());
	}

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		ModConfigs.TOOLTIP.getTooltipString(event.getItemStack().getItem()).ifPresent((str) -> {
			List<Component> tooltip = event.getToolTip();
			List<String> added = Lists.reverse(Lists.newArrayList(str.split("\n")));
			if (!added.isEmpty()) {
				tooltip.add(1, TextComponent.EMPTY);
				for (String newStr : added) {
					tooltip.add(1, (new TextComponent(newStr)).withStyle(ChatFormatting.GRAY));
				}
			}
		});
	}

}
