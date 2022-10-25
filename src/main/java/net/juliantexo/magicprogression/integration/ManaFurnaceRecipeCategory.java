package net.juliantexo.magicprogression.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.block.ModBlocks;
import net.juliantexo.magicprogression.recipe.ManaFurnaceRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ManaFurnaceRecipeCategory implements IRecipeCategory<ManaFurnaceRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(MagicProgression.MOD_ID, "mana_smelting");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(MagicProgression.MOD_ID, "textures/gui/mana_furnace_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ManaFurnaceRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MANA_FURNACE.get()));
    }

    @Override
    public RecipeType<ManaFurnaceRecipe> getRecipeType() {
        return JEIMagicProgressionModPlugin.MANA_SMELTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Mana Furnace");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ManaFurnaceRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 17).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35).addItemStack(recipe.getResultItem());
    }
}
