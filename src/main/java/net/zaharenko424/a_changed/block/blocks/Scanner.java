package net.zaharenko424.a_changed.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zaharenko424.a_changed.block.HorizontalDirectionalBlock;
import net.zaharenko424.a_changed.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Scanner extends HorizontalDirectionalBlock {

    private static final VoxelShape SHAPE_NORTH = Shapes.box(0.0625, 0.1875, 0.625, 0.9375, 0.8125, 1);
    private static final VoxelShape SHAPE_EAST = Shapes.box(0, 0.1875, 0.0625, 0.375, 0.8125, 0.9375);
    private static final VoxelShape SHAPE_SOUTH = Shapes.box(0.0625, 0.1875, 0, 0.9375, 0.8125, 0.375);
    private static final VoxelShape SHAPE_WEST = Shapes.box(0.625, 0.1875, 0.0625, 1, 0.8125, 0.9375);

    public Scanner(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends net.minecraft.world.level.block.HorizontalDirectionalBlock> codec() {
        return simpleCodec(Scanner::new);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return switch (p_60555_.getValue(FACING)){
            case EAST -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    public boolean use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_) {
        if(p_60504_.isClientSide) return false;

        ((ServerPlayer) p_60506_).setRespawnPosition(p_60504_.dimension(), p_60506_.blockPosition(), p_60506_.getYHeadRot(), true, true);
        p_60504_.playSound(null, p_60505_, SoundRegistry.SAVE.get(), SoundSource.BLOCKS);
        return true;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        return use(state, level, pos, player) ? InteractionResult.SUCCESS_NO_ITEM_USED : super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        return use(state, level, pos, player) ? ItemInteractionResult.SUCCESS : super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}