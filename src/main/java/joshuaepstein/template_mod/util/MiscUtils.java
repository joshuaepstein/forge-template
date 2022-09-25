package joshuaepstein.template_mod.util;

import com.google.common.collect.Iterables;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class MiscUtils {

    public static boolean inventoryContains(IInventory inventory, Predicate<ItemStack> filter) {
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            if (filter.test(inventory.getItem(slot)))
                return true;
        }
        return false;
    }

    public static void broadcast(ITextComponent message) {
        MinecraftServer srv = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        if (srv != null)
            srv.getPlayerList().broadcastMessage(message, ChatType.CHAT, Util.NIL_UUID);
    }

    public static void giveItem(PlayerEntity player, ItemStack stack){
        giveItem(player, stack);
    }

    public static void giveItem(PlayerEntity player, List<ItemStack> listStacks){
        giveItem(player, listStacks);
    }

    public static void giveItem(ServerPlayerEntity player, ItemStack stack) {
        stack = stack.copy();
        if (player.inventory.add(stack) && stack.isEmpty()) {
            stack.setCount(1);
            ItemEntity dropped = player.drop(stack, false);
            if (dropped != null)
                dropped.makeFakeItem();
            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player

                    .getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.inventoryMenu.broadcastChanges();
        } else {
            ItemEntity dropped = player.drop(stack, false);
            if (dropped != null) {
                dropped.setNoPickUpDelay();
                dropped.setOwner(player.getUUID());
            }
        }
    }

    public static void giveItem(ServerPlayerEntity player, List<ItemStack> listStacks) {
        for(ItemStack stack : listStacks){
            stack = stack.copy();
            if (player.inventory.add(stack) && stack.isEmpty()) {
                stack.setCount(1);
                ItemEntity dropped = player.drop(stack, false);
                if (dropped != null)
                    dropped.makeFakeItem();
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player

                        .getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.inventoryMenu.broadcastChanges();
            } else {
                ItemEntity dropped = player.drop(stack, false);
                if (dropped != null) {
                    dropped.setNoPickUpDelay();
                    dropped.setOwner(player.getUUID());
                }
            }
        }
    }



    public static Point2D.Float getMidpoint(Rectangle r) {
        return new Point2D.Float(r.x + r.width / 2.0F, r.y + r.height / 2.0F);
    }

    @Nullable
    public static <T> T getRandomEntry(Collection<T> collection, Random rand) {
        if (collection.isEmpty())
            return null;
        int randomPick = rand.nextInt(collection.size());
        return Iterables.get(collection, randomPick, null);
    }

    public static <T extends Enum<T>> T getEnumEntry(Class<T> enumClass, int index) {
        Enum[] arrayOfEnum = enumClass.getEnumConstants();
        return (T)arrayOfEnum[MathHelper.clamp(index, 0, arrayOfEnum.length - 1)];
    }

    public static boolean isPlayerFakeMP(ServerPlayerEntity player) {
        if (player instanceof net.minecraftforge.common.util.FakePlayer)
            return true;
        if (player.connection == null)
            return true;
        try {
            player.getIpAddress().length();
            player.connection.connection.getRemoteAddress().toString();
            if (!player.connection.connection.channel().isOpen())
                return true;
        } catch (Exception exc) {
            return true;
        }
        return false;
    }

    public static Color blendColors(Color color1, Color color2, float color1Ratio) {
        return new Color(blendColors(color1.getRGB(), color2.getRGB(), color1Ratio), true);
    }

    public static int blendColors(int color1, int color2, float color1Ratio) {
        float ratio1 = MathHelper.clamp(color1Ratio, 0.0F, 1.0F);
        float ratio2 = 1.0F - ratio1;
        int a1 = (color1 & 0xFF000000) >> 24;
        int r1 = (color1 & 0xFF0000) >> 16;
        int g1 = (color1 & 0xFF00) >> 8;
        int b1 = color1 & 0xFF;
        int a2 = (color2 & 0xFF000000) >> 24;
        int r2 = (color2 & 0xFF0000) >> 16;
        int g2 = (color2 & 0xFF00) >> 8;
        int b2 = color2 & 0xFF;
        int a = MathHelper.clamp(Math.round(a1 * ratio1 + a2 * ratio2), 0, 255);
        int r = MathHelper.clamp(Math.round(r1 * ratio1 + r2 * ratio2), 0, 255);
        int g = MathHelper.clamp(Math.round(g1 * ratio1 + g2 * ratio2), 0, 255);
        int b = MathHelper.clamp(Math.round(b1 * ratio1 + b2 * ratio2), 0, 255);
        return a << 24 | r << 16 | g << 8 | b;
    }

    public static BlockPos getRandomPos(MutableBoundingBox box, Random r) {
        return getRandomPos(AxisAlignedBB.of(box), r);
    }

    public static BlockPos getRandomPos(AxisAlignedBB box, Random r) {
        int sizeX = Math.max(1, MathHelper.floor(box.getXsize()));
        int sizeY = Math.max(1, MathHelper.floor(box.getYsize()));
        int sizeZ = Math.max(1, MathHelper.floor(box.getZsize()));
        return new BlockPos(box.minX + (double)r.nextInt(sizeX), box.minY + (double)r.nextInt(sizeY), box.minZ + (double)r.nextInt(sizeZ));
    }

    public static Vector3d getRandomOffset(AxisAlignedBB box, Random r) {
        return new Vector3d(box.minX + (double)r.nextFloat() * (box.maxX - box.minX), box.minY + (double)r.nextFloat() * (box.maxY - box.minY), box.minZ + (double)r.nextFloat() * (box.maxZ - box.minZ));
    }

    public static Vector3d getRandomOffset(BlockPos pos, Random r) {
        return new Vector3d((double)((float)pos.getX() + r.nextFloat()), (double)((float)pos.getY() + r.nextFloat()), (double)((float)pos.getZ() + r.nextFloat()));
    }

    public static Vector3d getRandomOffset(BlockPos pos, Random r, float scale) {
        float x = (float)pos.getX() + 0.5F - scale / 2.0F + r.nextFloat() * scale;
        float y = (float)pos.getY() + 0.5F - scale / 2.0F + r.nextFloat() * scale;
        float z = (float)pos.getZ() + 0.5F - scale / 2.0F + r.nextFloat() * scale;
        return new Vector3d((double)x, (double)y, (double)z);
    }

}
