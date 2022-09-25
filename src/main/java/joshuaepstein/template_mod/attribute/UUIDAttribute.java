package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class UUIDAttribute extends TAttribute.Instance<UUID> {
  public void write(CompoundTag nbt) {
    if (getBaseValue() != null)
      nbt.putString("BaseValue", getBaseValue().toString()); 
  }
  
  public void read(CompoundTag nbt) {
    if (nbt.contains("BaseValue", 8))
      setBaseValue(UUID.fromString(nbt.getString("BaseValue"))); 
  }
}
