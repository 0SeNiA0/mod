package net.zaharenko424.a_changed.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zaharenko424.a_changed.AChanged;
import net.zaharenko424.a_changed.DNAType;
import net.zaharenko424.a_changed.item.BloodSyringe;
import org.jetbrains.annotations.NotNull;

import static net.zaharenko424.a_changed.AChanged.MODID;

public class DNATypeRegistry {

    public static final DeferredRegister<DNAType> DNA_TYPES = DeferredRegister.create(AChanged.resourceLoc("dna_registry"), MODID);
    public static final Registry<DNAType> DNA_TYPE_REGISTRY = DNA_TYPES.makeRegistry(builder -> {});

    public static final DeferredHolder<DNAType, DNAType> APPLE_DNA = DNA_TYPES.register("apple", ()-> new DNAType(Items.APPLE::getDefaultInstance));
    public static final DeferredHolder<DNAType, DNAType> CAT_DNA = entityDna("cat", EntityType.CAT);
    public static final DeferredHolder<DNAType, DNAType> COD_DNA = entityDna("cod", EntityType.COD);
    public static final DeferredHolder<DNAType, DNAType> SALMON_DNA = entityDna("salmon", EntityType.SALMON);
    public static final DeferredHolder<DNAType, DNAType> WOLF_DNA = entityDna("wolf", EntityType.WOLF);

    private static @NotNull DeferredHolder<DNAType, DNAType> entityDna(String name, EntityType<?> entityType){
        return DNA_TYPES.register(name, ()-> new DNAType(()-> BloodSyringe.encodeEntityType(entityType, null)));
    }
}