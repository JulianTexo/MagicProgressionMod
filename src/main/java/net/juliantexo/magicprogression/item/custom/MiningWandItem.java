package net.juliantexo.magicprogression.item.custom;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class MiningWandItem extends DiggerItem {


    public MiningWandItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties pProperties) {
        super((float)pAttackDamageModifier, pAttackSpeedModifier,pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }


}
