package joshuaepstein.template_mod.util;

import joshuaepstein.superpowers.Main;
import net.minecraft.util.ResourceLocation;

public enum PointTypes {

    SKILLPOINT("skill_point", Main.id("skill_point"), 1);

    String name;
    ResourceLocation id;
    int numberId;

    PointTypes(String name, ResourceLocation id, int numberId){
        this.name = name;
        this.id = id;
        this.numberId = numberId;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getId() {
        return id;
    }

    public int getNumberId() {
        return numberId;
    }
}
