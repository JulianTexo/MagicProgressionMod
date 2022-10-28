package net.juliantexo.magicprogression.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.juliantexo.magicprogression.MagicProgression;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation MANA_BAR = new ResourceLocation(MagicProgression.MOD_ID,
            "textures/mana/bars.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) ->{

        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MANA_BAR);

        GuiComponent.blit(poseStack, x - 91, y - 48, 0, 10, 182, 5, 256, 256);

        GuiComponent.blit(poseStack, x - 91, y - 48, 0, 15, ClientManaData.getScaledManaForGui(), 5, 256, 256);

    });

}
