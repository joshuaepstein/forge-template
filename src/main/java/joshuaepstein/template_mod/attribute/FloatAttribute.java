package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class FloatAttribute extends NumberAttribute<Float> {
  public FloatAttribute() {}
  
  public FloatAttribute(TAttribute.Modifier<Float> modifier) {
    super(modifier);
  }
  
  public void write(CompoundTag nbt) {
    nbt.putFloat("BaseValue", getBaseValue().floatValue());
  }
  
  public void read(CompoundTag nbt) {
    setBaseValue(Float.valueOf(nbt.getFloat("BaseValue")));
  }
  
  public static Generator generator() {
    return new Generator();
  }
  
  public static Generator.Operator of(Type type) {
    return new Generator.Operator(type);
  }
  
  public static class Generator extends NumberAttribute.Generator<Float, Generator.Operator> {
    public Float getDefaultValue(Random random) {
      return Float.valueOf(0.0F);
    }
    
    public static class Operator extends NumberAttribute.Generator.Operator<Float> {
      public Operator(Type type) {
        super(type);
      }
      
      public Float apply(Float value, Float modifier) {
        if (getType() == Type.SET)
          return modifier; 
        if (getType() == Type.ADD)
          return Float.valueOf(value.floatValue() + modifier.floatValue()); 
        if (getType() == Type.MULTIPLY)
          return Float.valueOf(value.floatValue() * modifier.floatValue()); 
        return value;
      }
    }
  }
  
  public static class Operator extends NumberAttribute.Generator.Operator<Float> {
    public Operator(Type type) {
      super(type);
    }
    
    public Float apply(Float value, Float modifier) {
      if (getType() == Type.SET)
        return modifier; 
      if (getType() == Type.ADD)
        return Float.valueOf(value.floatValue() + modifier.floatValue()); 
      if (getType() == Type.MULTIPLY)
        return Float.valueOf(value.floatValue() * modifier.floatValue()); 
      return value;
    }
  }
}
