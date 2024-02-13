package net.zaharenko424.a_changed.registry;

import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.function.Supplier;

public class DNAType {

    private final Supplier<ItemStack> material;

    public DNAType(Supplier<ItemStack> material) {
        this.material = material;
    }

    public ItemStack getMaterial(){
        return material.get();
    }

    @Override
    public String toString() {
        return Objects.requireNonNull(DNATypeRegistry.DNA_TYPE_REGISTRY.getKey(this),
                "Tried to get a key of non registered DNAType!").toString();
    }
}