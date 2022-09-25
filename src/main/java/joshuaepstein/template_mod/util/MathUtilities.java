package joshuaepstein.template_mod.util;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtilities {
  private static final Random rand = new Random();

  public static float randomFloat(float min, float max) {
    if (min >= max)
      return min;
    return min + rand.nextFloat() * (max - min);
  }

  public static int getRandomInt(int min, int max) {
    if (min >= max)
      return min;
    return min + rand.nextInt(max - min);
  }

  public static double randomDouble(double min, double max){
    if(min >= max) return min;
    return min + rand.nextDouble() * (max - min);
  }

  public static double map(double value, double x0, double y0, double x1, double y1) {
    return x1 + (y1 - x1) * (value - x0) / (y0 - x0);
  }

  public static double length(Vector2f vec) {
    return Math.sqrt((vec.x * vec.x + vec.y * vec.y));
  }

  public static double extractYaw(Vector3d vec) {
    return Math.atan2(vec.z(), vec.x());
  }

  public static double extractPitch(Vector3d vec) {
    return Math.asin(vec.y() / vec.length());
  }

  public static Vector3d rotatePitch(Vector3d vec, float pitch) {
    float f = MathHelper.cos(pitch);
    float f1 = MathHelper.sin(pitch);
    double d0 = vec.x();
    double d1 = vec.y() * f + vec.z() * f1;
    double d2 = vec.z() * f - vec.y() * f1;
    return new Vector3d(d0, d1, d2);
  }

  public static Vector3d rotateYaw(Vector3d vec, float yaw) {
    float f = MathHelper.cos(yaw);
    float f1 = MathHelper.sin(yaw);
    double d0 = vec.x() * f + vec.z() * f1;
    double d1 = vec.y();
    double d2 = vec.z() * f - vec.x() * f1;
    return new Vector3d(d0, d1, d2);
  }

  public static Vector3d rotateRoll(Vector3d vec, float roll) {
    float f = MathHelper.cos(roll);
    float f1 = MathHelper.sin(roll);
    double d0 = vec.x() * f + vec.y() * f1;
    double d1 = vec.y() * f - vec.x() * f1;
    double d2 = vec.z();
    return new Vector3d(d0, d1, d2);
  }

  /**
   * Calculates the distance between two Vec3 positions.
   *
   * @param firstPos: The first position to work with.
   * @param secondPos: The second position to work with.
   * @return double: The distance between the two provided locations.
   */
  public static double getDistanceBetweenPoints (Vector3d firstPos, Vector3d secondPos) {

    final double distanceX = firstPos.x - secondPos.x;
    final double distanceY = firstPos.y - secondPos.y;
    final double distanceZ = firstPos.z - secondPos.z;

    return Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
  }

  public static double round (double value, int places) {

    return value >= 0 && places > 0 ? BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).doubleValue() : value;
  }

  /**
   * Gets the middle integer between two other integers. The order is not important.
   *
   * @param first: The first integer.
   * @param second: The second integer.
   * @return int: The integer that is between the two provided integers.
   */
  public static int getAverage (int first, int second) {

    return Math.round((first + second) / 2.0F);
  }

  /**
   * Converts time in ticks to a human readable string.
   *
   * @param ticks: The amount of ticks to convert.
   * @return String: A human readable version of the time.
   */
  public static String ticksToTime (int ticks) {

    final int seconds = ticks / 20;
    final int minutes = seconds / 60;
    return minutes + ":" + seconds;
  }

  /**
   * Gets the percentage of an integer. Result is an integer and decimal is lost.
   *
   * @param value The value to get the percentage of.
   * @param total The total/max value.
   * @return The percentage as an integer.
   */
  public static int getPercentage (int value, int total) {

    return (int) ((float) value / (float) total * 100f);
  }

  /**
   * Gets the distance in world for an amount of pixels. A basic block is a cubic meter, and
   * each pixel is 1/16th of a block.
   *
   * @param pixels The amount of pixels
   * @return The distance in game for those pixels.
   */
  public static double getPixelDistance (int pixels) {

    return pixels / 16d;
  }

  /**
   * Creates a bounding box using pixel size.
   *
   * @param minX The min X pos.
   * @param minY The min Y pos.
   * @param minZ The min Z pos.
   * @param maxX The max X pos.
   * @param maxY The max Y pos.
   * @param maxZ The max Z pos.
   * @return A bounding box that is made to a pixel specific size.
   */
  public static AxisAlignedBB getBoundsForPixels (int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {

    return new AxisAlignedBB(getPixelDistance(minX), getPixelDistance(minY), getPixelDistance(minZ), getPixelDistance(maxX), getPixelDistance(maxY), getPixelDistance(maxZ));
  }

  /**
   * Creates a voxel shape that has been rotated a given direction. The default/base input is
   * expected to be oriented north. Thanks to Gigaherz for writing the original version of
   * this code.
   *
   * @param facing The direction to rotate the shape.
   * @param x1 The min x coordinate.
   * @param y1 The min y coordinate.
   * @param z1 The min z coordinate.
   * @param x2 The max x coordinate.
   * @param y2 The max y coordinate.
   * @param z2 The max z coordinate.
   * @return A voxel shape that has been rotated in the specified direction.
   */
  public static VoxelShape rotateShape (Direction facing, double x1, double y1, double z1, double x2, double y2, double z2) {

    switch (facing) {
      case NORTH:
        return Block.box(x1, y1, z1, x2, y2, z2);
      case EAST:
        return Block.box(16 - z2, y1, x1, 16 - z1, y2, x2);
      case SOUTH:
        return Block.box(16 - x2, y1, 16 - z2, 16 - x1, y2, 16 - z1);
      case WEST:
        return Block.box(z1, y1, 16 - x2, z2, y2, 16 - x1);
      default:
        throw new IllegalArgumentException("Can not rotate face in direction " + facing.name());
    }
  }

}
