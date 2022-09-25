package joshuaepstein.template_mod.mixins;

import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin({AnvilMenu.class})
public class MixinAnvilMenu {
    @ModifyConstant(method = {"onTake"}, constant = {@Constant(intValue = 40, ordinal = 2)}, remap = false)
    private int overrideMaxRepairLevel(int oldValue) {
        return Integer.MAX_VALUE;
    }
}