package cn.org.snowskystudio.basictechnology.block.entity;

import cn.org.snowskystudio.basictechnology.recipe.PressMachineRecipe;
import cn.org.snowskystudio.basictechnology.screen.PressMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PressMachineBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;
    private int hasEnergy = 0;

    public PressMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PRESS_MACHINE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> PressMachineBlockEntity.this.progress;
                    case 1 -> PressMachineBlockEntity.this.maxProgress;
                    case 2 -> PressMachineBlockEntity.this.hasEnergy;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> PressMachineBlockEntity.this.progress = pValue;
                    case 1 -> PressMachineBlockEntity.this.maxProgress = pValue;
                    case 2 -> PressMachineBlockEntity.this.hasEnergy = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0;i < itemHandler.getSlots();i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.basictechnology.press_machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PressMachineMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("press_machine.inventory", itemHandler.serializeNBT());
        pTag.putInt("press_machine.progress", progress);
        pTag.putInt("press_machine.hasEnergy", hasEnergy);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        itemHandler.deserializeNBT(pTag.getCompound("press_machine.inventory"));
        progress = pTag.getInt("press_machine.progress");
        hasEnergy = pTag.getInt("press_machine.hasEnergy");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void subProgress() {
        progress -= 15;
        if (progress <= 0)
            resetProgress();
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<RecipeHolder<PressMachineRecipe>> recipe = getCrrectRcipe();
        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<PressMachineRecipe>> recipe = getCrrectRcipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<RecipeHolder<PressMachineRecipe>> getCrrectRcipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(PressMachineRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
}
