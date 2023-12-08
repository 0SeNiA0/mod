package net.zaharenko424.testmod.worldgen;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.zaharenko424.testmod.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OrangeTreeGrower extends AbstractTreeGrower {

    public static ResourceKey<ConfiguredFeature<?,?>> TREE = Utils.featureKey("orange_tree");

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource p_222910_, boolean p_222911_) {
        return TREE;
    }
}