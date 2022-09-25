package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

public class ModEntities {

//    public static EntityType<?> TEMPLATE_ENTITY;

    public static void register(RegistryEvent.Register<EntityType<?>> event){
//        TEMPLATE_ENTITY = register("template_entity", EntityType.Builder.of(TemplateEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f), Zombie::createAttributes, event);
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder, RegistryEvent.Register<EntityType<?>> event){
        EntityType<T> entityType = builder.build(Main.sId(name));
        event.getRegistry().register(entityType.setRegistryName(Main.id(name)));
        return entityType;
    }

    private static <T extends LivingEntity> EntityType<T> register(String name, EntityType.Builder<T> builder, Supplier<AttributeSupplier.Builder> attributes, RegistryEvent.Register<EntityType<?>> event){
        EntityType<T> entityType = register(name, builder, event);
        if(attributes != null)
            ForgeHooks.FORGE_ATTRIBUTES.put(entityType, attributes.get().build());
        return entityType;
    }

    public static class Renderers {
        public static void register(final FMLClientSetupEvent event){
//            EntityRenderers.register(TEMPLATE_ENTITY, TemplateEntityRenderer::new);
        }
    }

}
