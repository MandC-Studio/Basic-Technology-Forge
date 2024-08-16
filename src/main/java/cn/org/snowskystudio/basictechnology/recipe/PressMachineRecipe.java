package cn.org.snowskystudio.basictechnology.recipe;

import cn.org.snowskystudio.basictechnology.BasicTechnology;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PressMachineRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;

    public PressMachineRecipe(NonNullList<Ingredient> inputItems, ItemStack output) {
        this.inputItems = inputItems;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return inputItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<PressMachineRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "press_machine";
    }

    public static class Serializer implements RecipeSerializer<PressMachineRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(BasicTechnology.MODID, "press_machine");

        @Override
        public Codec<PressMachineRecipe> codec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    Codec.list(Ingredient.CODEC)
                            .fieldOf("ingredients")
                            .forGetter(PressMachineRecipe::getIngredients),
                    ItemStack.CODEC
                            .fieldOf("result")
                            .forGetter(recipe -> recipe.getResultItem(null))
            ).apply(instance, (ingredients, result) -> {
                NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
                for (int i = 0; i < ingredients.size(); i++) {
                    inputs.set(i, ingredients.get(i));
                }
                return new PressMachineRecipe(inputs, result);
            }));
        }

        @Override
        public @Nullable PressMachineRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(friendlyByteBuf));
            }

            ItemStack output = friendlyByteBuf.readItem();
            return new PressMachineRecipe(inputs, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PressMachineRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
