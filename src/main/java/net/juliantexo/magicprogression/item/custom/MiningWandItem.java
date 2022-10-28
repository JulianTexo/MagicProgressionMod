package net.juliantexo.magicprogression.item.custom;

import net.juliantexo.magicprogression.mana.PlayerMana;
import net.juliantexo.magicprogression.mana.PlayerManaProvider;
import net.juliantexo.magicprogression.networking.ModMessages;
import net.juliantexo.magicprogression.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MiningWandItem extends DiggerItem {


    public MiningWandItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties pProperties) {
        super((float)pAttackDamageModifier, pAttackSpeedModifier,pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }


    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide && pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
            if(pEntityLiving instanceof Player) {
                Player player = (Player) pEntityLiving;
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve().get().getMana()), (ServerPlayer) player);
                if(player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve().get().getMana() > 25) {
                    player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve().get().subMana(25);

                    pStack.hurtAndBreak(1, pEntityLiving, (p_40992_) -> {
                        p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                    });
                }
            }
        }

        return true;
    }

}
