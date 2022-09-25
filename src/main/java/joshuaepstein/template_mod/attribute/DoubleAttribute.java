package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class DoubleAttribute extends NumberAttribute<Double> {
  public DoubleAttribute() {}
  
  public DoubleAttribute(TAttribute.Modifier<Double> modifier) {
    super(modifier);
  }
  
  public void write(CompoundTag nbt) {
    nbt.putDouble("BaseValue", getBaseValue().doubleValue());
  }
  
  public void read(CompoundTag nbt) {
    setBaseValue(Double.valueOf(nbt.getDouble("BaseValue")));
  }
  
  public static Generator generator() {
    return new Generator();
  }
  
  public static Generator.Operator of(Type type) {
    return new Generator.Operator(type);
  }
  
  public static class Generator extends NumberAttribute.Generator<Double, Generator.Operator> {
    public Double getDefaultValue(Random random) {
      return Double.valueOf(0.0D);
    }
    
    public static class Operator extends NumberAttribute.Generator.Operator<Double> {
      public Operator(Type type) {
        super(type);
      }
      
      public Double apply(Double value, Double modifier) {
        if (getType() == Type.SET)
          return modifier; 
        if (getType() == Type.ADD)
          return Double.valueOf(value.doubleValue() + modifier.doubleValue()); 
        if (getType() == Type.MULTIPLY)
          return Double.valueOf(value.doubleValue() * modifier.doubleValue()); 
        return value;
      }
    }
  }
  
  public static class Operator extends NumberAttribute.Generator.Operator<Double> {
    public Operator(Type type) {
      super(type);
    }
    
    public Double apply(Double value, Double modifier) {
      if (getType() == Type.SET)
        return modifier; 
      if (getType() == Type.ADD)
        return Double.valueOf(value.doubleValue() + modifier.doubleValue()); 
      if (getType() == Type.MULTIPLY)
        return Double.valueOf(value.doubleValue() * modifier.doubleValue()); 
      return value;
    }
  }
}
