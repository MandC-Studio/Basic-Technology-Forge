package cn.org.snowskystudio.basictechnology.item;

import cn.org.snowskystudio.basictechnology.BasicTechnology;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * This class provide all the items in this mod.
 */
public class ModItems {
    // This is the Deferred Register variable.
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BasicTechnology.MODID);

    // These are the new nuggets
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget",
            () -> new Item(new Item.Properties().fireResistant()));

    // These are the new ingots
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));

    // These are the new sticks
    public static final RegistryObject<Item> COPPER_STICK = ITEMS.register("copper_stick",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_STICK = ITEMS.register("iron_stick",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_STICK = ITEMS.register("steel_stick",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_STICK = ITEMS.register("gold_stick",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_STICK = ITEMS.register("netherite_stick",
            () -> new Item(new Item.Properties().fireResistant()));

    // These are the new plates
    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_PLATE = ITEMS.register("steel_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_PLATE = ITEMS.register("netherite_plate",
            () -> new Item(new Item.Properties().fireResistant()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
