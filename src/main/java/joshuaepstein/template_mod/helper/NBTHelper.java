package joshuaepstein.template_mod.helper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.Function;

public class NBTHelper {

    public static <T, N extends CompoundTag> Map<UUID, T> readMap(CompoundTag nbt, String name, Class<N> nbtType, Function<N, T> mapper) {
        Map<UUID, T> res = new HashMap<>();
        ListTag uuidList = nbt.getList(name + "Keys", Tag.TAG_STRING);
        ListTag valuesList = (ListTag) nbt.get(name + "Values");

        if (uuidList.size() != valuesList.size()) {
            throw new IllegalStateException("Map doesn't have the same amount of keys as values");
        }

        for (int i = 0; i < uuidList.size(); i++) {
            res.put(UUID.fromString(uuidList.get(i).getAsString()), mapper.apply((N) valuesList.get(i)));
        }

        return res;
    }

    public static <T, N extends CompoundTag> void writeMap(CompoundTag nbt, String name, Map<UUID, T> map, Class<N> nbtType, Function<T, N> mapper) {
        ListTag uuidList = new ListTag();
        ListTag valuesList = new ListTag();
        map.forEach((key, value) -> {
            uuidList.add(StringTag.valueOf(key.toString()));
            valuesList.add(mapper.apply(value));
        });
        nbt.put(name + "Keys", uuidList);
        nbt.put(name + "Values", valuesList);
    }

    public static <T, N extends CompoundTag> List<T> readList(CompoundTag nbt, String name, Class<N> nbtType, Function<N, T> mapper) {
        List<T> res = new LinkedList<>();
        ListTag ListTag = (ListTag) nbt.get(name);

        for (int i = 0; i < ListTag.size(); i++) {
            res.add(mapper.apply((N)ListTag.get(i)));
        }

        return res;
    }

    public static <T, N extends CompoundTag> void writeList(CompoundTag nbt, String name, Collection<T> list, Class<N> nbtType, Function<T, N> mapper) {
        ListTag ListTag = new ListTag();
        list.forEach(item -> ListTag.add(mapper.apply(item)));
        nbt.put(name, ListTag);
    }

    public static void setTag(ItemStack stack, String key, CompoundTag value) {
        getTagCompound(stack).put(key, value);
    }

    public static void setByte(ItemStack stack, String key, byte value) {
        getTagCompound(stack).putByte(key, value);
    }

    public static void setShort(ItemStack stack, String key, short value) {
        getTagCompound(stack).putShort(key, value);
    }

    public static void setInt(ItemStack stack, String key, int value) {
        getTagCompound(stack).putInt(key, value);
    }

    public static void setLong(ItemStack stack, String key, long value) {
        getTagCompound(stack).putLong(key, value);
    }

    public static void setFloat(ItemStack stack, String key, float value) {
        getTagCompound(stack).putFloat(key, value);
    }

    public static void setDouble(ItemStack stack, String key, double value) {
        getTagCompound(stack).putDouble(key, value);
    }

    public static void setString(ItemStack stack, String key, String value) {
        getTagCompound(stack).putString(key, value);
    }

    public static void setByteArray(ItemStack stack, String key, byte[] value) {
        getTagCompound(stack).putByteArray(key, value);
    }

    public static void setIntArray(ItemStack stack, String key, int[] value) {
        getTagCompound(stack).putIntArray(key, value);
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        getTagCompound(stack).putBoolean(key, value);
    }

    public static CompoundTag getTag(ItemStack stack, String key) {
        return stack.hasTag() ? (CompoundTag) getTagCompound(stack).get(key) : null;
    }

    public static byte getByte(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getByte(key) : 0;
    }

    public static short getShort(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getShort(key) : 0;
    }

    public static int getInt(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getInt(key) : 0;
    }

    public static long getLong(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getLong(key) : 0L;
    }

    public static float getFloat(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getFloat(key) : 0.0F;
    }

    public static double getDouble(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getDouble(key) : 0.0D;
    }

    public static String getString(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getString(key) : "";
    }

    public static byte[] getByteArray(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getByteArray(key) : new byte[0];
    }

    public static int[] getIntArray(ItemStack stack, String key) {
        return stack.hasTag() ? getTagCompound(stack).getIntArray(key) : new int[0];
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        return stack.hasTag() && getTagCompound(stack).getBoolean(key);
    }

    public static boolean hasKey(ItemStack stack, String key) {
        return stack.hasTag() && getTagCompound(stack).contains(key);
    }

    public static void flipBoolean(ItemStack stack, String key) {
        setBoolean(stack, key, !getBoolean(stack, key));
    }

    public static void removeTag(ItemStack stack, String key) {
        if (hasKey(stack, key)) {
            getTagCompound(stack).remove(key);
        }
    }

    public static void validateCompound(ItemStack stack) {
        if (!stack.hasTag()) {
            CompoundTag tag = new CompoundTag();
            stack.setTag(tag);
        }
    }

    public static CompoundTag getTagCompound(ItemStack stack) {
        validateCompound(stack);
        return stack.getTag();
    }
}
