package net.juliantexo.magicprogression.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.recipe.ManaFurnaceRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIMagicProgressionModPlugin implements IModPlugin {
    public static RecipeType<ManaFurnaceRecipe> MANA_SMELTING_TYPE =
            new RecipeType<>(ManaFurnaceRecipeCategory.UID, ManaFurnaceRecipe.class);



    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MagicProgression.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                ManaFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<ManaFurnaceRecipe> recipesManaSmelting = rm.getAllRecipesFor(ManaFurnaceRecipe.Type.INSTANCE);
        registration.addRecipes(MANA_SMELTING_TYPE, recipesManaSmelting);
    }

}
