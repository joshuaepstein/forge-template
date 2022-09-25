package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MOD_ID);

//    public static final RegistryObject<Enchantment> TEST_ENCHANTMENT = ENCHANTMENTS.register("test_enchantment", () -> new TestEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.VANISHABLE, EquipmentSlot.values()));

}
