package net.zaharenko424.a_changed.entity.block.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EmptyEnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class AbstractProxyWire extends BlockEntity {

    public static final int BANDWIDTH = 256;
    private WireNetworkCache networkCache;

    public AbstractProxyWire(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void tickNetwork(){
        long currentTick = level.getGameTime();
        if(networkCache != null && networkCache.isValid(currentTick)) return;
        updateNetworkCache(currentTick);

        if(!networkCache.isWorthTicking()) return;

        HashMap<Pair<IEnergyStorage, BlockEntity>, Integer> consumers = networkCache.getConsumersMapped();
        HashMap<Pair<IEnergyStorage, BlockEntity>, Integer> providers = networkCache.getProvidersMapped();

        int totalConsume = networkCache.getTotalConsume();
        int totalProvide = networkCache.getTotalProvide();

        float transferPercentage = (float) totalProvide / totalConsume;

        float bandwidthPercentage = (float) totalConsume / BANDWIDTH;
        float mul = totalConsume > totalProvide ? transferPercentage : 1;
        float consumeMul = bandwidthPercentage > 1 ? Math.min(1 / bandwidthPercentage, mul) : mul;

        bandwidthPercentage = (float) totalProvide / BANDWIDTH;
        mul = totalProvide > totalConsume ? 1 / transferPercentage : 1;
        float provideMul = bandwidthPercentage > 1 ? Math.min(1/ bandwidthPercentage, mul) : mul;

        float[] overflow = new float[]{0};

        consumers.forEach((pair, amount) -> {
            float toConsume = amount * consumeMul;
            overflow[0] += toConsume % 1;
            int i = calculateOverflow(overflow, (1 - consumeMul) * amount);
            pair.getKey().receiveEnergy((int) toConsume + i, false);
            updateBlockEntity(pair.getValue());
        });

        providers.forEach((pair, amount) -> {
            pair.getKey().extractEnergy((int) (amount * provideMul) - calculateOverflow(overflow, amount), false);
            updateBlockEntity(pair.getValue());
        });
    }

    private int calculateOverflow(float @NotNull [] overflow, float min){
        if(overflow[0] >= 1){
            int i = (int) Math.min(overflow[0], min);
            overflow[0] -= i;
            return i;
        }
        return 0;
    }

    private void updateBlockEntity(BlockEntity entity){
        if(entity instanceof AbstractMachineEntity<?,?> machine) machine.update();
        else entity.setChanged();
    }

    public WireNetworkCache getNetworkCache(){
        long currentTick = level.getGameTime();
        if(networkCache != null && networkCache.isValid(currentTick)) return networkCache;
        tickNetwork();
        return networkCache;
    }

    private void updateNetworkCache(long currentTick){
        if(networkCache == null) networkCache = new WireNetworkCache();
        if(networkCache.isValid(currentTick)) return;
        networkCache.update(currentTick);
        cacheNetwork(networkCache);
    }

    private void cacheNetwork(@NotNull WireNetworkCache cache){
        networkCache = cache;
        networkCache.addVisited(this);
        BlockPos pos;
        BlockEntity entity;
        IEnergyStorage storage;
        for(Direction direction : Direction.values()){
            pos = worldPosition.relative(direction);
            if(!level.isLoaded(pos)) continue;
            entity = level.getBlockEntity(pos);
            if(entity instanceof AbstractProxyWire wire){
                if(!networkCache.isVisited(wire)) wire.cacheNetwork(networkCache);
                continue;
            }
            if(entity == null || !entity.getCapability(Capabilities.ENERGY).isPresent()) continue;
            storage = entity.getCapability(Capabilities.ENERGY).orElse(EmptyEnergyStorage.INSTANCE);
            if(storage.canReceive()) networkCache.addConsumer(storage, entity);
            else if(storage.canExtract()) networkCache.addProvider(storage, entity);
        }
    }
}