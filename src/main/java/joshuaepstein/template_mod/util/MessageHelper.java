package joshuaepstein.template_mod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class MessageHelper {
    public static ITextComponent tooltipDots(int amount, TextFormatting formatting) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            text.append("\u2b22 ");
        }
        return new StringTextComponent(text.toString()).withStyle(formatting);
    }

    public static void sendMessage(StringTextComponent message, PlayerEntity player){
        player.sendMessage(message, player.getUUID());
    }
}
