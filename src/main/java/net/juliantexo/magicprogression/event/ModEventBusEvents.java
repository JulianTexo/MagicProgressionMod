package net.juliantexo.magicprogression.event;

import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.particle.ModParticles;
import net.juliantexo.magicprogression.particle.custom.PurpleBubble;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.targets.FMLServerDevLaunchHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;

import java.rmi.registry.Registry;

@Mod.EventBusSubscriber(modid = MagicProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
        Minecraft.getInstance().particleEngine.register(ModParticles.PURPLE_BUBBLE_PARTICLE.get(), PurpleBubble.Provider::new);
    }


    //Remove Items from creative tabs
    /*@SubscribeEvent
    public static void removeItemsFromCreativeTabs(FMLCommonSetupEvent event){
        for (Item item:ForgeRegistries.ITEMS) {
            if (item.getName(new ItemStack(item)).getString().equals("Wooden Pickaxe")){
                item.getCreativeTabs().clear();
            }
        }
    }*/
}
