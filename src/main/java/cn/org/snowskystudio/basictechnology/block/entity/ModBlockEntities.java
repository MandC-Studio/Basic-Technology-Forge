package cn.org.snowskystudio.basictechnology.block.entity;

import cn.org.snowskystudio.basictechnology.BasicTechnology;
import cn.org.snowskystudio.basictechnology.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BasicTechnology.MODID);

    public static final RegistryObject<BlockEntityType<PressMachineBlockEntity>> PRESS_MACHINE_BE =
            BLOCK_ENTITIES.register("press_machine_be", ()->
                    BlockEntityType.Builder.of(PressMachineBlockEntity::new,
                            ModBlocks.PRESS_MACHINE.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
