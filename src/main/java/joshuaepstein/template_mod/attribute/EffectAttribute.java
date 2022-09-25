package joshuaepstein.template_mod.attribute;

import com.google.gson.annotations.Expose;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class EffectAttribute extends PooledAttribute<List<EffectAttribute.Instance>> {
  public EffectAttribute() {}
  
  public EffectAttribute(TAttribute.Modifier<List<Instance>> modifier) {
    super(modifier);
  }
  
  public void write(CompoundTag nbt) {
    if (getBaseValue() == null)
      return; 
    CompoundTag tag = new CompoundTag();
    ListTag effectsList = new ListTag();
    getBaseValue().forEach(effect -> {
          CompoundTag effectTag = new CompoundTag();
          tag.putString("Id", effect.effect);
          effectsList.add(effectTag);
        });
    tag.put("Effects", effectsList);
    nbt.put("BaseValue", tag);
  }
  
  public void read(CompoundTag nbt) {
    if (!nbt.contains("BaseValue", 10)) {
      setBaseValue(new ArrayList<>());
      return;
    } 
    CompoundTag tag = nbt.getCompound("BaseValue");
    ListTag effectsList = tag.getList("Effects", 10);
    setBaseValue(effectsList.stream()
        .map(inbt -> (CompoundTag)inbt)
        .map(effect -> new Instance(tag.getString("Id")))
        .collect(Collectors.toList()));
  }
  
  public static Generator generator() {
    return new Generator();
  }
  
  public static Generator.Operator of(Type type) {
    return new Generator.Operator(type);
  }
  
  public static class Instance {
    @Expose
    protected String effect;
    
    public Instance(String effect) {
      this.effect = effect;
    }
    
    public Instance(MobEffect effect) {
      this(effect.getRegistryName().toString());
    }
    
    public String getId() {
      return this.effect;
    }
    
    public MobEffect toEffect() {
      return Registry.MOB_EFFECT.getOptional(new ResourceLocation(this.effect)).orElse(null);
    }
    
    public String toString() {
      return "Instance{effect='" + this.effect + '\'' + '}';
    }
  }
  
  public static class Generator extends PooledAttribute.Generator<List<Instance>, Generator.Operator> {
    public List<Instance> getDefaultValue(Random random) {
      return new ArrayList<>();
    }

    public static class Operator extends PooledAttribute.Generator.Operator<List<Instance>> {
      @Expose
      protected String type;

      public Operator(Type type) {
        this.type = type.name();
      }

      public Type getType() {
        return Type.getByName(this.type).orElseThrow(() -> new IllegalStateException("Unknown type \"" + this.type + "\""));
      }

      public List<Instance> apply(List<Instance> value, List<Instance> modifier) {
        if (getType() == Type.SET)
          return modifier;
        if (getType() == Type.MERGE) {
          List<Instance> res = new ArrayList<>(value);
          res.addAll(modifier);
          return res;
        }
        return value;
      }
    }
  }

  public static class Operator extends PooledAttribute.Generator.Operator<List<Instance>> {
    @Expose
    protected String type;

    public Operator(Type type) {
      this.type = type.name();
    }

    public Type getType() {
      return Type.getByName(this.type).orElseThrow(() -> new IllegalStateException("Unknown type \"" + this.type + "\""));
    }

    public List<Instance> apply(List<Instance> value, List<Instance> modifier) {
      if (getType() == Type.SET)
        return modifier;
      if (getType() == Type.MERGE) {
        List<Instance> res = new ArrayList<>(value);
        res.addAll(modifier);
        return res;
      } 
      return value;
    }
  }
  
  public enum Type {
    SET, MERGE;
    
    public static Optional<Type> getByName(String name) {
      for (Type value : values()) {
        if (value.name().equalsIgnoreCase(name))
          return Optional.of(value); 
      } 
      return Optional.empty();
    }
  }
}
