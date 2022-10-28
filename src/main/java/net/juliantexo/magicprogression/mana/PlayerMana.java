package net.juliantexo.magicprogression.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerMana  {

    private int mana;
    private final int MAX_MANA = 200;
    private final int MIN_MANA = 0;

    public int getMana(){
        return mana;
    }

    public void addMana(int add){
        this.mana = Math.min(mana + add, MAX_MANA);
    }

    public void subMana(int sub){
        this.mana = Math.max(mana - sub, MIN_MANA);
    }

    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
    }

    public void loadNBTData(CompoundTag nbt){
        mana = nbt.getInt("mana");
    }

}
