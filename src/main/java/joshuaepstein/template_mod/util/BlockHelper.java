package joshuaepstein.template_mod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class BlockHelper {
  public static Iterable<BlockPos> getSphericalPositions(BlockPos center, float radius) {
    return getOvalPositions(center, radius, radius);
  }

  public static Iterable<BlockPos> getOvalPositions(BlockPos center, float widthRadius, float heightRadius) {
    Collection<BlockPos> positions = new Stack<>();
    int wRadius = Mth.ceil(widthRadius);
    int hRadius = Mth.ceil(heightRadius);
    BlockPos pos = BlockPos.ZERO;
    for (int xx = -wRadius; xx <= wRadius; xx++) {
      for (int yy = -hRadius; yy <= hRadius; yy++) {
        for (int zz = -wRadius; zz <= wRadius; zz++) {
          if (pos.distToCenterSqr((xx + 0.5F), (yy + 0.5F), (zz + 0.5F)) <= Math.max(widthRadius, heightRadius))
            positions.add(pos.offset(center).offset(xx, yy, zz));
        }
      }
    }
    return positions;
  }

  public static void damageMiningItem(ItemStack stack, ServerPlayer player, int amount) {
    Runnable damageItem = () -> stack.hurtAndBreak(amount, (LivingEntity)player, (player2) -> {
      player2.broadcastBreakEvent(player2.getUsedItemHand());
    });
    for (int i = 0; i < amount; i++)
      damageItem.run();
  }

  public static boolean breakBlock(ServerLevel world, ServerPlayer player, BlockPos pos, boolean breakBlock, boolean ignoreHarvestRestrictions) {
    return breakBlock(world, player, pos, world.getBlockState(pos), player.getMainHandItem(), breakBlock, ignoreHarvestRestrictions);
  }

  public static boolean breakBlock(ServerLevel world, ServerPlayer player, BlockPos pos, BlockState stateBroken, ItemStack heldItem, boolean breakBlock, boolean ignoreHarvestRestrictions) {
    ItemStack original = player.getItemInHand(InteractionHand.MAIN_HAND);
    try {
      player.setItemInHand(InteractionHand.MAIN_HAND, heldItem);
      return doNativeBreakBlock(world, player, pos, stateBroken, heldItem, breakBlock, ignoreHarvestRestrictions);
    } finally {
      player.setItemInHand(InteractionHand.MAIN_HAND, original);
    }
  }

  private static boolean doNativeBreakBlock(ServerLevel world, ServerPlayer player, BlockPos pos, BlockState stateBroken, ItemStack heldItem, boolean breakBlock, boolean ignoreHarvestRestrictions) {
    int xp;
    try {
      boolean preCancelEvent = !heldItem.isEmpty() && !heldItem.getItem().canAttackBlock(stateBroken, world, pos, player);
      BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, stateBroken, player);
      event.setCanceled(preCancelEvent);
      MinecraftForge.EVENT_BUS.post(event);
      if (event.isCanceled())
        return false;
      xp = event.getExpToDrop();
    } catch (Exception exc) {
      return false;
    }
    if (xp == -1)
      return false;
    if (heldItem.onBlockStartBreak(pos, player))
      return false;
    boolean harvestable = true;
    try {
      if (!ignoreHarvestRestrictions)
        harvestable = stateBroken.canHarvestBlock(world, pos, player);
    } catch (Exception exc) {
      return false;
    }
    try {
      heldItem.copy().mineBlock(world, stateBroken, pos, player);
    } catch (Exception exc) {
      return false;
    }
    boolean wasCapturingStates = world.captureBlockSnapshots;
    List<BlockSnapshot> previousCapturedStates = new ArrayList<>(world.capturedBlockSnapshots);
    world.captureBlockSnapshots = true;
    try {
      if (breakBlock) {
        if (!stateBroken.onDestroyedByPlayer(world, pos, player, harvestable, Fluids.EMPTY.defaultFluidState())) {
          restoreWorldState(world, wasCapturingStates, previousCapturedStates);
          return false;
        }
      } else {
        stateBroken.getBlock().playerWillDestroy(world, pos, stateBroken, player);
      }
    } catch (Exception exc) {
      restoreWorldState(world, wasCapturingStates, previousCapturedStates);
      return false;
    }
    stateBroken.getBlock().destroy(world, pos, stateBroken);
    if (harvestable)
      try {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        stateBroken.getBlock().playerDestroy(world, player, pos, stateBroken, tileEntity, heldItem.copy());
      } catch (Exception exc) {
        restoreWorldState(world, wasCapturingStates, previousCapturedStates);
        return false;
      }
    if (xp > 0)
      stateBroken.getBlock().popExperience(world, pos, xp);
    BlockDropCaptureHelper.startCapturing();
    try {
      world.captureBlockSnapshots = false;
      world.restoringBlockSnapshots = true;
      world.capturedBlockSnapshots.forEach(s -> s.restore(true));
      world.restoringBlockSnapshots = false;
      world.capturedBlockSnapshots.forEach(s -> world.setBlockAndUpdate(s.getPos(), Blocks.AIR.defaultBlockState()));
    } finally {
      BlockDropCaptureHelper.getCapturedStacksAndStop();
      world.capturedBlockSnapshots.clear();
      world.captureBlockSnapshots = wasCapturingStates;
      world.capturedBlockSnapshots.addAll(previousCapturedStates);
    }
    return true;
  }

  private static void restoreWorldState(Level world, boolean prevCaptureFlag, List<BlockSnapshot> prevSnapshots) {
    world.captureBlockSnapshots = false;
    world.restoringBlockSnapshots = true;
    world.capturedBlockSnapshots.forEach(s -> s.restore(true));
    world.restoringBlockSnapshots = false;
    world.capturedBlockSnapshots.clear();
    world.captureBlockSnapshots = prevCaptureFlag;
    world.capturedBlockSnapshots.addAll(prevSnapshots);
  }
}
