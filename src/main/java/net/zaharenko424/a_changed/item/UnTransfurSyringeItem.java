package net.zaharenko424.a_changed.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.zaharenko424.a_changed.registry.ItemRegistry;
import net.zaharenko424.a_changed.transfurSystem.TransfurEvent;
import net.zaharenko424.a_changed.transfurSystem.TransfurManager;
import org.jetbrains.annotations.NotNull;

public class UnTransfurSyringeItem extends AbstractSyringe{
    public UnTransfurSyringeItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if(!TransfurManager.isTransfurred(pPlayer)) return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack p_41409_, @NotNull Level p_41410_, @NotNull LivingEntity p_41411_) {
        Player player= (Player) p_41411_;
        if(!p_41410_.isClientSide) TransfurEvent.UNTRANSFUR.accept((ServerPlayer) player);
        return onUse(p_41409_,new ItemStack(ItemRegistry.SYRINGE_ITEM.get()),player);
    }
}