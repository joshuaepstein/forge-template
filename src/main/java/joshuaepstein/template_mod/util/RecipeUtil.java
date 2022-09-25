package joshuaepstein.template_mod.util;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.Tuple;
import net.minecraft.world.Level;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Nonnull;
import java.util.Optional;

public class RecipeUtil {
    @Nonnull
    public static Optional<Tuple<ItemStack, Float>> findSmeltingResult(Level world, BlockState input) {
        ItemStack stack = new ItemStack(input.getBlock());
        if (stack.isEmpty())
            return Optional.empty();
        return findSmeltingResult(world, stack);
    }

    @Nonnull
    public static Optional<Tuple<ItemStack, Float>> findSmeltingResult(Level world, ItemStack input) {
        RecipeManager mgr = world.getRecipeManager();
        Inventory inventory = new Inventory(input);
        Optional<IRecipe<IInventory>> optRecipe = ObjectUtils.firstNonNull(new Optional[] { mgr
                .getRecipeFor(IRecipeType.SMELTING, inventory, world), mgr
                .getRecipeFor(IRecipeType.CAMPFIRE_COOKING, inventory, world), mgr
                .getRecipeFor(IRecipeType.SMOKING, inventory, world),
                Optional.empty() });
        return optRecipe.map(recipe -> {
            ItemStack smeltResult = recipe.assemble(inventory).copy();
            float exp = 0.0F;
            if (recipe instanceof AbstractCookingRecipe)
                exp = ((AbstractCookingRecipe)recipe).getExperience();
            return new Tuple<>(smeltResult, Float.valueOf(exp));
        });
    }
}
