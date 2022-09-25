package joshuaepstein.template_mod.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber
public class PlayerActiveFlags {
    private static final Map<UUID, List<FlagTimeout>> timeouts = new HashMap();

    public PlayerActiveFlags() {
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            timeouts.forEach((playerId, flagTimeouts) -> {
                flagTimeouts.forEach(FlagTimeout::tick);
                flagTimeouts.removeIf(FlagTimeout::isFinished);
            });
        }
    }

    public static void set(Player player, Flag flag, int timeout) {
        set(player.getUUID(), flag, timeout);
    }

    public static void set(UUID playerId, Flag flag, int timeout) {
        List<FlagTimeout> flags = timeouts.computeIfAbsent(playerId, id -> new ArrayList());
        for(FlagTimeout flagTimeout : flags){
            if(flagTimeout.flag == flag){
                flagTimeout.tickTimeout = timeout;
                return;
            }
        }
        flags.add(new FlagTimeout(flag, timeout));
    }

    public static boolean isSet(Player player, Flag flag) {
        return isSet(player.getUUID(), flag);
    }

    public static boolean isSet(UUID playerId, Flag flag) {
        List<FlagTimeout> flags = timeouts.getOrDefault(playerId, Collections.emptyList());
        for(FlagTimeout timout : flags){
            if(timout.flag == flag && !timout.isFinished()){
                return true;
            }
        }
        return false;
    }

    public enum Flag {
        ATTACK_AOE,
        CHAINING_AOE
    }

    private static class FlagTimeout {
        private final Flag flag;
        private int tickTimeout;

        private FlagTimeout(Flag flag, int tickTimeout) {
            this.flag = flag;
            this.tickTimeout = tickTimeout;
        }

        private void tick() {
            --this.tickTimeout;
        }

        private boolean isFinished() {
            return this.tickTimeout <= 0;
        }
    }
}
