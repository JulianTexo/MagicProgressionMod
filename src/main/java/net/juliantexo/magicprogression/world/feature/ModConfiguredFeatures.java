package net.juliantexo.magicprogression.world.feature;

import com.google.common.base.Suppliers;
import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MagicProgression.MOD_ID);

    //Suppliers for all ores (by dimensions)
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_MANA_CRYSTAL_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.MANA_CRYSTAL_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.MANA_CRYSTAL_ORE.get().defaultBlockState())));

    //Register the Ores (for all dimensions)
    public static final RegistryObject<ConfiguredFeature<?, ?>> MANA_CRYSTAL_ORE = CONFIGURED_FEATURES.register("mana_crystal_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_MANA_CRYSTAL_ORES.get(),/*vein size*/ 15)));

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }
}
