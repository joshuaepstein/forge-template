package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {

    private static final String NETWORK_VERSION = "0.21.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Main.MOD_ID, "network"),
            () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION), // Client acceptance predicate
            version -> version.equals(NETWORK_VERSION) // Server acceptance predicate
    );

    private static int ID = 0;

    public static void initialize() {
//        CHANNEL.registerMessage(nextId(), OpenRandomItemScreenMessage.class, OpenRandomItemScreenMessage::encode, OpenRandomItemScreenMessage::decode, OpenRandomItemScreenMessage::handle);
    }

    public static int nextId() {
        return ID++;
    }

}
