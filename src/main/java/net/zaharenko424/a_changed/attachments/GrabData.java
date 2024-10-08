package net.zaharenko424.a_changed.attachments;

import io.netty.buffer.Unpooled;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zaharenko424.a_changed.AChanged;
import net.zaharenko424.a_changed.ability.AbilityData;
import net.zaharenko424.a_changed.ability.GrabMode;
import net.zaharenko424.a_changed.entity.LatexBeast;
import net.zaharenko424.a_changed.network.packets.ability.ClientboundAbilitySyncPacket;
import net.zaharenko424.a_changed.registry.AbilityRegistry;
import net.zaharenko424.a_changed.registry.AttachmentRegistry;
import net.zaharenko424.a_changed.registry.MobEffectRegistry;
import net.zaharenko424.a_changed.transfurSystem.TransfurManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Whoever modifies the data is responsible for its synchronisation!
 */
public class GrabData implements AbilityData {

    public static final Serializer SERIALIZER = new Serializer();

    public final LivingEntity holder;
    private LivingEntity grabbedEntity;
    private LivingEntity grabbedBy;
    private GrabMode mode = GrabMode.ASSIMILATE;
    private boolean wantsToBeGrabbed;
    private boolean activated;

    public GrabData(IAttachmentHolder holder){
        if(!(holder instanceof LivingEntity entity)) throw new IllegalArgumentException();
        this.holder = entity;
    }

    public static GrabData dataOf(LivingEntity holder){//TODO potentially return null for inappropriate holders instead of throwing in <init>
        return holder.getData(AttachmentRegistry.GRAB_DATA);
    }

    public LivingEntity getGrabbedEntity() {
        return grabbedEntity;
    }

    public LivingEntity getGrabbedBy() {
        return grabbedBy;
    }

    public void setGrabbedBy(LivingEntity grabbedBy) {
        if(holder.level().isClientSide) return;

        this.grabbedBy = grabbedBy;
        if(grabbedEntity != null) drop();
        else syncClients();
    }

    public GrabMode getMode() {
        return mode;
    }

    public void setMode(GrabMode mode) {
        if(holder.level().isClientSide) return;
        if(this.mode == mode) return;

        if(((this.mode == GrabMode.FRIENDLY || mode == GrabMode.FRIENDLY) && this.mode != mode)
                || (grabbedEntity instanceof Player player1 && !TransfurManager.wantsToBeGrabbed(player1) && !mode.givesDebuffToTarget)) drop();
        this.mode = mode;
        if(grabbedEntity != null) {
            grab(grabbedEntity, true);
        } else syncClients();
    }

    public boolean wantsToBeGrabbed() {
        return wantsToBeGrabbed;
    }

    public void setWantsToBeGrabbed(boolean wantsToBeGrabbed) {
        if(holder.level().isClientSide || wantsToBeGrabbed == this.wantsToBeGrabbed) return;

        if(!wantsToBeGrabbed) escape(false);

        this.wantsToBeGrabbed = wantsToBeGrabbed;
        syncClients();
    }

    public void escape(boolean force){
        if(holder.level().isClientSide || grabbedBy == null) return;

        GrabData data = dataOf(grabbedBy);
        GrabMode mode = data.getMode();
        if(!mode.givesDebuffToTarget || force) {
            if(mode.givesDebuffToTarget) data.holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
            data.drop();
        }
    }

    public boolean isActivated() {
        return activated;
    }

    public static final int grabCooldown = 160;
    public static final int grabDuration = 160;

    public boolean canGrab(LivingEntity potentialTarget) {//TODO limit grabbable entities to transfurrable & latexes(?)
        return potentialTarget != null && getGrabbedBy() == null && getGrabbedEntity() == null
                && !holder.hasEffect(MobEffectRegistry.GRAB_COOLDOWN)
                && (potentialTarget.getType().is(AChanged.TRANSFURRABLE_TAG) || potentialTarget instanceof LatexBeast)
                && mode.checkTarget(potentialTarget);
    }

    public static boolean canGrab(LivingEntity holder, LivingEntity potentialTarget){
        GrabData data = dataOf(holder);
        return data.canGrab(potentialTarget);
    }

    public void grab(@NotNull LivingEntity target) {
        if(holder.level().isClientSide) return;
        grab(target, false);
    }

