package net.zaharenko424.a_changed;

import com.mojang.logging.LogUtils;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zaharenko424.a_changed.item.LatexSyringeItem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.zaharenko424.a_changed.registry.BlockEntityRegistry.BLOCK_ENTITIES;
import static net.zaharenko424.a_changed.registry.BlockRegistry.*;
import static net.zaharenko424.a_changed.registry.EntityRegistry.ENTITIES;
import static net.zaharenko424.a_changed.registry.FluidRegistry.FLUIDS;
import static net.zaharenko424.a_changed.registry.FluidRegistry.FLUID_TYPES;
import static net.zaharenko424.a_changed.registry.ItemRegistry.*;
import static net.zaharenko424.a_changed.registry.MobEffectRegistry.EFFECTS;
import static net.zaharenko424.a_changed.registry.SoundRegistry.SOUNDS;
import static net.zaharenko424.a_changed.registry.TransfurRegistry.TRANSFUR_REGISTRY;
import static net.zaharenko424.a_changed.registry.TransfurRegistry.TRANSFUR_TYPES;

@ParametersAreNonnullByDefault
@Mod(AChanged.MODID)
public class AChanged {

    public static final String MODID = "a_changed";
    public static final Logger LOGGER = LogUtils.getLogger();

    //Registries
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE,MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);

    //Attributes
    public static final DeferredHolder<Attribute,Attribute> AIR_DECREASE_SPEED = ATTRIBUTES.register("air_decrease_speed",
            () -> new RangedAttribute("attribute."+MODID+".air_decrease_speed",1,0,256));
    public static final DeferredHolder<Attribute,Attribute> LATEX_RESISTANCE = ATTRIBUTES.register("latex_resistance",
            () -> new RangedAttribute("attribute."+MODID+".latex_resistance",0,0,1));

    //Particles
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> BLUE_GAS_PARTICLE = PARTICLE_TYPES.register("blue_gas", ()-> new SimpleParticleType(true));

    //Tags
    public static final TagKey<EntityType<?>> TRANSFURRABLE_TAG = TagKey.create(Registries.ENTITY_TYPE, resourceLoc("transfurrable"));
    public static final TagKey<EntityType<?>> SEWAGE_SYSTEM_CONSUMABLE = TagKey.create(Registries.ENTITY_TYPE, resourceLoc("sewage_system_consumable"));

    //Game rules
    public static final GameRules.Key<GameRules.BooleanValue> CHOOSE_TF_OR_DIE = GameRules.register("chooseTransfurOrDie", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> KEEP_TRANSFUR = GameRules.register("keepTransfurOnDeath", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> TRANSFUR_IS_DEATH = GameRules.register("transfurIsDeath", GameRules.Category.MISC, GameRules.BooleanValue.create(true));

    //Creative tabs
    static {
        CREATIVE_MODE_TABS.register("item_tab", () -> CreativeModeTab.builder()
            .icon(() -> SYRINGE_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.a_changed.item_tab"))
            .displayItems((parameters, output) -> {
                output.accept(ORANGE_ITEM);
                output.accept(ORANGE_JUICE_ITEM);
                output.accept(ORANGE_LEAVES_ITEM);
                output.accept(ORANGE_SAPLING_ITEM);
                output.accept(ORANGE_TREE_LOG_ITEM);
                output.accept(WHITE_LATEX_ITEM);
                output.accept(DARK_LATEX_ITEM);
                output.accept(WHITE_LATEX_BLOCK_ITEM);
                output.accept(DARK_LATEX_BLOCK_ITEM);
                output.accept(HAZARD_BLOCK_ITEM);
                output.accept(HAZARD_LAB_BLOCK_ITEM);
                output.accept(BLUE_LAB_TILE_ITEM);
                output.accept(BOLTED_BLUE_LAB_TILE_ITEM);
                output.accept(BOLTED_LAB_TILE_ITEM);
                output.accept(BROWN_LAB_BLOCK_ITEM);
                output.accept(CARPET_BLOCK_ITEM);
                output.accept(CONNECTED_BLUE_LAB_TILE_ITEM);
                output.accept(CONNECTED_LAB_TILE_ITEM);
                output.accept(LAB_BLOCK_ITEM);
                output.accept(LAB_TILE_ITEM);
                output.accept(ORANGE_LAB_BLOCK_ITEM);
                output.accept(SMART_SEWAGE_SYSTEM_ITEM);
                output.accept(STRIPED_ORANGE_LAB_BLOCK_ITEM);
                output.accept(VENT_WALL_ITEM);
                output.accept(YELLOW_LAB_BLOCK_ITEM);

                output.accept(AIR_CONDITIONER_ITEM);
                output.accept(CARDBOARD_BOX);
                output.accept(CHAIR_ITEM);
                output.accept(COMPUTER_ITEM);
                output.accept(DARK_LATEX_CRYSTAL_ITEM);
                output.accept(DARK_LATEX_CRYSTAL_SHARD);
                output.accept(GREEN_CRYSTAL_ITEM);
                output.accept(GREEN_CRYSTAL_SHARD);
                output.accept(GAS_TANK_ITEM);
                output.accept(KEYPAD_ITEM);
                output.accept(LAB_DOOR_ITEM);
                output.accept(LATEX_CONTAINER_ITEM);
                output.accept(LIBRARY_DOOR_ITEM);
                output.accept(MAINTENANCE_DOOR_ITEM);
                output.accept(METAL_BOX_ITEM);
                output.accept(NOTE_ITEM);
                output.accept(NOTEPAD_ITEM);
                output.accept(SCANNER_ITEM);
                output.accept(SMALL_CARDBOARD_BOX_ITEM);
                output.accept(TABLE_ITEM);
                output.accept(TALL_CARDBOARD_BOX_ITEM);
                output.accept(TRAFFIC_CONE);
                output.accept(VENT_ITEM);

                output.accept(HAZMAT_HELMET);
                output.accept(HAZMAT_CHESTPLATE);
                output.accept(HAZMAT_LEGGINGS);
                output.accept(HAZMAT_BOOTS);

                output.accept(LATEX_SOLVENT_BUCKET);
                output.accept(WHITE_LATEX_BUCKET);
                output.accept(DARK_LATEX_BUCKET);

                output.accept(SYRINGE_ITEM);
                output.accept(UNTRANSFUR_SYRINGE_ITEM);
                TRANSFUR_REGISTRY.stream().forEach((tf)->output.accept(LatexSyringeItem.encodeTransfur(tf.id)));
            }).build());
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation resourceLoc(String path){
        return new ResourceLocation(MODID,path);
    }

    @Contract("_ -> new")
    public static @NotNull ResourceLocation textureLoc(String path){
        return new ResourceLocation(MODID,"textures/"+path+".png");
    }

    public static @NotNull ResourceLocation textureLoc(ResourceLocation loc){
        return loc.withPrefix("textures/").withSuffix(".png");
    }

    public AChanged() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TRANSFUR_TYPES.register(modEventBus);
        ATTRIBUTES.register(modEventBus);
        EFFECTS.register(modEventBus);
        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        FLUID_TYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        SOUNDS.register(modEventBus);
        PARTICLE_TYPES.register(modEventBus);
    }
}