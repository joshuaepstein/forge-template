package joshuaepstein.template_mod.util;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;
import java.util.List;

public class VoxelUtils {
    public static VoxelShape combineAll(BooleanOp fct, VoxelShape... shapes) {
        return combineAll(fct, Arrays.asList(shapes));
    }

    public static VoxelShape combineAll(BooleanOp fct, List<VoxelShape> shapes) {
        if (shapes.isEmpty())
            return Shapes.empty();
        VoxelShape first = shapes.get(0);
        for (int i = 1; i < shapes.size(); i++)
            first = Shapes.joinUnoptimized(first, shapes.get(i), fct);
        return first;
    }
}
