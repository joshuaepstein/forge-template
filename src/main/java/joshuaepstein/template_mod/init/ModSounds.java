package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class ModSounds {

//    public static SoundEvent SOUND;

    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
//        SOUND = registerSound(event, Main.id("sound"));
    }

    public static void registerSoundTypes() {
//        LAZY_SOUND.initialize(0.45F, 1.0F, BREAK, null, null, HIT, null);
    }

    /* ---------------------------- */

    private static SoundEvent registerSound(RegistryEvent.Register<SoundEvent> event, String name){
        return registerSound(event, Main.id(name));
    }

    private static SoundEvent registerSound(RegistryEvent.Register<SoundEvent> event, ResourceLocation soundName) {
        SoundEvent soundEvent = new SoundEvent(soundName);
        soundEvent.setRegistryName(soundName);
        event.getRegistry().register(soundEvent);
        return soundEvent;
    }

}
