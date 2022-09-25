package joshuaepstein.template_mod.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE)
public class DamageOverTimeHelper {
  private static final Map<ResourceKey<Level>, List<DamageOverTimeEntry>> worldEntries = new HashMap<>();

  public static void applyDamageOverTime(LivingEntity target, DamageSource damageSource, float totalDamage, int seconds) {
    DamageOverTimeEntry entry = new DamageOverTimeEntry(seconds * 20, damageSource, target.getId(), totalDamage / seconds);
    (worldEntries.computeIfAbsent(target.getCommandSenderWorld().dimension(), key -> new ArrayList())).add(entry);
  }

  @SubscribeEvent
  public static void onWorldTick(TickEvent.WorldTickEvent event) {
    if (event.phase == TickEvent.Phase.END)
      return;
    Level world = event.world;
    if (world.isClientSide())
      return;
    List<DamageOverTimeEntry> entries = worldEntries.computeIfAbsent(world.dimension(), key -> new ArrayList());
    entries.forEach(rec$ -> (rec$).decrement());
    entries.forEach(entry -> {
      if (entry.ticks % 20 == 0) {
        Entity e = world.getEntity(entry.entityId);
        if (e instanceof LivingEntity && e.isAlive()) {
//              DamageUtil.shotgunAttack(e, ());
        } else {
          entry.invalidate();
        }
      }
    });
    entries.removeIf(entry -> !entry.valid);
  }

  private static class DamageOverTimeEntry {
    private int ticks;

    private final DamageSource source;

    private final int entityId;

    private final float damagePerSecond;

    private boolean valid = true;

    public DamageOverTimeEntry(int ticks, DamageSource source, int entityId, float damagePerSecond) {
      this.ticks = ticks;
      this.source = source;
      this.entityId = entityId;
      this.damagePerSecond = damagePerSecond;
    }

    private void decrement() {
      this.ticks--;
      this.valid = (this.valid && this.ticks > 0);
    }

    private void invalidate() {
      this.valid = false;
    }
  }
}
