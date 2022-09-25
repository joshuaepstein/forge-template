package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.network.IContainerFactory;

public class ModContainers {
//    public static MenuType<TemplateContainer> TEMPLATE_CONTAINER;

    public static void register(RegistryEvent.Register<MenuType<?>> event){
//        TEMPLATE_CONTAINER = createMenuType((id, inv, data) -> {
//            return new TemplateContainer(id, inv.player.level, data.readBlockPos(), inv, inv.player);
//        });

        event.getRegistry().registerAll(
//                TEMPLATE_CONTAINER.setRegistryName("template_container")
        );

        Main.LOGGER.info("Template Mod | Containers are loaded successfully!");
    }

    private static <T extends AbstractContainerMenu> MenuType<T> createMenuType(IContainerFactory<T> factory){
        return new MenuType<T>(factory);
    }


}
