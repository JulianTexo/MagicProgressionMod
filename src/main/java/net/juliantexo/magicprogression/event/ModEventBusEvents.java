package net.juliantexo.magicprogression.event;

import net.juliantexo.magicprogression.MagicProgression;
import net.juliantexo.magicprogression.particle.ModParticles;
import net.juliantexo.magicprogression.particle.custom.PurpleBubble;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
        Minecraft.getInstance().particleEngine.register(ModParticles.PURPLE_BUBBLE_PARTICLE.get(), PurpleBubble.Provider::new);
    }
}
