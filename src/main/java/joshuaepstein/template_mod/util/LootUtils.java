package joshuaepstein.template_mod.util;

import com.google.common.collect.ImmutableMap;
import joshuaepstein.superpowers.Main;
import joshuaepstein.superpowers.init.ModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LootUtils {
  public static boolean doesContextFulfillSet(LootContext ctx, LootParameterSet set) {
    for (LootParameter<?> required : set.getRequired()) {
      if (!ctx.hasParam(required))
        return false;
    }
    return true;
  }

  public static void genStarterBox(ServerWorld world, ServerPlayerEntity player, NonNullList<ItemStack> itemsAdded) {
    try{
      LootContext.Builder builder = (new LootContext.Builder(world)).withRandom(world.random).withParameter(LootParameters.THIS_ENTITY, player).withParameter(LootParameters.ORIGIN, Vector3d.atCenterOf(player.blockPosition()));
      LootContext ctx = builder.create(LootParameterSets.CHEST);
      NonNullList<ItemStack> stacks = NonNullList.create();
      stacks.addAll(world.getServer().getLootTables().get(Main.id("chests/starter_box")).getRandomItems(ctx));
      if(itemsAdded.size() >= 1){
        stacks.addAll(itemsAdded);
      }
      int left = 23 - stacks.size();
      NonNullList<ItemStack> empties = NonNullList.create();
      for(int i = 0; i < left; i++){
        empties.add(ItemStack.EMPTY);
      }
      stacks.addAll(empties);
      Collections.shuffle(stacks);
      Collections.shuffle(stacks);
      Collections.shuffle(stacks);
      Collections.shuffle(stacks);
      if(stacks.size() > 54){
        Main.LOGGER.error("Attempted to generate Starter Box with more than 54 items. Check box loot table.");
        stacks = NonNullList.of(ItemStack.EMPTY, stacks.stream().limit(54).toArray(ItemStack[]::new));
      }


      ItemStack BOX = new ItemStack(ModBlocks.POWER_CRATE);
      ItemStackHelper.saveAllItems(BOX.getOrCreateTagElement("BlockEntityTag"), stacks);
      BOX.setHoverName(new StringTextComponent(TextFormatting.GREEN + "Starter " + TextFormatting.GRAY + "Box"));
//      MiscUtils.giveItem(player, BOX);
      player.drop(BOX, false);
    } catch (Exception e){
      e.printStackTrace();
    }
  }


  public static void genLootShulkerBox(ServerWorld world, ServerPlayerEntity player, NonNullList<ItemStack> itemsAdded, ResourceLocation location) {
    try{
      LootContext.Builder builder = (new LootContext.Builder(world)).withRandom(world.random).withParameter(LootParameters.THIS_ENTITY, player).withParameter(LootParameters.ORIGIN, Vector3d.atCenterOf(player.blockPosition()));
      LootContext ctx = builder.create(LootParameterSets.CHEST);
      NonNullList<ItemStack> stacks = NonNullList.create();
      stacks.addAll(world.getServer().getLootTables().get(location).getRandomItems(ctx));
      if(itemsAdded.size() >= 1){
        stacks.addAll(itemsAdded);
      }
      int left = 44 - stacks.size();
      NonNullList<ItemStack> empties = NonNullList.create();
      for(int i = 0; i < left; i++){
        empties.add(ItemStack.EMPTY);
      }
      stacks.addAll(empties);
      Collections.shuffle(stacks);
      Collections.shuffle(stacks);
      Collections.shuffle(stacks);
      Collections.shuffle(stacks);
      if(stacks.size() > 54){
        Main.LOGGER.error("Attempted to generate " + location +"with more than 54 items. Check box loot table.");
        stacks = NonNullList.of(ItemStack.EMPTY, stacks.stream().limit(54).toArray(ItemStack[]::new));
      }

      ItemStack BOX = new ItemStack(Items.BLACK_SHULKER_BOX);
      ItemStackHelper.saveAllItems(BOX.getOrCreateTagElement("BlockEntityTag"), stacks);
//      MiscUtils.giveItem(player, BOX);
      player.drop(BOX, false);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  /**
   * Maps a list of loot pools using their names.
   *
   * @param pools The pools to map.
   * @return An immutable map of loot pools.
   */
  public static Map<String, LootPool> mapPools (List<LootPool> pools) {
    return ImmutableMap.copyOf(pools.stream().collect(Collectors.toMap(LootPool::getName, p -> p)));
  }

  /**
   * Gets all loot pools within a table.
   *
   * @param table The table to pull from.
   * @return The list of pools within a table.
   */
  public static List<LootPool> getPools (LootTable table) {
    return ObfuscationReflectionHelper.getPrivateValue(LootTable.class, table, "pools");
  }

  /**
   * Gets all loot entries from a loot pool.
   *
   * @param pool The loot pool to pull from.
   * @return The list of entries within the pool.
   */
  public static List<LootEntry> getEntries (LootPool pool) {
    return ObfuscationReflectionHelper.getPrivateValue(LootPool.class, pool, "entries");
  }

  /**
   * Gets all loot conditions from a loot pool.
   *
   * @param pool The loot pool to pull from.
   * @return The list of loot conditions.
   */
  public static List<ILootCondition> getConditions (LootPool pool) {
    return ObfuscationReflectionHelper.getPrivateValue(LootPool.class, pool, "conditions");
  }

  /**
   * Gets an ItemStack used with a given loot context. It first checks for the tool context.
   * If no tool is found it will check for the killer's held item. If there is no killer it
   * will check the direct killer.
   *
   * @param ctx The loot context to read from.
   * @return The stack that was found for the given context. This may be null.
   */
  @Nullable
  public static ItemStack getItemContext (LootContext ctx) {
    ItemStack stack = ctx.getParamOrNull(LootParameters.TOOL);
    if (stack == null) {
      Entity killer = ctx.getParamOrNull(LootParameters.KILLER_ENTITY);
      if (killer == null) {
        killer = ctx.getParamOrNull(LootParameters.DIRECT_KILLER_ENTITY);
      }
      if (killer instanceof LivingEntity) {
        stack = ((LivingEntity) killer).getMainHandItem();
      }
    }
    return stack;
  }
}
