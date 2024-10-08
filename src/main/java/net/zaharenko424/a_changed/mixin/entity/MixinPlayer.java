package net.zaharenko424.a_changed.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.zaharenko424.a_changed.block.blocks.VentDuct;
import net.zaharenko424.a_changed.capability.TransfurHandler;
import net.zaharenko424.a_changed.registry.MobEffectRegistry;
import net.zaharenko424.a_changed.transfurSystem.DamageSources;
import net.zaharenko424.a_changed.transfurSystem.TransfurContext;
import net.zaharenko424.a_changed.transfurSystem.TransfurManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    public MixinPlayer(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    /**
     * Apply transfur progress to the thing, that is being attacked by the player.
     */
    @Redirect(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/Entity.hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),method = "attack",allow = 1)
    public boolean hurtProxy(@NotNull Entity target, DamageSource p_19946_, float damage){
        if(target.level().isClientSide) return target.hurt(p_19946_, damage);
        TransfurHandler handler = TransfurHandler.nonNullOf(this);
        if(!getMainHandItem().isEmpty() || !handler.isTransfurred() || handler.getTransfurType().isOrganic()) return target.hurt(p_19946_, damage);
        damage += TransfurManager.LATEX_DAMAGE_BONUS;
        if(!DamageSources.checkTarget(target)) return target.hurt(p_19946_, damage);
        if(target.hurt(DamageSources.transfur(null, this), damage)){
            float tfProgress = 5f;
            if(hasEffect(MobEffectRegistry.ASSIMILATION_BUFF)) tfProgress += 5;
            TransfurHandler.nonNullOf((LivingEntity) target)
                    .addTransfurProgress(tfProgress, Objects.requireNonNull(handler.getTransfurType()), TransfurContext.ADD_PROGRESS_DEF);
            return true;
        }
        return false;
    }

    /**
     * @return  dimensions of specified pose when transfurred.
     */
    @ModifyReturnValue(at = @At("RETURN"), method = "getDefaultDimensions")
    private EntityDimensions onGetDimensions(EntityDimensions original, Pose pose) {
        TransfurHandler handler = TransfurHandler.of(this);
        if(handler != null && handler.isTransfurred()) return handler.getTransfurType().getPoseDimensions(pose);
        return original;
    }

    /**
     * Make player "swim" in vents.
     */
    @ModifyReturnValue(at = @At("TAIL"), method = "canPlayerFitWithinBlocksAndEntitiesWhen")
    private boolean onCanFitWithinBlocksAndEntitiesWhen(boolean original, Pose pose){
        if(pose != Pose.SWIMMING && getInBlockState().getBlock() instanceof VentDuct duct
                && duct.getShape(getInBlockState(), level(), blockPosition(), CollisionContext.empty()).bounds().move(blockPosition()).intersects(getBoundingBox())) {
            return false;
        }
        return original;
    }

    /**
     * Allows player to fly when transfurred as flying latex.
     */
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;canElytraFly(Lnet/minecraft/world/entity/LivingEntity;)Z"),
            method = "tryToStartFallFlying")
    private boolean onTryStartFallFlyingCheck(boolean original){
        if(TransfurManager.hasFallFlyingAbility(this)) return true;
        return original;
    }
}