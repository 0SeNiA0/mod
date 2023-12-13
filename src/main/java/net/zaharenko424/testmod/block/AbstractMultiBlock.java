package net.zaharenko424.testmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@SuppressWarnings("deprecation")
public abstract class AbstractMultiBlock extends Block {

    public AbstractMultiBlock(Properties p_54120_) {
        super(p_54120_);
    }

    protected abstract IntegerProperty part();

    @Override
    public @NotNull BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        BlockPos pos=getMainPos(p_60541_,p_60545_);
        BlockState air= Blocks.AIR.defaultBlockState();
        if(p_60545_==pos){
            pos=getSecondaryPos(p_60541_,p_60545_);
            return p_60544_.getBlockState(pos).is(this)?p_60541_:air;
        }
        return canSurvive(p_60541_,p_60544_,p_60545_)?p_60541_:air;
    }

    @Override
    public void playerWillDestroy(Level p_49852_, BlockPos p_49853_, BlockState p_49854_, Player p_49855_) {
        BlockPos mainPos=getMainPos(p_49854_,p_49853_);
        BlockState state=p_49852_.getBlockState(mainPos);
        if (!p_49852_.isClientSide&&state.is(this)&& p_49855_.isCreative()) {
            p_49852_.setBlockAndUpdate(mainPos,Blocks.AIR.defaultBlockState());
            p_49852_.levelEvent(p_49855_, 2001, mainPos, Block.getId(state));
        }
        super.playerWillDestroy(p_49852_, p_49853_, p_49854_, p_49855_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_.add(part()));
    }

    protected abstract BlockPos getMainPos(BlockState state, BlockPos pos);

    protected abstract BlockPos getSecondaryPos(BlockState state, BlockPos pos);
}