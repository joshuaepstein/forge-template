package joshuaepstein.template_mod;

import com.mojang.logging.LogUtils;
import joshuaepstein.template_mod.events.RegistryEvents;
import joshuaepstein.template_mod.init.ModCommands;
import joshuaepstein.template_mod.init.ModEnchantments;
import joshuaepstein.template_mod.util.ServerScheduler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EventListenerProxy;
import java.util.stream.Collectors;

@Mod(Main.MOD_ID)
public class Main {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "template_mod";


    public Main() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, RegistryEvents::onCommandRegister);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(ServerScheduler.INSTANCE::onServerTick);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::onPlayerLogin);
        registerDeferredRegistries(bus);
    }


    public void registerDeferredRegistries(IEventBus bus){
        ModEnchantments.ENCHANTMENTS.register(bus);
    }


    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static String sId(String name) {
        return MOD_ID + ":" + name;
    }

    public static int format(double value, int scale){
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros().intValue();
    }

    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        ServerLevel world = player.getLevel();


    }


}
