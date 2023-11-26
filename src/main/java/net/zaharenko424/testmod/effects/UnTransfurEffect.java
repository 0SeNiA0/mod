package net.zaharenko424.testmod.effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.zaharenko424.testmod.TransfurManager;
import org.jetbrains.annotations.NotNull;

public class UnTransfurEffect extends MobEffect {
    public UnTransfurEffect() {
        super(MobEffectCategory.NEUTRAL, 128);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
        return p_295329_==1;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity p_19467_, int p_19468_) {
        if(p_19467_ instanceof ServerPlayer player){
            TransfurManager.unTransfur(player);
        }
    }
}