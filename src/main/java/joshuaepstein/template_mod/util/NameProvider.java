package joshuaepstein.template_mod.util;

import com.google.common.collect.Lists;
import net.minecraftforge.common.UsernameCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameProvider {
    private static final Random rand = new Random();

    private static final List<String> DEV_NAMES = Lists.newArrayList("JoshuaEpstein");

    private static final List<String> PLAYER_NAMES = Lists.newArrayList("Ar0nStars", "Twg23", "bumblebee1248", "HellFirePVP", "iskall85");

    public static String getRandomName(){
        return getRandomName(rand);
    }

    public static String getRandomName(Random random){
        return MiscUtils.getRandomEntry(getAllAvailableNames(), random);
    }

    public static List<String> getAllAvailableNames() {
        List<String> names = new ArrayList<>();
        names.addAll(DEV_NAMES);
        names.addAll(PLAYER_NAMES);
        names.addAll(getKnownUsernames());
        return names;
    }

    private static List<String> getKnownUsernames(){
        return new ArrayList<>(UsernameCache.getMap().values());
    }
}
