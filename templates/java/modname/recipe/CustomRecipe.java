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
import net.minecraft.world.level.block.FurnaceBlock;
import org.jetbrains.annotations.Nullable;

//TODO: change class name
public class CustomRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public CustomRecipe(ResourceLocation id, ItemStack output,
                        NonNullList<Ingredient> recipeItems){
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    //TODO: change matches method to test if all input slots contain an item necessary for a recipe
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }

        return recipeItems.get(0).test(pContainer.getItem(0));
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
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    //TODO: change RecipeType<?>
    public static class Type implements RecipeType<CustomRecipe>{
        private Type() { }
        public static final Type INSTANCE = new Type();
        //TODO: change recipe name
        public static final String ID = "custom_recipe";
    }

    //TODO: change RecipeSerializer<?>
    public static class Serializer implements RecipeSerializer<CustomRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID
                //TODO: change name
                = new ResourceLocation(MagicProgression.MOD_ID, "custom_recipe");

        //TODO: change return type of this method
        @Override
        public CustomRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new CustomRecipe(pRecipeId, output, inputs);
        }

        //TODO: change return type of this method
        @Override
        public @Nullable CustomRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new CustomRecipe(pRecipeId, output, inputs);
        }

        //TODO: change input parameter of this method
        @Override
        public void toNetwork(FriendlyByteBuf buf, CustomRecipe recipe){
            buf.writeInt(recipe.getIngredients().size());

            for(Ingredient ing : recipe.getIngredients()){
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
