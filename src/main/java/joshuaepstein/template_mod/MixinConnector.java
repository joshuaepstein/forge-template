package joshuaepstein.template_mod;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {
    public void connect() {
        Mixins.addConfigurations("assets/template_mod/template_mod.mixins.json");
    }
}
