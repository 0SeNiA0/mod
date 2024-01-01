package net.zaharenko424.a_changed.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.NetworkEvent;
import net.neoforged.neoforge.network.simple.SimpleMessage;
import net.zaharenko424.a_changed.client.screen.KeypadScreen;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ClientboundOpenKeypadPacket implements SimpleMessage {

    private final boolean isPasswordSet;
    private final int length;
    private final BlockPos pos;

    public ClientboundOpenKeypadPacket(boolean isPasswordSet, int length, BlockPos pos){
        this.isPasswordSet=isPasswordSet;
        this.length=length;
        this.pos=pos;
    }

    public ClientboundOpenKeypadPacket(FriendlyByteBuf buffer){
        isPasswordSet=buffer.readBoolean();
        length=buffer.readInt();
        pos=buffer.readBlockPos();
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(isPasswordSet);
        buffer.writeInt(length);
        buffer.writeBlockPos(pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleMainThread(NetworkEvent.Context context) {
        if(Minecraft.getInstance().player==null) return;
        Minecraft.getInstance().setScreen(new KeypadScreen(isPasswordSet,length,pos));
    }
}