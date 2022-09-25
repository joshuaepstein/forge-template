package joshuaepstein.template_mod.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.UUID;

public final class SkullUtils {
    /**
     * Create a skull from an instance of Player.
     *
     * @param player The Player to use the skin from.
     * @return ItemStack An ItemStack containing a skull that represents the passed player.
     */
    public static ItemStack createSkull (Player player) {

        return createSkull(player.getGameProfile().getName(), player.getUUID());
    }

    /**
     * Creates a skull using a players UUID.
     *
     * @param name The name of the player.
     * @param uuid The UUID of the player to base the skull on.
     * @return ItemStack An ItemStack containing a skull which represents the owner of the
     *         passed UUID.
     */
    public static ItemStack createSkull (String name, UUID uuid) {

        final ItemStack stack = new ItemStack(Items.PLAYER_HEAD, 1);
        final CompoundTag ownerTag = new CompoundTag();
        ownerTag.putString("Name", name);
        ownerTag.putString("Id", uuid.toString());
        stack.getOrCreateTag().put("SkullOwner", ownerTag);
        return stack;
    }
}
