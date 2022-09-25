package joshuaepstein.template_mod.util;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Stack;

@EventBusSubscriber
public class BlockDropCaptureHelper {
  private static final Stack<NonNullList<ItemStack>> capturing = new Stack<>();

  @SubscribeEvent
  public static void onDrop(EntityJoinWorldEvent event) {
    if (event.getWorld() instanceof ServerLevel && event.getEntity() instanceof ItemEntity) {
      ItemStack itemStack = ((ItemEntity)event.getEntity()).getItem();
      if (!capturing.isEmpty()) {
        event.setCanceled(true);
        if (!itemStack.isEmpty() &&
                !capturing.isEmpty())
          capturing.peek().add(itemStack);
        event.getEntity().remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
      }
    }
  }

  public static void startCapturing() {
    capturing.push(NonNullList.create());
  }

  public static NonNullList<ItemStack> getCapturedStacksAndStop() {
    return capturing.pop();
  }
}
