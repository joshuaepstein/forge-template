package joshuaepstein.template_mod.util;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class InventoryUtils {

    public static IItemHandler getInventory (Level world, BlockPos pos, Direction side) {
        final TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity != null) {
            final LazyOptional<IItemHandler> inventoryCap = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            return inventoryCap.orElse(EmptyHandler.INSTANCE);
        }
        else {
            // Some blocks like composters are not tile entities so their inv can not be
            // accessed through the normal capability system.
            final BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof ISidedInventoryProvider) {
                final ISidedInventoryProvider inventoryProvider = (ISidedInventoryProvider) state.getBlock();
                final ISidedInventory inventory = inventoryProvider.getContainer(state, world, pos);
                if (inventory != null) {
                    return new SidedInvWrapper(inventory, side);
                }
            }
        }
        return EmptyHandler.INSTANCE;
    }



}
