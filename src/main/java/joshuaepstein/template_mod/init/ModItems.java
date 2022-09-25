package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static CreativeModeTab GROUP = new CreativeModeTab(Main.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.ACACIA_BOAT);
        }

        @Override
        public boolean hasSearchBar() {
            return false;
        }
    };



    public static void register(RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> registry = event.getRegistry();

        Main.LOGGER.info("Template Mod | Items are loaded successfully!");
    }

}
