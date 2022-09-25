package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegistryEvent;

public class ModBlocks {


    public static void registerBlocks(RegistryEvent.Register<Block> event) {
//        registerBlock(event, new Block(Block.Properties.copy(Blocks.ACACIA_PLANKS)), Main.id("acacia_planks"));

        Main.LOGGER.info("Template Mod | Blocks are loaded successfully!");
    }

    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
//        registerBlockItem(ModBlocks.ACACIA_PLANKS, Main.id("acacia_planks"));

        Main.LOGGER.info("Template Mod | BlockItems are loaded successfully!");
    }

    public static void registerBlockEntities(RegistryEvent.Register<BlockEntityType<?>> event) {
//        registerTileEntity(event, BlockEntityType.Builder.of(ModBlocks::new, ModBlocks.ACACIA_PLANKS), Main.id("acacia_planks"));

        Main.LOGGER.info("Template Mod | BlockEntities are loaded successfully!");
    }

    public static void registerBlockEntityRenderers(){
//        BlockEntityRenderers.register(BlockEntityType.SIGN, SignRenderer::new);

        Main.LOGGER.info("Template Mod | BlockEntityRenderers are loaded successfully!");
    }


    private static void registerBlock(RegistryEvent.Register<Block> event, Block block, ResourceLocation id) {
        block.setRegistryName(id);
        event.getRegistry().register(block);
    }

    private static <T extends BlockEntity> void registerTileEntity(RegistryEvent.Register<BlockEntityType<?>> event, BlockEntityType<?> type, ResourceLocation id) {
        type.setRegistryName(id);
        event.getRegistry().register(type);
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block, boolean hide) {
        BlockItem blockItem;
        if(hide){
            blockItem = new BlockItem(block, (new Item.Properties()).stacksTo(64));
        } else {
            blockItem = new BlockItem(block, (new Item.Properties()).tab(ModItems.GROUP).stacksTo(64));
        }
        blockItem.setRegistryName(block.getRegistryName());
        event.getRegistry().register(blockItem);
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block) {
        registerBlockItem(event, block, ModItems.GROUP);
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block, CreativeModeTab itemGroup) {
        BlockItem blockItem = new BlockItem(block, (new Item.Properties()).tab(itemGroup).stacksTo(64));
        blockItem.setRegistryName(block.getRegistryName());
        event.getRegistry().register(blockItem);
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block, CreativeModeTab itemGroup, int maxStackSize, final boolean showGlint) {
        BlockItem blockItem = new BlockItem(block, (new Item.Properties()).tab(itemGroup).stacksTo(maxStackSize)) {
            public boolean isFoil(ItemStack stack) {
                return showGlint;
            }
        };
        blockItem.setRegistryName(block.getRegistryName());
        event.getRegistry().register(blockItem);
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block, int maxStackSize) {
        BlockItem blockItem = new BlockItem(block, (new Item.Properties()).tab(ModItems.GROUP).stacksTo(maxStackSize));
        blockItem.setRegistryName(block.getRegistryName());
        event.getRegistry().register(blockItem);
    }

    private static void registerBlockItem(RegistryEvent.Register<Item> event, Block block, BlockItem blockItem) {
        blockItem.setRegistryName(block.getRegistryName());
        event.getRegistry().register(blockItem);
    }

    private static void registerTallBlockItem(RegistryEvent.Register<Item> event, Block block) {
        DoubleHighBlockItem tallBlockItem = new DoubleHighBlockItem(block, (new Item.Properties()).tab(ModItems.GROUP).stacksTo(64));
        tallBlockItem.setRegistryName(block.getRegistryName());
        event.getRegistry().register(tallBlockItem);
    }

    private static boolean never(BlockState blockState, BlockGetter iBlockReader, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockReader, BlockPos pos) {
        return false;
    }

}
