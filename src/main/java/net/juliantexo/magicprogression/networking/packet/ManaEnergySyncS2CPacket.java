package net.juliantexo.magicprogression.networking.packet;

import net.juliantexo.magicprogression.block.custom.ManaFurnaceBlock;
import net.juliantexo.magicprogression.block.entity.ManaFurnaceBlockEntity;
import net.juliantexo.magicprogression.client.ClientManaData;
import net.juliantexo.magicprogression.screen.ManaFurnaceMenu;
import net.juliantexo.magicprogression.screen.ManaFurnaceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaEnergySyncS2CPacket {
    private final int mana;
    private final BlockPos pos;

    public ManaEnergySyncS2CPacket(int mana, BlockPos pos){
        this.mana = mana;
        this.pos = pos;
    }

    public ManaEnergySyncS2CPacket(FriendlyByteBuf buf){
        this.mana = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(mana);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ManaFurnaceBlockEntity blockEntity){

                blockEntity.setEneryLevel(mana);

                if(Minecraft.getInstance().player.containerMenu instanceof ManaFurnaceMenu menu &&
                menu.getBLockEntity().getBlockPos() == pos){
                    blockEntity.setEneryLevel(mana);
                }

            }
        });
        return true;
    }

}
