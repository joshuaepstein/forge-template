package joshuaepstein.template_mod.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerUtil {

    public static boolean playerHasItem(PlayerEntity player, Item item) {
        for (final ItemStack stack : player.inventory.items) {
            if (stack != null && stack.getItem().equals(item)) {
                return true;
            }
        }
        for (final EquipmentSlotType slotType : EquipmentSlotType.values()) {
            if (player.getItemBySlot(slotType).getItem() == item) {
                return true;
            }
        }

        return false;
    }

    public static List<ItemStack> getStacksFromPlayer(PlayerEntity player, Item item) {

        final List<ItemStack> items = new ArrayList<>();

        for (final ItemStack stack : player.inventory.items) {
            if (stack != null && stack.getItem() == item) {
                items.add(stack);
            }
        }

        for (final EquipmentSlotType slotType : EquipmentSlotType.values()) {
            final ItemStack stack = player.getItemBySlot(slotType);
            if (stack.getItem() == item) {
                items.add(stack);
            }
        }

        return items;
    }

//    public static void removeAmountFromPlayer(PlayerEntity player, Item item, int amount) {
//        int removedAmount = 0;
//        for (final ItemStack stack : player.inventory.items) {
//            if (stack != null && stack.getItem() == item) {
//                if(amount <= 64){
//                    stack.shrink(amount);
//                    removedAmount -= stack.getCount();
//                    amount -= amount;
//                    if(removedAmount <= 0){
//                        stack.shrink(amount);
//                    }
//                } else {
//                    stack.shrink(64);
//                    amount -= 64;
//                }
//            }
//        }
//        int sendMessage = 0;
//        if(sendMessage != 0){
//            player.sendMessage(new StringTextComponent("There is currently an error with removing the correct amount of payment items from the players inventory").withStyle(TextFormatting.GREEN), player.getUUID());
//            sendMessage += 1;
//        }
//        for (final EquipmentSlotType slotType : EquipmentSlotType.values()) {
//            final ItemStack stack = player.getItemBySlot(slotType);
//            if (stack.getItem() == item) {
//                if(amount <= 64){
//                    stack.shrink(amount);
//                    amount -= amount;
//                } else {
//                    stack.shrink(64);
//                    amount -= 64;
//                }
//            }
//        }
//    }

    public static void removeAmountFromPlayer(PlayerEntity player, Item itemToRemove, int toRemove){
        int itemsRemoved = 0;
        for(final ItemStack stack : player.inventory.items){
            if(stack != null && stack.getItem() == itemToRemove){
                if(toRemove <=64){
                    stack.shrink(toRemove);
                    itemsRemoved += stack.getCount();
                    toRemove -= toRemove;
                    if(itemsRemoved <= 0)
                        stack.shrink(toRemove);
                } else {
                    stack.shrink(64);
                    toRemove -= 64;
                }
            }
        }

        for(final EquipmentSlotType slotType : EquipmentSlotType.values()){
            final ItemStack stack = player.getItemBySlot(slotType);
            if(stack.getItem() == itemToRemove){
                if(toRemove <= 64){
                    stack.shrink(toRemove);
                    toRemove -= toRemove;
                } else {
                    stack.shrink(64);
                    toRemove -= 64;
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static ClientPlayerEntity getClientPlayer () {
        return Minecraft.getInstance().player;
    }

    @OnlyIn(Dist.CLIENT)
    public static ResourceLocation getPlayerSkinLocation (GameProfile profile) {
        // Validate the profile first.
        if (profile != null) {
            final Minecraft minecraft = Minecraft.getInstance();
            // Load skin data about the profile.
            final Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(profile);

            // If the loaded data has a skin, return that.
            if (map.containsKey(Type.SKIN)) {
                return minecraft.getSkinManager().registerTexture(map.get(Type.SKIN), Type.SKIN);
            } else {
                return DefaultPlayerSkin.getDefaultSkin(PlayerEntity.createPlayerUUID(profile));
            }
        }
        // Default to the legacy steve skin.
        return DefaultPlayerSkin.getDefaultSkin();
    }


}
