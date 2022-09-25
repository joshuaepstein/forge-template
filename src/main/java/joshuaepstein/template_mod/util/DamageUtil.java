package joshuaepstein.template_mod.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;
import java.util.function.Function;

public class DamageUtil {
  public static <T extends Entity> void shotgunAttack(T e, Consumer<T> attackFn) {
    shotgunAttackApply(e, entity -> {
          attackFn.accept(entity);
          return null;
        });
  }
  
  public static <T extends Entity, R> R shotgunAttackApply(T e, Function<T, R> attackFn) {
    int prevHurtTicks = (e).invulnerableTime;
    if (e instanceof LivingEntity) {
      LivingEntity le = (LivingEntity)e;
      float prevDamage = le.lastHurt;
      (e).invulnerableTime = 0;
      le.lastHurt = 0.0F;
      try {
        return attackFn.apply(e);
      } finally {
        (e).invulnerableTime = prevHurtTicks;
        le.lastHurt = prevDamage;
      } 
    } 
    (e).invulnerableTime = 0;
    try {
      return attackFn.apply(e);
    } finally {
      (e).invulnerableTime = prevHurtTicks;
    } 
  }
}
