package joshuaepstein.template_mod.attribute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;
import java.util.function.Supplier;

public class TAttribute<T, I extends TAttribute.Instance<T>> {
  private final ResourceLocation id;

  private final Supplier<I> instance;

  private final List<TAttribute<T, I>> modifiers;

  public TAttribute(ResourceLocation id, Supplier<I> instance) {
    this(id, instance, new TAttribute[0]);
  }

  public TAttribute(ResourceLocation id, Supplier<I> instance, TAttribute<T, I>... modifiers) {
    this.id = id;
    this.instance = instance;
    this.modifiers = new ArrayList<>(Arrays.asList(modifiers));
  }

  public ResourceLocation getId() {
    return this.id;
  }

  protected String getTagKey() {
    return "Attributes";
  }

  public Optional<I> get(CompoundTag nbt) {
    if (nbt == null || !nbt.contains(getTagKey(), 9))
      return Optional.empty();
    ListTag attributesList = nbt.getList(getTagKey(), 10);
    for (Tag element : attributesList) {
      CompoundTag tag = (CompoundTag)element;
      if (tag.getString("Id").equals(getId().toString())) {
        Instance instance = this.instance.get();
        instance.parent = this;
        instance.delegate = tag;
        instance.read(tag);
        return Optional.of((I)instance);
      }
    }
    return Optional.empty();
  }

  public boolean exists(CompoundTag nbt) {
    return get(nbt).isPresent();
  }

  public I getOrDefault(CompoundTag nbt, T value) {
    return getOrDefault(nbt, () -> value);
  }

  public I getOrDefault(CompoundTag nbt, Supplier<T> value) {
    return get(nbt).orElse((I)((Instance)this.instance.get()).setBaseValue(value.get()));
  }

  public I getOrCreate(CompoundTag nbt, T value) {
    return getOrCreate(nbt, () -> value);
  }

  public I getOrCreate(CompoundTag nbt, Supplier<T> value) {
    return get(nbt).orElseGet(() -> create(nbt, value));
  }

  public I create(CompoundTag nbt, T value) {
    return create(nbt, () -> value);
  }

  public I create(CompoundTag nbt, Supplier<T> value) {
    if (!nbt.contains(getTagKey(), 9))
      nbt.put(getTagKey(), new ListTag());
    ListTag attributesList = nbt.getList(getTagKey(), 10);
    CompoundTag attributeNBT = attributesList.stream().map(element -> (CompoundTag)element).filter(tag -> tag.getString("Id").equals(getId().toString())).findFirst().orElseGet(() -> {
      CompoundTag tag = new CompoundTag();
      attributesList.add(tag);
      return tag;
    });
    Instance instance = this.instance.get();
    instance.parent = this;
    instance.delegate = attributeNBT;
    instance.setBaseValue(value.get());
    return (I)instance;
  }

  public Optional<I> get(ItemStack stack) {
    CompoundTag nbt = stack.getTagElement("TemplateMod");
    if (nbt == null || !nbt.contains(getTagKey(), 9))
      return Optional.empty();
    ListTag attributesList = nbt.getList(getTagKey(), 10);
    for (Tag element : attributesList) {
      CompoundTag tag = (CompoundTag)element;
      if (tag.getString("Id").equals(getId().toString())) {
        Instance instance = this.instance.get();
        instance.parent = this;
        instance.delegate = tag;
        instance.read(tag);
        return Optional.of((I)instance);
      }
    }
    return Optional.empty();
  }

  public Optional<T> getBase(ItemStack stack) {
    return get(stack).map(Instance::getBaseValue);
  }

  public Optional<T> getValue(ItemStack stack) {
    return get(stack).map(attribute -> attribute.getValue(stack));
  }

  public boolean exists(ItemStack stack) {
    return get(stack).isPresent();
  }

  public I getOrDefault(ItemStack stack, T value) {
    return getOrDefault(stack, () -> value);
  }

