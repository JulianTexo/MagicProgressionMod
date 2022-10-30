package net.juliantexo.magicprogression.item.custom;

import net.juliantexo.magicprogression.mana.PlayerMana;
import net.juliantexo.magicprogression.mana.PlayerManaProvider;
import net.juliantexo.magicprogression.networking.ModMessages;
import net.juliantexo.magicprogression.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChargedCrystalItem extends Item {

    private int mana;
    private int MAX_MANA;
    private final int MIN_MANA = 0;

    public ChargedCrystalItem(Properties pProperties, int max_mana) {
        super(pProperties);
        this.mana = 0;
        this.MAX_MANA = max_mana;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.literal("This Mana Crystal currently has: "
                    + this.mana + "/" + this.MAX_MANA + " mana stored."));
        } else {
            pTooltipComponents.add(Component.literal("Press <shift> for more information."));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count)
    {
        if(this.mana < this.MAX_MANA) {
            if (player.getCapability(PlayerManaProvider.PLAYER_MANA).isPresent()) {
                PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve().get();
                if (playerMana.getMana() > 25) {
                    playerMana.subMana(25);
                    ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve().get().getMana()), (ServerPlayer) player);
                    this.addMana(25);
                }
            }
        }
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        if(pStack.getItem() instanceof ChargedCrystalItem){
            return Math.round(13.0F - (float)this.getMana() * 13.0F / (float) this.getMAX_MANA());
        }
        return Math.round(13.0F - (float)pStack.getDamageValue() * 13.0F / (float)this.getMaxDamage(pStack));
    }

    public void addMana(int add){
        this.mana = Math.min(mana + add, this.getMAX_MANA());
    }

    public void subMana(int sub){
        this.mana = Math.max(mana - sub, this.getMIN_MANA());
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMAX_MANA() {
        return MAX_MANA;
    }

    public int getMIN_MANA() {
        return MIN_MANA;
    }
}
