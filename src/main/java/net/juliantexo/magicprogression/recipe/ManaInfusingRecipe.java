package net.juliantexo.magicprogression.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.juliantexo.magicprogression.MagicProgression;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ManaInfusingRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public ManaInfusingRecipe(ResourceLocation id, ItemStack output,
                             NonNullList<Ingredient> recipeItems){
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }

        boolean allItemsValid = true;

        for(Ingredient ingredient:recipeItems){
            for(int i = 0; i < recipeItems.size(); i++){
                if(
                        !(ingredient.test(pContainer.getItem(0))
                        || ingredient.test(pContainer.getItem(1))
                        || ingredient.test(pContainer.getItem(2)))
                ){
                    allItemsValid = false;
                }
            }
        }


        return  allItemsValid;
    }

    @Override
    public NonNullList<Ingredient> getIngredients(){
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ManaInfusingRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ManaInfusingRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<ManaInfusingRecipe>{
        private Type() { }
        public static final ManaInfusingRecipe.Type INSTANCE = new ManaInfusingRecipe.Type();
        public static final String ID = "mana_infusing";
    }

    public static class Serializer implements RecipeSerializer<ManaInfusingRecipe>{
        public static final ManaInfusingRecipe.Serializer INSTANCE = new ManaInfusingRecipe.Serializer();
        public static final ResourceLocation ID
                = new ResourceLocation(MagicProgression.MOD_ID, "mana_infusing");

        @Override
        public ManaInfusingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            System.out.println("DEBUG PRINT: " + inputs.get(0).getItems().toString());

            return new ManaInfusingRecipe(pRecipeId, output, inputs);
        }

        @Override
        public @Nullable ManaInfusingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new ManaInfusingRecipe(pRecipeId, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ManaInfusingRecipe recipe){
            buf.writeInt(recipe.getIngredients().size());

            for(Ingredient ing : recipe.getIngredients()){
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
