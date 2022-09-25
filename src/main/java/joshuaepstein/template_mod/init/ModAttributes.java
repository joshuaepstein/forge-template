package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import joshuaepstein.template_mod.attribute.TAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModAttributes {
    public static Map<ResourceLocation, TAttribute<?, ?>> REGISTRY = new HashMap<>();

//    public static SAttribute<Boolean, BooleanAttribute> TEMPLATE;

    public static void register(RegistryEvent.Register<Attribute> event) {
//        TEMPLATE = register(Main.id("imbued"), BooleanAttribute::new);
    }

    /* ------------------------------------------- */

    private static Attribute register(IForgeRegistry<Attribute> registry, String name, Attribute attribute) {
        registry.register(attribute.setRegistryName(Main.id(name)));
        return attribute;
    }

    @SafeVarargs
    private static <T, I extends TAttribute.Instance<T>> TAttribute<T, I> register(ResourceLocation id, Supplier<I> instance, TAttribute<T, I>... modifiers) {
        TAttribute<T, I> attribute = new TAttribute<>(id, instance, modifiers);
        REGISTRY.put(id, attribute);
        return attribute;
    }

}
