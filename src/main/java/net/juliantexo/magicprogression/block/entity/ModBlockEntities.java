package net.juliantexo.magicprogression.block.entity;

import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagicProgression.MOD_ID);

    public static final RegistryObject<BlockEntityType<ManaFurnaceBlockEntity>> MANA_FURNACE =
            BLOCK_ENTITIES.register("mana_furnace", () ->
                    BlockEntityType.Builder.of(ManaFurnaceBlockEntity::new, ModBlocks.MANA_FURNACE.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
