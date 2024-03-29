package net.zaharenko424.a_changed.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public interface SlotAwareRecipe <C extends Container> extends Recipe<C> {

    @Override
    default boolean matches(@NotNull C c, @NotNull Level level) {
        return matches(c, 0, level);
    }

    boolean matches(@NotNull C container, int slot, @NotNull Level level);

    default @NotNull ItemStack assemble(@NotNull C pContainer, @NotNull RegistryAccess pRegistryAccess){
        return assemble(pContainer, 0);
    }

    ItemStack assemble(C container, int slot);
}