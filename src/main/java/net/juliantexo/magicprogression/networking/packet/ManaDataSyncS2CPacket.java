package net.juliantexo.magicprogression.networking.packet;

import net.juliantexo.magicprogression.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaDataSyncS2CPacket {
    private final int mana;

    public ManaDataSyncS2CPacket(int mana){
        this.mana = mana;
    }

    public ManaDataSyncS2CPacket(FriendlyByteBuf buf){
        this.mana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(mana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //HERE WE ARE ON THE CLIENT
            ClientManaData.setPlayerMana(mana);
        });
        return true;
    }

}
