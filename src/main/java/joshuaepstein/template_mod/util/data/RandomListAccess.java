package joshuaepstein.template_mod.util.data;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;

public interface RandomListAccess<T> {
  @Nullable
  T getRandom(Random paramRandom);
  
  default Optional<T> getOptionalRandom(Random random) {
    return Optional.ofNullable(getRandom(random));
  }
  
  void forEach(BiConsumer<T, Number> paramBiConsumer);
  
  boolean removeEntry(T paramT);
  
  @Nullable
  default T removeRandom(Random random) {
    T element = getRandom(random);
    if (element != null) {
      removeEntry(element);
      return element;
    } 
    return null;
  }
}
