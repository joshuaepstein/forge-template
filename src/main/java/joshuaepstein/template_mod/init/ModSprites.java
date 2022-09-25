package joshuaepstein.template_mod.init;

import net.minecraft.client.resources.model.Material;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraft.client.renderer.Sheets;

public class ModSprites {


    public static void register(TextureStitchEvent.Pre event){
        if(event.getAtlas().location().equals(Sheets.CHEST_SHEET)){
//            addSprite(event, TemplateEntityRendereringSomething.NORMAL);
        }
    }

    public static void addSprite(TextureStitchEvent.Pre event, Material material){
        event.addSprite(material.atlasLocation());
        event.addSprite(material.texture());
    }
}
