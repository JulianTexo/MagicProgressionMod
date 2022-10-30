package net.juliantexo.magicprogression.item.custom;

import net.juliantexo.magicprogression.mana.PlayerManaProvider;
import net.juliantexo.magicprogression.networking.ModMessages;
import net.juliantexo.magicprogression.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

public class CombatWandItem extends SwordItem {

    public CombatWandItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(pAttacker instanceof Player) {
            Player player = (Player)pAttacker;
            ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve().get().getMana()), (ServerPlayer) player);
            if(player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve().get().getMana() > 25) {
                player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve().get().subMana(25);

                pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
                    p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }
        }
        return true;

    }

}
