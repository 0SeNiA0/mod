package net.zaharenko424.testmod.util;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface StateProperties {
    IntegerProperty PART = IntegerProperty.create("part", 0,1);
}