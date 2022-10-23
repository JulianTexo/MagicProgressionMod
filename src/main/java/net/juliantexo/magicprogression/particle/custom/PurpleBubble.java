package net.juliantexo.magicprogression.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PurpleBubble extends RisingParticle {

    protected PurpleBubble(ClientLevel pLevel, double xCoord, double yCoord, double zCoord, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, xCoord, yCoord, zCoord, pXSpeed, pYSpeed, pZSpeed);

    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            PurpleBubble purpleBubble = new PurpleBubble(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            purpleBubble.pickSprite(this.sprite);
            return purpleBubble;
        }
    }
}
