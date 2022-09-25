package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModLootModifiers {
    public static void registerGlobalModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
        IForgeRegistry<GlobalLootModifierSerializer<?>> registry = event.getRegistry();
//        registry.register(new TemplateLootModifier.Serializer().setRegistryName(Main.id("template_loot_modifier")));
    }
}
