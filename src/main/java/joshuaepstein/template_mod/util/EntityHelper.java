package joshuaepstein.template_mod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorld;

import java.util.List;

public class EntityHelper {
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public static void changeHealth(LivingEntity entity, int healthChange){
        float health = entity.getHealth();
        entity.setHealth(health + healthChange);
        if(entity.isDeadOrDying())
            entity.die((entity.getLastDamageSource() !=null) ? entity.getLastDamageSource() : DamageSource.GENERIC);
    }

    public static <T extends Entity> T changeSize(T entity, float size) {
        entity.getDimensions(Pose.STANDING).scale(size);
        entity.refreshDimensions();
        return entity;
    }

    public static void giveItem(PlayerEntity player, ItemStack itemStack) {
        boolean added = player.inventory.add(itemStack);
        if (!added)
            player.drop(itemStack, false, false);
    }

    public static <T extends Entity> List<T> getNearby(IWorld world, Vector3i pos, float radius, Class<T> entityClass) {
        AxisAlignedBB selectBox = BOX.move(pos.getX(), pos.getY(), pos.getZ()).inflate(radius);
        return world.getEntitiesOfClass(entityClass, selectBox, entity ->
                (entity.isAlive() && !entity.isSpectator()));
    }
}
