package net.zaharenko424.a_changed.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.zaharenko424.a_changed.entity.block.PileOfOrangesEntity;
import net.zaharenko424.a_changed.registry.ItemRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PileOfOranges extends Block implements EntityBlock {

    public PileOfOranges(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if(!(pLevel.getBlockEntity(pPos) instanceof PileOfOrangesEntity oranges)) return Shapes.empty();
        return oranges.getFinalShape();
    }

    @Override
    protected void spawnDestroyParticles(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState) {}

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(pLevel.isClientSide || pHand != InteractionHand.MAIN_HAND) return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        Vec3 clickedPos = pHit.getLocation();
        if(!(pLevel.getBlockEntity(pPos) instanceof PileOfOrangesEntity oranges)) return InteractionResult.FAIL;
        ItemStack item = pPlayer.getMainHandItem();

        if(item.is(ItemRegistry.ORANGE_ITEM)){
            if(!validateClickPos(clickedPos, pPos) || !oranges.addOrange(clickedPos, (int) pPlayer.yHeadRot)) return InteractionResult.PASS;
            if(!pPlayer.isCreative()) item.shrink(1);
            return InteractionResult.SUCCESS;
        }

        if(item.isEmpty()){
            item = oranges.removeOrange(clickedPos);
            if(oranges.isEmpty()) pLevel.removeBlock(pPos, false);
            if(!item.isEmpty()){
                ItemHandlerHelper.giveItemToPlayer(pPlayer, item);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    private boolean validateClickPos(Vec3 clickPos, BlockPos pos){
        return new AABB(pos).deflate(2 / 16f).move(0, -2 / 16f, 0).contains(clickPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new PileOfOrangesEntity(pPos, pState);
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState pNewState, boolean pMovedByPiston) {
        if(!level.isClientSide && level.getBlockEntity(pos) instanceof PileOfOrangesEntity oranges){
            oranges.onRemove();
        }
        super.onRemove(pState, level, pos, pNewState, pMovedByPiston);
    }
}