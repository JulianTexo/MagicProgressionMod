package net.juliantexo.magicprogression.recipe;

import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.item.ModItemGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MagicProgression.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ManaFurnaceRecipe>> MANA_SMELTING_SERIALIZER =
            SERIALIZERS.register("mana_smelting", () -> ManaFurnaceRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ManaInfusingRecipe>> MANA_INFUSING =
            SERIALIZERS.register("mana_infusing", () -> ManaInfusingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
