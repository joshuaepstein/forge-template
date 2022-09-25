package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class IntegerAttribute extends NumberAttribute<Integer> {
  public IntegerAttribute() {}
  
  public IntegerAttribute(TAttribute.Modifier<Integer> modifier) {
    super(modifier);
  }
  
  public void write(CompoundTag nbt) {
    nbt.putInt("BaseValue", getBaseValue());
  }
  
  public void read(CompoundTag nbt) {
    setBaseValue(Integer.valueOf(nbt.getInt("BaseValue")));
  }
  
  public static Generator generator() {
    return new Generator();
  }
  
  public static Generator.Operator of(Type type) {
    return new Generator.Operator(type);
  }
  
  public static class Generator extends NumberAttribute.Generator<Integer, Generator.Operator> {
    public Integer getDefaultValue(Random random) {
      return Integer.valueOf(0);
    }
    
    public static Operator of(Type type) {
      return new Operator(type);
    }
    
    public static class Operator extends NumberAttribute.Generator.Operator<Integer> {
      public Operator(Type type) {
        super(type);
      }
      
      public Integer apply(Integer value, Integer modifier) {
        if (getType() == Type.SET)
          return modifier; 
        if (getType() == Type.ADD)
          return Integer.valueOf(value + modifier); 
        if (getType() == Type.MULTIPLY)
          return Integer.valueOf(value * modifier); 
        return value;
      }
    }
  }
  
  public static class Operator extends NumberAttribute.Generator.Operator<Integer> {
    public Operator(Type type) {
      super(type);
    }
    
    public Integer apply(Integer value, Integer modifier) {
      if (getType() == Type.SET)
        return modifier; 
      if (getType() == Type.ADD)
        return Integer.valueOf(value + modifier); 
      if (getType() == Type.MULTIPLY)
        return Integer.valueOf(value * modifier); 
      return value;
    }
  }
}
