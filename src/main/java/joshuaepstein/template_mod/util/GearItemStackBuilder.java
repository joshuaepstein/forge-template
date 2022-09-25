package joshuaepstein.template_mod.util;

import joshuaepstein.superpowers.init.ModAttributes;
import joshuaepstein.superpowers.init.ModConfigs;
import joshuaepstein.superpowers.item.gear.Gear;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
public class GearItemStackBuilder {
  int modelId = -1;
  
  int specialModelId = -1;
  
  int color = -1;
  
  Item item = null;
  
  Gear.Rarity gearRarity = Gear.Rarity.UNIQUE;
  
  public GearItemStackBuilder(Item item) {
    if (!(item instanceof Gear))
      throw new IllegalArgumentException("Expected a gear item");
    this.item = item;
  }
  
  public GearItemStackBuilder setColor(int color) {
    this.color = color;
    return this;
  }
  
  public GearItemStackBuilder setModelId(int modelId) {
    this.modelId = modelId;
    return this;
  }
  
  public GearItemStackBuilder setSpecialModelId(int specialModelId) {
    this.specialModelId = specialModelId;
    return this;
  }
  
  public GearItemStackBuilder setGearRarity(Gear.Rarity gearRarity) {
    this.gearRarity = gearRarity;
    return this;
  }
  
  public ItemStack build() {
    ItemStack itemStack = new ItemStack(this.item);
    ModAttributes.GEAR_STATE.create(itemStack, Gear.State.IDENTIFIED);
    ModAttributes.GEAR_RARITY.create(itemStack, this.gearRarity);
    itemStack.getOrCreateTag().remove("RollTicks");
    itemStack.getOrCreateTag().remove("LastModelHit");
    ModAttributes.GEAR_ROLL_TYPE.create(itemStack, ModConfigs.GEAR.DEFAULT_ROLL);
    ModAttributes.GEAR_COLOR.create(itemStack, this.color);
    ModAttributes.GEAR_MODEL.create(itemStack, this.modelId);
    ModAttributes.GEAR_SPECIAL_MODEL.create(itemStack, this.specialModelId);
    return itemStack;
  }
}