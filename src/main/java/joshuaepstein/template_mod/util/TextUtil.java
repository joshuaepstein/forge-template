package joshuaepstein.template_mod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class TextUtil {
    static final ChatFormatting[] rainbow = new ChatFormatting[] { ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.BLUE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.DARK_PURPLE };

    public static TextComponent applyRainbowTo(String text) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            sb.append(getNextColor(i));
            sb.append(c);
        }
        return new TextComponent(sb.toString());
    }

    public static ChatFormatting getRainbow(String text){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            return getNextColor(i);
        }
        return getRainbow(text);
    }

    private static ChatFormatting getNextColor(int index){
        return rainbow[index % rainbow.length];
    }

    public static Component tooltipDots(int amount, ChatFormatting formatting) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            text.append("\u2b22 ");
        }
        return new TextComponent(text.toString())
                .withStyle(formatting);
    }
}
