package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistryKeyAttribute<T> extends TAttribute.Instance<ResourceKey<T>> {
  public void write(CompoundTag nbt) {
    if (getBaseValue() != null) {
      CompoundTag valueNBT = new CompoundTag();
      valueNBT.putString("Parent", getBaseValue().getRegistryName().toString());
      valueNBT.putString("Identifier", getBaseValue().location().toString());
      nbt.put("BaseValue", valueNBT);
    } 
  }
  
  public void read(CompoundTag nbt) {
    if (nbt.contains("BaseValue", 10)) {
      CompoundTag valueNBT = nbt.getCompound("BaseValue");
      setBaseValue(ResourceKey.create(
              ResourceKey.createRegistryKey(new ResourceLocation(valueNBT.getString("Parent"))), new ResourceLocation(valueNBT
              .getString("Identifier"))));
    } 
  }
}
