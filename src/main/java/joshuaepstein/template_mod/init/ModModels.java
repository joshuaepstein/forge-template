package joshuaepstein.template_mod.init;

import com.google.common.base.Predicates;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ModModels {
    public static void setupRenderLayers(){
//        setupRenderLayers(ModBlocks.BLOCK, RenderType.solid(), RenderType.translucent());
    }

    private static void setupRenderLayer(Block block, RenderType... renderType){
        ItemBlockRenderTypes.setRenderLayer(block, Predicates.in(Arrays.asList(renderType)));
    }
}
