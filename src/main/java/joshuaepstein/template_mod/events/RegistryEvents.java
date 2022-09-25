package joshuaepstein.template_mod.events;

import joshuaepstein.template_mod.init.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RegistryEvents {

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event){
        ModBlocks.registerBlocks(event);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){
        ModItems.register(event);
        ModBlocks.registerBlockItems(event);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event){
        ModModels.setupRenderLayers();
    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event){
        ModSounds.registerSounds(event);
        ModSounds.registerSoundTypes();
    }

    @SubscribeEvent
    public static void onFeatureRegister(RegistryEvent.Register<Feature<?>> event){
    }

    @SubscribeEvent
    public static void onContainerRegister(RegistryEvent.Register<MenuType<?>> event){
        ModContainers.register(event);
    }

    @SubscribeEvent
    public static void onBlockEntityRegister(RegistryEvent.Register<BlockEntityType<?>> event){
        ModBlocks.registerBlockEntities(event);
    }

    @SubscribeEvent
    public static void onRecipeRegister(RegistryEvent.Register<RecipeSerializer<?>> event){
        ModRecipes.registerRecipes(event);
        ModRecipes.Serializer.registerRecipes(event);
    }

    @SubscribeEvent
    public static void onEffectRegister(RegistryEvent.Register<MobEffect> event){
        ModEffects.register(event);
    }

    @SubscribeEvent
    public static void onAttributeRegister(RegistryEvent.Register<Attribute> event){
        Attribute attr = Attributes.MAX_HEALTH;
        if(attr instanceof RangedAttribute)
            ((RangedAttribute) attr).maxValue = 1.7976931348623157E308D;
    }

    @SubscribeEvent
    public static void onRegisterGlobalLootModifiersSerializers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
        ModLootModifiers.registerGlobalModifiers(event);
    }

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityType<?>> event){
        ModEntities.register(event);
    }

    @SubscribeEvent
    public static void onTextureStitchRegister(TextureStitchEvent.Pre event){
        ModSprites.register(event);
    }

    public static void onCommandRegister(RegisterCommandsEvent event){
        ModCommands.registerCommands(event.getDispatcher(), event.getEnvironment());
    }
}
