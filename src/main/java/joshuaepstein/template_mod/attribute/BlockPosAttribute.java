package joshuaepstein.template_mod.attribute;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class BlockPosAttribute extends TAttribute.Instance<BlockPos> {
  public void write(CompoundTag nbt) {
    if (getBaseValue() != null)
      nbt.putIntArray("BaseValue", new int[] { getBaseValue().getX(),
            getBaseValue().getY(),
            getBaseValue().getZ() }); 
  }
  
  public void read(CompoundTag nbt) {
    if (nbt.contains("BaseValue", 11)) {
      int[] pos = nbt.getIntArray("BaseValue");
      setBaseValue(new BlockPos(pos[0], pos[1], pos[2]));
    } 
  }
}
