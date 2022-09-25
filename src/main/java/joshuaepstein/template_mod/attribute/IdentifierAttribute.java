package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class IdentifierAttribute extends TAttribute.Instance<ResourceLocation> {
  public void write(CompoundTag nbt) {
    if (getBaseValue() != null)
      nbt.putString("BaseValue", getBaseValue().toString()); 
  }
  
  public void read(CompoundTag nbt) {
    if (nbt.contains("BaseValue", 8))
      setBaseValue(new ResourceLocation(nbt.getString("BaseValue"))); 
  }
}
