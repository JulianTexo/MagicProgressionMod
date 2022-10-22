package net.juliantexo.magicprogression.item;

import net.juliantexo.magicprogression.MagicProgression;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagicProgression.MOD_ID);

    //ITEMS
    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
