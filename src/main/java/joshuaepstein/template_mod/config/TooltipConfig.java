package joshuaepstein.template_mod.config;

import com.google.gson.annotations.Expose;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TooltipConfig extends Config {
    @Expose
    private final List<TooltipEntry> tooltips = new ArrayList();

    public TooltipConfig() {
    }

    public Optional<String> getTooltipString(Item item) {
        String itemRegistryName = item.getRegistryName().toString();
        return this.tooltips.stream().filter((entry) -> entry.item.equals(itemRegistryName)).map(TooltipEntry::getValue).findFirst();
    }

    public String getName() {
        return "tooltip";
    }

    protected void reset() {
        this.tooltips.clear();
        this.tooltips.add(new TooltipEntry(Items.STONE.getRegistryName().toString(), "A Stone Item"));
    }

    public static class TooltipEntry {
        @Expose
        private String item;
        @Expose
        private String value;

        public TooltipEntry(String item, String value) {
            this.item = item;
            this.value = value;
        }

        public String getItem() {
            return this.item;
        }

        public String getValue() {
            return this.value;
        }
    }
}
