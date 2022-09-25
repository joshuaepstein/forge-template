package joshuaepstein.template_mod.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Tooltip {

    public static void add(List<Component> tooltips, Component component){
        tooltips.add(component);
    }

    public static void add(List<Component> tooltips, Component... components){
        tooltips.addAll(Arrays.asList(components));
    }

    public static void addPredicate(List<Component> tooltips, Component component, String toMatch, String match){
        if(Objects.equals(match, toMatch)){
            tooltips.add(component);
        }
    }

    public static void addPredicate(List<Component> tooltips, Component component, boolean addTooltip){

    }

    public static void add(List<Component> tooltips, Component component, int addTooltip){

        for(int i = 0; i < addTooltip; i++){
            if(i != 0){
                tooltips.add(new TextComponent(" "));
            }
            tooltips.add(component);
        }
    }

    public static void addBlank(List<Component> tooltips){
        tooltips.add(new TextComponent(" "));
    }

    public static void addBlank(List<Component> tooltips, int amount){
        int amount1 = amount;
        while(amount1 != 0){
            tooltips.add(new TextComponent(" "));
            amount1 -= 1;
        }
    }

    public static void removeTooltip(List<Component> tooltips, Component toRemove){
        tooltips.remove(toRemove);
    }

}
