package net.juliantexo.magicprogression.block;

import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.block.custom.ManaFurnaceBlock;
import net.juliantexo.magicprogression.item.ModItemGroup;
import net.juliantexo.magicprogression.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MagicProgression.MOD_ID);

    public static final RegistryObject<Block> MANA_FURNACE = registerBlock("mana_furnace",
            () -> new ManaFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .lightLevel(state -> state.getValue(ManaFurnaceBlock.LIT) ? 7 : 0)), ModItemGroup.TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                           CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
