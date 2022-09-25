package joshuaepstein.template_mod.util;

import joshuaepstein.superpowers.init.ModConfigs;
import net.minecraft.util.text.TextFormatting;

import java.util.Random;

public enum PowersRarity {
    COMMON(TextFormatting.WHITE, "Common"),
    RARE(TextFormatting.AQUA, "Rare"),
    EPIC(TextFormatting.BLUE, "Epic"),
    SUPER(TextFormatting.YELLOW, "Super");

    public final TextFormatting color;
    public final String name;

    PowersRarity(TextFormatting color, String name) {
        this.color = color;
        this.name = name;
    }

    public static PowersRarity getWeightedRandom() {
        Random rand = new Random();
        return getWeightedRarityAt(rand.nextInt(getTotalWeight()));
    }

    private static int getTotalWeight() {
        int totalWeight = 0;
        for (PowersRarity rarity : values())
            totalWeight += getWeight(rarity);
        return totalWeight;
    }

    private static PowersRarity getWeightedRarityAt(int index) {
        PowersRarity current = null;
        for (PowersRarity rarity : values()) {
            current = rarity;
            index -= getWeight(rarity);
            if (index < 0)
                break;
        }
        return current;
    }

    private static int getWeight(PowersRarity rarity) {
        switch (rarity) {
            case COMMON:
                return ModConfigs.RARITY.NORMAL_WEIGHT;
            case RARE:
                return ModConfigs.RARITY.RARE_WEIGHT;
            case EPIC:
                return ModConfigs.RARITY.EPIC_WEIGHT;
            case SUPER:
                return ModConfigs.RARITY.SUPER_WEIGHT;
        }
        return ModConfigs.RARITY.NORMAL_WEIGHT;
    }


}
