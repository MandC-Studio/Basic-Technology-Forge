package cn.org.snowskystudio.basictechnology.datagen;

import cn.org.snowskystudio.basictechnology.BasicTechnology;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BasicTechnology.MODID, exFileHelper);
    }

    /**
     * Generate the state files and the model files for blocks.
     */
    @Override
    protected void registerStatesAndModels() {

    }

    /**
     * This method provide a fast way to handle with block and block item.
     * @param blockRegistryObject the block you wang to register
     */
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        // Call defult function.
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    /*
    private void blockWithDirectionalRotation(RegistryObject<Block> blockRegistryObject, ModelFile.UncheckedModelFile modelLocation, int mov) {
        simpleBlockItem(blockRegistryObject.get(), modelLocation);
        getVariantBuilder(blockRegistryObject.get())
                .forAllStates(state -> {
                    Direction facing = state.getValue(CrystallizerBlock.FACING);
                    int xRot = 0;
                    int yRot = 0;
                    switch (facing) {
                        case NORTH:
                            yRot = mov;
                            break;
                        case EAST:
                            yRot = (90 + mov)%360;
                            break;
                        case SOUTH:
                            yRot = (180 + mov)%360;
                            break;
                        case WEST:
                            yRot = (270 + mov)%360;
                            break;
                    }

                    return ConfiguredModel.builder()
                            .modelFile(modelLocation)
                            .rotationX(xRot)
                            .rotationY(yRot)
                            .build();
                });
    }*/
}
