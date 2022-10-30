package net.juliantexo.magicprogression.item;

import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.item.custom.ChargedCrystalItem;
import net.juliantexo.magicprogression.item.custom.CombatWandItem;
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
    public static final RegistryObject<Item> MANA_CRYSTAL_SHARD = ITEMS.register("mana_crystal_shard",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB)));
    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new ChargedCrystalItem(new Item.Properties().tab(ModItemGroup.TAB).stacksTo(1), 250));
    public static final RegistryObject<Item> MANA_INGOT = ITEMS.register("mana_ingot",
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
            () -> new MiningWandItem(Tiers.WOOD, 1, -2.8F,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_MINING_WAND = ITEMS.register("stone_mining_wand",
            () -> new MiningWandItem(Tiers.STONE, 1, -2.8F,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> IRON_MINING_WAND = ITEMS.register("iron_mining_wand",
            () -> new MiningWandItem(Tiers.IRON, 1, -2.8F,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> GOLD_MINING_WAND = ITEMS.register("gold_mining_wand",
            () -> new MiningWandItem(Tiers.GOLD, 1, -2.8F,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> DIAMOND_MINING_WAND = ITEMS.register("diamond_mining_wand",
            () -> new MiningWandItem(Tiers.DIAMOND, 1, -2.8F,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));

    //COMBAT WANDS
    public static final RegistryObject<Item> WOODEN_COMBAT_WAND = ITEMS.register("wooden_combat_wand",
            () -> new CombatWandItem(Tiers.WOOD, 3, -2.4f,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> STONE_COMBAT_WAND = ITEMS.register("stone_combat_wand",
            () -> new CombatWandItem(Tiers.STONE, 3, -2.4f,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_COMBAT_WAND = ITEMS.register("iron_combat_wand",
            () -> new CombatWandItem(Tiers.IRON, 3, -2.4f,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> GOLD_COMBAT_WAND = ITEMS.register("gold_combat_wand",
            () -> new CombatWandItem(Tiers.GOLD, 3, -2.4f,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> DIAMOND_COMBAT_WAND = ITEMS.register("diamond_combat_wand",
            () -> new CombatWandItem(Tiers.DIAMOND, 3, -2.4f,
                    (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));




    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
