package net.zaharenko424.testmod.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkEvent;
import net.neoforged.neoforge.network.simple.SimpleMessage;
import net.zaharenko424.testmod.TransfurManager;
import net.zaharenko424.testmod.capability.ITransfurHandler;
import net.zaharenko424.testmod.capability.TransfurCapability;
import net.zaharenko424.testmod.transfurTypes.AbstractTransfurType;
import org.jetbrains.annotations.NotNull;

import static net.zaharenko424.testmod.TransfurManager.*;

public class ClientboundPlayerTransfurUpdatePacket implements SimpleMessage {

    private final float transfurProgress;
    private final AbstractTransfurType transfurType;
    private final boolean isTransfurred;

    public ClientboundPlayerTransfurUpdatePacket(@NotNull ITransfurHandler handler){
        transfurProgress=handler.getTransfurProgress();
        transfurType=handler.getTransfurType();
        isTransfurred=handler.isTransfurred();
    }

    public ClientboundPlayerTransfurUpdatePacket(@NotNull FriendlyByteBuf buffer){
        CompoundTag tag=buffer.readNbt();
        if(tag==null) throw new RuntimeException("Empty or corrupted packet received!");
        transfurProgress = tag.getFloat(TRANSFUR_PROGRESS_KEY);
        if(tag.contains(TRANSFUR_TYPE_KEY)) transfurType = TransfurManager.getTransfurType(new ResourceLocation(tag.getString(TRANSFUR_TYPE_KEY))); else transfurType=null;
        isTransfurred = tag.getBoolean(TRANSFURRED_KEY);
    }

    public void encode(@NotNull FriendlyByteBuf buffer){
        CompoundTag tag=new CompoundTag();
        tag.putFloat(TRANSFUR_PROGRESS_KEY, transfurProgress);
        if(transfurType!=null) tag.putString(TRANSFUR_TYPE_KEY, transfurType.id.toString());
        tag.putBoolean(TRANSFURRED_KEY, isTransfurred);
        buffer.writeNbt(tag);
    }

    @Override
    public void handleMainThread(NetworkEvent.Context context) {
        LocalPlayer player=Minecraft.getInstance().player;
        if(player==null) return;
        ITransfurHandler handler=player.getCapability(TransfurCapability.CAPABILITY).orElseThrow(TransfurCapability.NO_CAPABILITY_EXC);
        if(!isTransfurred){
            if(handler.isTransfurred()){
                handler.unTransfur();
                player.refreshDimensions();
            } else handler.setTransfurProgress(transfurProgress,transfurType);
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Transfur progress is "+transfurProgress));
            return;
        }
        handler.transfur(transfurType);
        player.refreshDimensions();
    }
}
