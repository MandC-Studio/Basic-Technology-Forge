package cn.org.snowskystudio.basictechnology.block;

import cn.org.snowskystudio.basictechnology.BasicTechnology;
import cn.org.snowskystudio.basictechnology.block.custom.PressMachineBlock;
import cn.org.snowskystudio.basictechnology.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * This class provide all the blocks in this mod.
 */
public class ModBlocks {
    // This is the Deferred Register variable.
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BasicTechnology.MODID);

    // These are the new ores

    // These are the new blocks
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    // These are the new entity blocks
    public static final RegistryObject<Block> PRESS_MACHINE = registerBlock("press_machine",
            () -> new PressMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));


    /**
     * This method is used to register a block quickly.
     * @param name the block's name
     * @param block the block supplier
     * @param <T> the block class
     * @return the registry block
     */
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    /**
     * This method is the helper method of "registerBlock".
     * @param name the block's name
     * @param block the block supplier
     * @param <T> the block class
     * @return the registry item
     */
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    /**
     * This method is used to make the deferred register work
     * @param eventBus the register
     */
    public static void regisiter(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
