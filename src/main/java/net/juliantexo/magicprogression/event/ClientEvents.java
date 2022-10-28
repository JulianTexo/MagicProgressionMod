package net.juliantexo.magicprogression.event;

import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.client.ManaHudOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = MagicProgression.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents{

    }


    @Mod.EventBusSubscriber(modid = MagicProgression.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("mana", ManaHudOverlay.HUD_MANA);
        }
    }
}
