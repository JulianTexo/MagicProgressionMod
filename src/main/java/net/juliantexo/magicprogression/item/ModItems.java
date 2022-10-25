package net.juliantexo.magicprogression.item;

import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.item.custom.MiningWandItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
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

    //RODS
    public static final RegistryObject<Item> STONE_ROD = ITEMS.register("stone_rod",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> IRON_ROD = ITEMS.register("iron_rod",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> GOLD_ROD = ITEMS.register("gold_rod",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> DIAMOND_ROD = ITEMS.register("diamond_rod",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    //TOOLS
    public static final RegistryObject<Item> WOODEN_MINING_WAND = ITEMS.register("wooden_mining_wand",
            () -> new MiningWandItem(Tiers.WOOD, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_MINING_WAND = ITEMS.register("stone_mining_wand",
            () -> new MiningWandItem(Tiers.STONE, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> IRON_MINING_WAND = ITEMS.register("iron_mining_wand",
            () -> new MiningWandItem(Tiers.IRON, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> GOLD_MINING_WAND = ITEMS.register("gold_mining_wand",
            () -> new MiningWandItem(Tiers.GOLD, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> DIAMOND_MINING_WAND = ITEMS.register("diamond_mining_wand",
            () -> new MiningWandItem(Tiers.DIAMOND, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));




    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
