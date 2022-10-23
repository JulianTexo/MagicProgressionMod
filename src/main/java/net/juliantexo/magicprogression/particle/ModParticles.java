package net.juliantexo.magicprogression.particle;

import net.juliantexo.magicprogression.MagicProgression;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MagicProgression.MOD_ID);


    public static final RegistryObject<SimpleParticleType> PURPLE_BUBBLE_PARTICLE =
            PARTICLE_TYPES.register("purple_bubble_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }

}
