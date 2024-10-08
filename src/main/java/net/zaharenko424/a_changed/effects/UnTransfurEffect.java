package net.zaharenko424.a_changed.effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.zaharenko424.a_changed.capability.TransfurHandler;
import net.zaharenko424.a_changed.transfurSystem.TransfurContext;

import javax.annotation.ParametersAreNonnullByDefault;
@ParametersAreNonnullByDefault
public class UnTransfurEffect extends UnRemovableEffect {

    public UnTransfurEffect() {
        super(MobEffectCategory.NEUTRAL, 13816530);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
        return p_295329_==1;
    }

    @Override
    public boolean applyEffectTick(LivingEntity p_19467_, int p_19468_) {
        if(p_19467_ instanceof ServerPlayer player)
            TransfurHandler.nonNullOf(player).unTransfur(TransfurContext.UNTRANSFUR);
        return true;
    }
}