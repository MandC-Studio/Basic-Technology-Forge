package cn.org.snowskystudio.basictechnology.item;

import cn.org.snowskystudio.basictechnology.BasicTechnology;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BasicTechnology.MODID);

    public static final RegistryObject<CreativeModeTab> CRYSTALLOGRAPHY_TAB = CREATIVE_MODE_TABS.register("crystallography_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.STEEL_PLATE.get()))
                    .title(Component.translatable("creativetab.basictechnology.crystallography_tab"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.COPPER_NUGGET.get());
                        pOutput.accept(ModItems.STEEL_NUGGET.get());
                        pOutput.accept(ModItems.NETHERITE_NUGGET.get());

                        pOutput.accept(ModItems.STEEL_INGOT.get());

                        pOutput.accept(ModItems.COPPER_STICK.get());
                        pOutput.accept(ModItems.IRON_STICK.get());
                        pOutput.accept(ModItems.STEEL_STICK.get());
                        pOutput.accept(ModItems.GOLD_STICK.get());
                        pOutput.accept(ModItems.NETHERITE_STICK.get());

                        pOutput.accept(ModItems.COPPER_PLATE.get());
                        pOutput.accept(ModItems.IRON_PLATE.get());
                        pOutput.accept(ModItems.STEEL_PLATE.get());
                        pOutput.accept(ModItems.GOLD_PLATE.get());
                        pOutput.accept(ModItems.NETHERITE_PLATE.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
