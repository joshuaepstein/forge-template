package joshuaepstein.template_mod.attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import joshuaepstein.template_mod.util.gson.IgnoreEmpty;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.Random;

public class StringAttribute extends PooledAttribute<String> {
  public StringAttribute() {}
  
  public StringAttribute(TAttribute.Modifier<String> modifier) {
    super(modifier);
  }
  
  public void write(CompoundTag nbt) {
    nbt.putString("BaseValue", getBaseValue());
  }
  
  public void read(CompoundTag nbt) {
    setBaseValue(nbt.getString("BaseValue"));
  }
  
  public static class Generator extends PooledAttribute.Generator<String, Generator.Operator> {
    public String getDefaultValue(Random random) {
      return "";
    }

    @Override
    public String generate(ItemStack param2ItemStack, Random param2Random) {
      return null;
    }

    public static class Operator extends PooledAttribute.Generator.Operator<String> {
      @Expose
      protected String type;
      
      @Expose
      @JsonAdapter(IgnoreEmpty.StringAdapter.class)
      protected String delimiter;
      
      @Expose
      @JsonAdapter(IgnoreEmpty.StringAdapter.class)
      protected String regex;
      
      public Operator(Type type) {
        this.type = type.name();
      }

      public Type getType() {
        return Type.getByName(this.type).orElseThrow(() -> new IllegalStateException("Unknown type \"" + this.type + "\""));
      }

      public String apply(String value, String modifier) {
        if (getType() == Type.SET)
          return modifier;
        if (getType() == Type.APPEND)
          return value + modifier;
        if (getType() == Type.JOIN)
          return value + this.delimiter + modifier;
        if (getType() == Type.REPLACE_FIRST)
          return value.replaceFirst(this.regex, modifier);
        if (getType() == Type.REPLACE_ALL)
          return value.replaceAll(this.regex, modifier); 
        return value;
      }
    }
  }
  
  public enum Type {
    SET, APPEND, JOIN, REPLACE_FIRST, REPLACE_ALL;
    
    public static Optional<Type> getByName(String name) {
      for (Type value : values()) {
        if (value.name().equalsIgnoreCase(name))
          return Optional.of(value); 
      } 
      return Optional.empty();
    }
  }
}