    private void grab(@NotNull LivingEntity target, boolean force){
        if(!force && !canGrab(target)) return;

        grabbedEntity = target;
        dataOf(grabbedEntity).setGrabbedBy(holder);
        if(target instanceof ServerPlayer player1 && mode == GrabMode.FRIENDLY) {
            player1.setGameMode(GameType.SPECTATOR);
            player1.addEffect(new MobEffectInstance(MobEffectRegistry.FRIENDLY_GRAB, -1, 0, false, false));
        }

        if(mode.givesDebuffToTarget) grabbedEntity.addEffect(new MobEffectInstance(MobEffectRegistry.GRABBED_DEBUFF, grabDuration, 0, false, false));
        if(mode.givesDebuffToSelf) holder.addEffect(new MobEffectInstance(MobEffectRegistry.HOLDING_DEBUFF, -1, 0, false, false));
        activated = true;
        syncClients();
    }

    public void drop() {
        if(holder.level().isClientSide) return;
        if(grabbedEntity == null) return;

        if(grabbedEntity.isAlive()){
            if(grabbedEntity instanceof ServerPlayer player1 && mode == GrabMode.FRIENDLY) {
                player1.setCamera(null);
                player1.setGameMode(GameType.SURVIVAL);
                player1.removeEffect(MobEffectRegistry.FRIENDLY_GRAB);
            }
            if(mode.givesDebuffToTarget) grabbedEntity.removeEffect(MobEffectRegistry.GRABBED_DEBUFF);
            dataOf(grabbedEntity).setGrabbedBy(null);
        }

        grabbedEntity = null;
        if(mode.givesDebuffToSelf) holder.removeEffect(MobEffectRegistry.HOLDING_DEBUFF);
        holder.addEffect(new MobEffectInstance(MobEffectRegistry.GRAB_COOLDOWN, grabCooldown, 0, false, false));
        activated = false;
        syncClients();
    }

    public void syncClients(){
        if(holder.level().isClientSide) return;
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(holder, updatePacket());
    }

    public void syncClient(@NotNull ServerPlayer packetReceiver) {
        PacketDistributor.sendToPlayer(packetReceiver, updatePacket());
    }

    private ClientboundAbilitySyncPacket updatePacket() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer(16));
        buf.writeVarInt(grabbedEntity != null ? grabbedEntity.getId() : -1);
        buf.writeVarInt(grabbedBy != null ? grabbedBy.getId() : -1);
        buf.writeEnum(mode);
        buf.writeBoolean(wantsToBeGrabbed);
        buf.writeBoolean(activated);
        return new ClientboundAbilitySyncPacket(holder.getId(), AbilityRegistry.GRAB_ABILITY.getId(), buf);
    }

    public void fromPacket(FriendlyByteBuf buf) {
        Level level = holder.level();
        if(!level.isClientSide) return;

        int id = buf.readVarInt();
        grabbedEntity = id == -1 ? null : level.getEntity(id) instanceof LivingEntity entity ? entity : null;
        id = buf.readVarInt();
        grabbedBy = id == -1 ? null : level.getEntity(id) instanceof LivingEntity entity ? entity : null;
        mode = buf.readEnum(GrabMode.class);
        wantsToBeGrabbed = buf.readBoolean();
        activated = buf.readBoolean();
    }

    public static class Serializer implements IAttachmentSerializer<CompoundTag, GrabData> {

        private Serializer() {}

        @Override
        public @NotNull GrabData read(@NotNull IAttachmentHolder holder, @NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookup) {
            GrabData data = new GrabData(holder);
            if(!(holder instanceof Player)) return data;

            data.mode = GrabMode.valueOf(tag.getString("mode"));
            data.wantsToBeGrabbed = tag.getBoolean("wantToBeGrabbed");
            return data;
        }

        @Override
        public @Nullable CompoundTag write(@NotNull GrabData data, HolderLookup.@NotNull Provider lookup) {
            if(!(data.holder instanceof Player)) return null;

            CompoundTag tag = new CompoundTag();
            tag.putString("mode", data.mode.toString());
            tag.putBoolean("wantToBeGrabbed", data.wantsToBeGrabbed);
            return tag;
        }
    }
}