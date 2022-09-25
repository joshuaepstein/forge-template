package joshuaepstein.template_mod.attribute;

import com.google.gson.annotations.Expose;
import net.minecraft.nbt.CompoundTag;

import java.util.Optional;
import java.util.Random;

public class EnumAttribute<E extends Enum<E>> extends PooledAttribute<E> {
  private final Class<E> enumClass;
  
  public EnumAttribute(Class<E> enumClass) {
    this.enumClass = enumClass;
  }
  
  public EnumAttribute(Class<E> enumClass, TAttribute.Modifier<E> modifier) {
    super(modifier);
    this.enumClass = enumClass;
  }
  
  public Class<E> getEnumClass() {
    return this.enumClass;
  }
  
  public void write(CompoundTag nbt) {
    nbt.putString("BaseValue", getBaseValue().name());
  }
  
  public void read(CompoundTag nbt) {
    setBaseValue(getEnumConstant(nbt.getString("BaseValue")));
  }
  
  public E getEnumConstant(String value) {
    try {
      return Enum.valueOf(getEnumClass(), value);
    } catch (Exception e) {
      Enum[] arrayOfEnum = getEnumClass().getEnumConstants();
      return (arrayOfEnum.length == 0) ? null : (E)arrayOfEnum[0];
    } 
  }
  
  public static <E extends Enum<E>> Generator<E> generator(Class<E> enumClass) {
    return new Generator<>();
  }
  
  public static <E extends Enum<E>> Generator.Operator<E> of(Type type) {
    return new Generator.Operator<>(type);
  }
  
  public static class Generator<E extends Enum<E>> extends PooledAttribute.Generator<E, Generator.Operator<E>> {
    public E getDefaultValue(Random random) {
      return null;
    }
    
    public static class Operator<E extends Enum<E>> extends PooledAttribute.Generator.Operator<E> {
      @Expose
      protected String type;
      
      public Operator(Type type) {
        this.type = type.name();
      }

      public Type getType() {
        return Type.getByName(this.type).orElseThrow(() -> new IllegalStateException("Unknown type \"" + this.type + "\""));
      }

      public E apply(E value, E modifier) {
        if (getType() == Type.SET)
          return modifier;
        return value;
      }
    }
  }

  public static class Operator<E extends Enum<E>> extends PooledAttribute.Generator.Operator<E> {
    @Expose
    protected String type;

    public Operator(Type type) {
      this.type = type.name();
    }

    public Type getType() {
      return Type.getByName(this.type).orElseThrow(() -> new IllegalStateException("Unknown type \"" + this.type + "\""));
    }

    public E apply(E value, E modifier) {
      if (getType() == Type.SET)
        return modifier; 
      return value;
    }
  }
  
  public enum Type {
    SET;
    
    public static Optional<Type> getByName(String name) {
      for (Type value : values()) {
        if (value.name().equalsIgnoreCase(name))
          return Optional.of(value); 
      } 
      return Optional.empty();
    }
  }
}
