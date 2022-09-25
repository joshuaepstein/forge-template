package joshuaepstein.template_mod.util;

import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class VectorHelper {
  public static Vector3d getDirectionNormalized(Vector3d destination, Vector3d origin) {
    return (new Vector3d(destination.x - origin.x, destination.y - origin.y, destination.z - origin.z));
  }

  public static Vector3d getVectorFromPos(BlockPos pos) {
    return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
  }

  public static Vector3d add(Vector3d a, Vector3d b) {
    return new Vector3d(a.x + b.x, a.y + b.y, a.z + b.z);
  }

  public static Vector3d subtract(Vector3d a, Vector3d b) {
    return new Vector3d(a.x - b.x, a.y - b.y, a.z - b.z);
  }

  public static Vector3d multiply(Vector3d velocity, float speed) {
    return new Vector3d(velocity.x * speed, velocity.y * speed, velocity.z * speed);
  }

  public static Vector3d getMovementVelocity(Vector3d current, Vector3d target, float speed) {
    return multiply(getDirectionNormalized(target, current), speed);
  }

  public static Vec2 normalize(Vec2 v) {
    float length = (float)Math.sqrt((v.x * v.x + v.y * v.y));
    return new Vec2(v.x / length, v.y / length);
  }

  public static Vec2 rotateDegrees(Vec2 v, float angleDeg) {
    float angle = (float)Math.toRadians(angleDeg);
    float cosAngle = Mth.cos(angle);
    float sinAngle = Mth.sin(angle);
    return new Vec2(v.x * cosAngle - v.y * sinAngle, v.x * sinAngle + v.y * cosAngle);
  }
}
