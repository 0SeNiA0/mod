package net.zaharenko424.a_changed.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.zaharenko424.a_changed.block.blocks.VentDuct;
import net.zaharenko424.a_changed.capability.ITransfurHandler;
import net.zaharenko424.a_changed.capability.TransfurCapability;
import net.zaharenko424.a_changed.registry.MobEffectRegistry;
import net.zaharenko424.a_changed.transfurSystem.DamageSources;
import net.zaharenko424.a_changed.transfurSystem.TransfurEvent;
import net.zaharenko424.a_changed.transfurSystem.TransfurManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    public MixinPlayer(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Redirect(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/Entity.hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),method = "attack",allow = 1)
    public boolean hurtProxy(@NotNull Entity target, DamageSource p_19946_, float damage){
        if(target.level().isClientSide) return target.hurt(p_19946_, damage);
        ITransfurHandler handler = TransfurCapability.nonNullOf(this);
        if(!getMainHandItem().isEmpty() || !handler.isTransfurred() || handler.getTransfurType().isOrganic()) return target.hurt(p_19946_, damage);
        damage += TransfurManager.LATEX_DAMAGE_BONUS;
        if(!DamageSources.checkTarget(target)) return target.hurt(p_19946_, damage);
        if(target.hurt(DamageSources.transfur(null, this), damage)){
            float tfProgress = 5f;
            if(hasEffect(MobEffectRegistry.ASSIMILATION_BUFF.get())) tfProgress += 5;
            if(hasEffect(MobEffects.DAMAGE_BOOST)) tfProgress += getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() + 1;
            TransfurEvent.ADD_TRANSFUR_DEF.accept((LivingEntity) target, Objects.requireNonNull(handler.getTransfurType()), tfProgress);
            return true;
        }
        return false;
    }

    @Inject(at = @At("HEAD"), method = "getDimensions", cancellable = true)
    private void onGetDimensions(Pose p_36166_, CallbackInfoReturnable<EntityDimensions> ci) {
        ITransfurHandler handler = getCapability(TransfurCapability.CAPABILITY);
        if(handler == null || !handler.isTransfurred()) return;
        ci.cancel();
        ci.setReturnValue(handler.getTransfurType().getPoseDimensions(p_36166_));
    }

    @Inject(at = @At("HEAD"), method = "getStandingEyeHeight", cancellable = true)
    private void onGetEyeHeight(Pose p_36259_, EntityDimensions p_36260_, CallbackInfoReturnable<Float> ci){
        ITransfurHandler handler = getCapability(TransfurCapability.CAPABILITY);
        if(handler == null || !handler.isTransfurred()) return;
        ci.cancel();
        ci.setReturnValue(handler.getTransfurType().getEyeHeight(p_36259_));
    }

    @Inject(at = @At("HEAD"), method = "canPlayerFitWithinBlocksAndEntitiesWhen", cancellable = true)
    private void onCanFitWithinBlocksAndEntitiesWhen(Pose pPose, CallbackInfoReturnable<Boolean> cir){
        if(pPose != Pose.SWIMMING && getFeetBlockState().getBlock() instanceof VentDuct duct
                && duct.getShape(getFeetBlockState(), level(), blockPosition(), CollisionContext.empty()).bounds().move(blockPosition()).intersects(getBoundingBox())) {
            cir.setReturnValue(false);
        }
    }
}