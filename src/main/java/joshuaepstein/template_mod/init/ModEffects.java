package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.RegistryEvent;

import java.awt.*;

public class ModEffects {
//    public static final MobEffect EFFECT = new TemplateEffect(MobEffectCategory.BENEFICIAL, Color.GRAY.getRGB(), Main.id("template_effect"));

    public static void register(RegistryEvent.Register<MobEffect> event){
        event.getRegistry().registerAll(
//                EFFECT
        );
    }

}
