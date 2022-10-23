package net.juliantexo.magicprogression;

import com.mojang.logging.LogUtils;
import net.juliantexo.magicprogression.block.ModBlocks;
import net.juliantexo.magicprogression.block.entity.ModBlockEntities;
import net.juliantexo.magicprogression.item.ModItems;
import net.juliantexo.magicprogression.recipe.ModRecipes;
import net.juliantexo.magicprogression.screen.ManaFurnaceScreen;
import net.juliantexo.magicprogression.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MagicProgression.MOD_ID)
public class MagicProgression
{
    public static final String MOD_ID = "magicprogression";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MagicProgression()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            MenuScreens.register(ModMenuTypes.MANA_FURNACE_MENU.get(), ManaFurnaceScreen::new);
        }
    }



    public void fillItemList(NonNullList<ItemStack> items) {

    }
}
