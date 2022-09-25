package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class BoundingBoxAttribute extends TAttribute.Instance<BoundingBox> {
  public void write(CompoundTag nbt) {
    if (getBaseValue() != null)
      nbt.put("BaseValue", createTag(getBaseValue()));
  }
  
  public void read(CompoundTag nbt) {
    if (nbt.contains("BaseValue", 11))
      setBaseValue(boundingBox(nbt.getIntArray("BaseValue")));
  }

  public IntArrayTag createTag(BoundingBox value) {
    return new IntArrayTag(new int[]{value.minX(), value.minY(), value.minZ(), value.maxX(), value.maxY(), value.maxZ()});
  }

  public BoundingBox boundingBox(int[] p_i43000_1_) {
    return new BoundingBox(p_i43000_1_[0],
            p_i43000_1_[1],
            p_i43000_1_[2],
            p_i43000_1_[3],
            p_i43000_1_[4],
            p_i43000_1_[5]);
  }
}