  public I getOrDefault(ItemStack stack, Random random, Instance.Generator<T> generator) {
    return getOrDefault(stack, () -> generator.generate(stack, random));
  }

  public I getOrDefault(ItemStack stack, Supplier<T> value) {
    return get(stack).orElse((I)((Instance)this.instance.get()).setBaseValue(value.get()));
  }

  public I getOrCreate(ItemStack stack, T value) {
    return getOrCreate(stack, () -> value);
  }

  public I getOrCreate(ItemStack stack, Random random, Instance.Generator<T> generator) {
    return getOrCreate(stack, () -> generator.generate(stack, random));
  }

  public I getOrCreate(ItemStack stack, Supplier<T> value) {
    return get(stack).orElseGet(() -> create(stack, value));
  }

  public I create(ItemStack stack, T value) {
    return create(stack, () -> value);
  }

  public I create(ItemStack stack, Random random, Instance.Generator<T> generator) {
    return create(stack, () -> generator.generate(stack, random));
  }

  public I create(ItemStack stack, Supplier<T> value) {
    CompoundTag nbt = stack.getOrCreateTagElement("TemplateMod");
    if (!nbt.contains(getTagKey(), 9))
      nbt.put(getTagKey(), new ListTag());
    ListTag attributesList = nbt.getList(getTagKey(), 10);
    CompoundTag attributeNBT = attributesList.stream().map(element -> (CompoundTag)element).filter(tag -> tag.getString("Id").equals(getId().toString())).findFirst().orElseGet(() -> {
      CompoundTag tag = new CompoundTag();
      attributesList.add(tag);
      return tag;
    });
    Instance instance = this.instance.get();
    instance.parent = this;
    instance.delegate = attributeNBT;
    instance.setBaseValue(value.get());
    return (I)instance;
  }

  public static abstract class Instance<T> implements INBTSerializable<CompoundTag>, Modifier<T> {
    protected TAttribute<T, ? extends Instance<T>> parent;

    protected T baseValue;

    private Modifier<T> modifier;

    protected CompoundTag delegate;

    protected Instance() {}

    protected Instance(Modifier<T> modifier) {
      this.modifier = modifier;
    }

    public final CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      nbt.putString("Id", this.parent.id.toString());
      write(nbt);
      return nbt;
    }

    public final void deserializeNBT(CompoundTag nbt) {
      read(nbt);
    }

    public T getBaseValue() {
      return this.baseValue;
    }

    public Instance<T> setBaseValue(T baseValue) {
      this.baseValue = baseValue;
      updateNBT();
      return this;
    }

    public T getValue(ItemStack stack) {
      T value = getBaseValue();
      if (this.parent == null)
        return value;
      for (TAttribute<T, ? extends Instance<T>> modifier : this.parent.modifiers) {
        Optional<? extends Instance<T>> instance = modifier.get(stack);
        if (instance.isPresent())
          value = instance.get().apply(stack, instance.get(), value);
      }
      return value;
    }

    public T apply(ItemStack stack, Instance<T> parent, T value) {
      return (this.modifier == null) ? value : this.modifier.apply(stack, parent, value);
    }

    public void updateNBT() {
      if (this.delegate == null)
        return;
      CompoundTag nbt = serializeNBT();
      for (String key : nbt.getAllKeys()) {
        Tag value = nbt.get(key);
        if (value != null)
          this.delegate.put(key, value);
      }
    }

    public abstract void write(CompoundTag param1CompoundTag);

    public abstract void read(CompoundTag param1CompoundTag);

    @FunctionalInterface
    public interface Generator<T> {
      T generate(ItemStack param2ItemStack, Random param2Random);
    }
  }

  @FunctionalInterface
  public interface Modifier<T> {
    T apply(ItemStack param1ItemStack, Instance<T> param1Instance, T param1T);
  }

  @FunctionalInterface
  public interface Generator<T> {
    T generate(ItemStack param1ItemStack, Random param1Random);
  }
}
