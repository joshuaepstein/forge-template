package joshuaepstein.template_mod.attribute;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public abstract class TModifier<T, I extends TAttribute.Instance<T>> extends TAttribute<T, I> {
  public TModifier(ResourceLocation id, Supplier<I> instance) {
    super(id, instance);
  }
  
  protected String getTagKey() {
    return "Modifiers";
  }
  
  public abstract T apply(Instance<T> paramInstance, T paramT);
}
