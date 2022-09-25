package joshuaepstein.template_mod.util;

import net.minecraft.util.LazyValue;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class ModUtils {

    private static final LazyValue<Boolean> isOptifinePresent = new LazyValue<>( () -> {
        try {
            final Class<?> clazz = Class.forName("net.optifine.Config");
            return clazz != null;
        } catch(final Exception e) {
            return false;
        }
    });

    public static boolean isOptifineLoaded () {

        return isOptifinePresent.get();
    }

    public static boolean isInModList (String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Nullable
    public static <T> T callIfPresent (String modid, Supplier<Callable<T>> toRun) {
        return callIfPresent(modid, toRun, null);
    }

    @Nullable
    public static <T> T callIfPresent (String modid, Supplier<Callable<T>> ifPresent, @Nullable Supplier<Callable<T>> notPresent) {

        try {

            if (isInModList(modid)) {

                return ifPresent.get().call();
            }

            else if (notPresent != null) {

                return notPresent.get().call();
            }
        }

        catch (final Exception e) {

            throw new RuntimeException(e);
        }

        return null;
    }

    public static void runIfPresent (String modid, Supplier<Runnable> ifPresent) {

        runIfPresent(modid, ifPresent, null);
    }

    public static void runIfPresent (String modid, Supplier<Runnable> ifPresent, @Nullable Supplier<Runnable> notPresent) {

        if (isInModList(modid)) {

            ifPresent.get().run();
        }

        else if (notPresent != null) {

            notPresent.get().run();
        }
    }

    public static ITextComponent getModName (ModContainer mod) {

        return new StringTextComponent(mod.getModInfo().getDisplayName());
    }

    @Nullable
    public static ModContainer getOwner (IForgeRegistryEntry<?> registerable) {

        if (registerable != null && registerable.getRegistryName() != null) {

            return ModList.get().getModContainerById(registerable.getRegistryName().getNamespace()).orElse(null);
        }

        return null;
    }

    public static String getActiveMod () {

        return ModLoadingContext.get().getActiveNamespace();
    }

}
