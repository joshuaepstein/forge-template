package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.Function;
import java.util.function.Supplier;

public class CompoundAttribute<T extends INBTSerializable<CompoundTag>> extends TAttribute.Instance<T> {
  protected Function<CompoundTag, T> read;
  
  public CompoundAttribute(Function<CompoundTag, T> read) {
    this.read = read;
  }
  
  public static <T extends INBTSerializable<CompoundTag>> CompoundAttribute<T> of(Supplier<T> supplier) {
    return new CompoundAttribute(nbt -> {
          INBTSerializable iNBTSerializable = supplier.get();
          iNBTSerializable.deserializeNBT((Tag)nbt);
          return iNBTSerializable;
        });
  }
  
  public void write(CompoundTag nbt) {
    if (getBaseValue() != null)
      nbt.put("BaseValue", ((INBTSerializable)getBaseValue()).serializeNBT()); 
  }
  
  public void read(CompoundTag nbt) {
    if (nbt.contains("BaseValue", 10))
      setBaseValue(this.read.apply(nbt.getCompound("BaseValue"))); 
  }
}
