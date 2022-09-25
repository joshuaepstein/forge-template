package joshuaepstein.template_mod.util;

import com.mojang.serialization.Codec;
import joshuaepstein.superpowers.Main;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;

import java.util.Optional;
import java.util.function.Consumer;

public class CodecUtils {
  public static <T> T readNBT(Codec<T> codec, CompoundNBT tag, String targetKey, T defaultValue) {
    return readNBT(codec, tag.get(targetKey)).orElse(defaultValue);
  }
  
  public static <T> T readNBT(Codec<T> codec, INBT nbt, T defaultValue) {
    return readNBT(codec, nbt).orElse(defaultValue);
  }
  
  public static <T> Optional<T> readNBT(Codec<T> codec, INBT nbt) {
    return codec.parse(NBTDynamicOps.INSTANCE, nbt)
      .resultOrPartial(Main.LOGGER::error);
  }
  
  public static <T> void writeNBT(Codec<T> codec, T value, CompoundNBT targetTag, String targetKey) {
    writeNBT(codec, value, nbt -> targetTag.put(targetKey, nbt));
  }
  
  public static <T> void writeNBT(Codec<T> codec, T value, Consumer<INBT> successConsumer) {
    codec.encodeStart(NBTDynamicOps.INSTANCE, value)
      .resultOrPartial(Main.LOGGER::error)
      .ifPresent(successConsumer);
  }
  
  public static <T> INBT writeNBT(Codec<T> codec, T value) {
    return codec.encodeStart(NBTDynamicOps.INSTANCE, value)
      .getOrThrow(false, Main.LOGGER::error);
  }
}
