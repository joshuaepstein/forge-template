package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class LongAttribute extends NumberAttribute<Long> {
  public LongAttribute() {}
  
  public LongAttribute(TAttribute.Modifier<Long> modifier) {
    super(modifier);
  }
  
  public void write(CompoundTag nbt) {
    nbt.putLong("BaseValue", getBaseValue().longValue());
  }
  
  public void read(CompoundTag nbt) {
    setBaseValue(Long.valueOf(nbt.getLong("BaseValue")));
  }
  
  public static Generator generator() {
    return new Generator();
  }
  
  public static Generator.Operator of(Type type) {
    return new Generator.Operator(type);
  }
  
  public static class Generator extends NumberAttribute.Generator<Long, Generator.Operator> {
    public Long getDefaultValue(Random random) {
      return Long.valueOf(0L);
    }
    
    public static Operator of(Type type) {
      return new Operator(type);
    }
    
    public static class Operator extends NumberAttribute.Generator.Operator<Long> {
      public Operator(Type type) {
        super(type);
      }
      
      public Long apply(Long value, Long modifier) {
        if (getType() == Type.SET)
          return modifier; 
        if (getType() == Type.ADD)
          return Long.valueOf(value.longValue() + modifier.longValue()); 
        if (getType() == Type.MULTIPLY)
          return Long.valueOf(value.longValue() * modifier.longValue()); 
        return value;
      }
    }
  }
  
  public static class Operator extends NumberAttribute.Generator.Operator<Long> {
    public Operator(Type type) {
      super(type);
    }
    
    public Long apply(Long value, Long modifier) {
      if (getType() == Type.SET)
        return modifier; 
      if (getType() == Type.ADD)
        return Long.valueOf(value.longValue() + modifier.longValue()); 
      if (getType() == Type.MULTIPLY)
        return Long.valueOf(value.longValue() * modifier.longValue()); 
      return value;
    }
  }
}
