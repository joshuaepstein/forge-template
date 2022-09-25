package joshuaepstein.template_mod.attribute;

import com.google.gson.annotations.Expose;
import net.minecraft.nbt.CompoundTag;

import java.util.Optional;
import java.util.Random;

public class BooleanAttribute extends PooledAttribute<Boolean> {
  public BooleanAttribute() {}
  
  public BooleanAttribute(TAttribute.Modifier<Boolean> modifier) {
    super(modifier);
  }
  
  public void write(CompoundTag nbt) {
    nbt.putBoolean("BaseValue", getBaseValue().booleanValue());
  }
  
  public void read(CompoundTag nbt) {
    setBaseValue(Boolean.valueOf(nbt.getBoolean("BaseValue")));
  }
  
  public static Generator generator() {
    return new Generator();
  }
  
  public static Generator.Operator of(Type type) {
    return new Generator.Operator(type);
  }
  
  public static class Generator extends PooledAttribute.Generator<Boolean, Generator.Operator> {
    public Boolean getDefaultValue(Random random) {
      return Boolean.valueOf(false);
    }
    
    public static class Operator extends PooledAttribute.Generator.Operator<Boolean> {
      @Expose
      protected String type;
      
      public Operator(Type type) {
        this.type = type.name();
      }

      public Type getType() {
        return Type.getByName(this.type).orElseThrow(() -> new IllegalStateException("Unknown type \"" + this.type + "\""));
      }

      public Boolean apply(Boolean value, Boolean modifier) {
        if (getType() == Type.SET)
          return modifier;
        if (getType() == Type.AND)
          return Boolean.valueOf(value.booleanValue() & modifier.booleanValue());
        if (getType() == Type.OR)
          return Boolean.valueOf(value.booleanValue() | modifier.booleanValue());
        if (getType() == Type.XOR)
          return Boolean.valueOf(value.booleanValue() ^ modifier.booleanValue());
        if (getType() == Type.NAND)
          return Boolean.valueOf(((value.booleanValue() & modifier.booleanValue())));
        if (getType() == Type.NOR)
          return Boolean.valueOf(((value.booleanValue() | modifier.booleanValue())));
        if (getType() == Type.XNOR)
          return Boolean.valueOf((value == modifier));
        return value;
      }
    }
  }

  public static class Operator extends PooledAttribute.Generator.Operator<Boolean> {
    @Expose
    protected String type;

    public Operator(Type type) {
      this.type = type.name();
    }

    public Type getType() {
      return Type.getByName(this.type).orElseThrow(() -> new IllegalStateException("Unknown type \"" + this.type + "\""));
    }

    public Boolean apply(Boolean value, Boolean modifier) {
      if (getType() == Type.SET)
        return modifier;
      if (getType() == Type.AND)
        return Boolean.valueOf(value.booleanValue() & modifier.booleanValue());
      if (getType() == Type.OR)
        return Boolean.valueOf(value.booleanValue() | modifier.booleanValue());
      if (getType() == Type.XOR)
        return Boolean.valueOf(value.booleanValue() ^ modifier.booleanValue());
      if (getType() == Type.NAND)
        return Boolean.valueOf(((value.booleanValue() & modifier.booleanValue())));
      if (getType() == Type.NOR)
        return Boolean.valueOf(((value.booleanValue() | modifier.booleanValue())));
      if (getType() == Type.XNOR)
        return Boolean.valueOf((value == modifier)); 
      return value;
    }
  }
  
  public enum Type {
    SET, AND, OR, XOR, NAND, NOR, XNOR;
    
    public static Optional<Type> getByName(String name) {
      for (Type value : values()) {
        if (value.name().equalsIgnoreCase(name))
          return Optional.of(value); 
      } 
      return Optional.empty();
    }
  }
}
