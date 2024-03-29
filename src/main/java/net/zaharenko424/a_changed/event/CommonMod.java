package net.zaharenko424.a_changed.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.zaharenko424.a_changed.AChanged;
import net.zaharenko424.a_changed.capability.GrabCapability;
import net.zaharenko424.a_changed.capability.item.ItemEnergyCapability;
import net.zaharenko424.a_changed.entity.AbstractLatexBeast;
import net.zaharenko424.a_changed.entity.LatexBeast;
import net.zaharenko424.a_changed.entity.WaterLatexBeast;
import net.zaharenko424.a_changed.entity.block.machines.AbstractMachineEntity;
import net.zaharenko424.a_changed.network.ClientPacketHandler;
import net.zaharenko424.a_changed.network.ServerPacketHandler;
import net.zaharenko424.a_changed.network.packets.*;
import net.zaharenko424.a_changed.network.packets.grab.*;
import net.zaharenko424.a_changed.network.packets.transfur.*;
import net.zaharenko424.a_changed.registry.AttachmentRegistry;
import net.zaharenko424.a_changed.registry.BlockEntityRegistry;
import net.zaharenko424.a_changed.registry.BlockRegistry;
import net.zaharenko424.a_changed.registry.ItemRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static net.zaharenko424.a_changed.AChanged.*;
import static net.zaharenko424.a_changed.capability.TransfurCapability.CAPABILITY;
import static net.zaharenko424.a_changed.registry.EntityRegistry.*;
@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(modid = AChanged.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonMod {

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event){
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(BlockRegistry.ORANGE_SAPLING.getId(), BlockRegistry.POTTED_ORANGE_SAPLING);
    }

    @SubscribeEvent
    public static void onRegisterPayload(RegisterPayloadHandlerEvent event){
        IPayloadRegistrar registrar = event.registrar(MODID);

        //Lambda SHOULDN'T be replaced with method reference! -> server will crash

        //Transfur tolerance update
        registrar.common(ClientboundTransfurToleranceSyncPacket.ID, ClientboundTransfurToleranceSyncPacket::new, handler ->
                handler.client((packet, context) -> ClientPacketHandler.INSTANCE.handleTransfurToleranceSync(packet)));

        //Grab
        registrar.common(ClientboundGrabSyncPacket.ID, ClientboundGrabSyncPacket::new, handler ->
                handler.client((packet, context) -> ClientPacketHandler.INSTANCE.handleGrabSyncPacket(packet, context)));
        registrar.play(ClientboundRemoteGrabSyncPacket.ID, ClientboundRemoteGrabSyncPacket::new, handler ->
                handler.client((packet, context) -> ClientPacketHandler.INSTANCE.handleRemoteGrabSyncPacket(packet, context)));
        registrar.play(ServerboundGrabPacket.ID, ServerboundGrabPacket::new, handler ->
                handler.server(ServerPacketHandler.INSTANCE::handleGrabPacket));

        //Grab modes
        registrar.play(ServerboundGrabModePacket.ID, ServerboundGrabModePacket::new, handler ->
                handler.server(ServerPacketHandler.INSTANCE::handleGrabModePacket));
        registrar.play(ServerboundWantToBeGrabbedPacket.ID, ServerboundWantToBeGrabbedPacket::new, handler ->
                handler.server(ServerPacketHandler.INSTANCE::handleWantToBeGrabbedPacket));

        //Transfur data
        registrar.play(ClientboundPlayerTransfurSyncPacket.ID, ClientboundPlayerTransfurSyncPacket::new, handler ->
                handler.client((packet, context) -> ClientPacketHandler.INSTANCE.handlePlayerTransfurSync(packet, context)));
        registrar.play(ClientboundRemotePlayerTransfurSyncPacket.ID, ClientboundRemotePlayerTransfurSyncPacket::new, handler ->
                handler.client((packet, context) -> ClientPacketHandler.INSTANCE.handleRemotePlayerTransfurSync(packet, context)));

        //Transfur screen
        registrar.play(ClientboundOpenTransfurScreenPacket.ID, a -> new ClientboundOpenTransfurScreenPacket(), handler ->
                handler.client((packet, context) -> ClientPacketHandler.INSTANCE.handleOpenTransfurScreen(context)));
        registrar.play(ServerboundTransfurChoicePacket.ID, ServerboundTransfurChoicePacket::new, handler ->
                handler.server(ServerPacketHandler.INSTANCE::handleTransfurChoicePacket));

        //Latex encoder
        registrar.play(ServerboundLatexEncoderScreenPacket.ID, ServerboundLatexEncoderScreenPacket::new, handler ->
                handler.server(ServerPacketHandler.INSTANCE::handleLatexEncoderScreenPacket));

        //Note
        registrar.play(ClientboundOpenNotePacket.ID, ClientboundOpenNotePacket::new, handler ->
                handler.client((packet, context) -> ClientPacketHandler.INSTANCE.handleOpenNotePacket(packet, context)));
        registrar.play(ServerboundEditNotePacket.ID, ServerboundEditNotePacket::new, handler ->
                handler.server(ServerPacketHandler.INSTANCE::handleEditNotePacket));

        //Keypad
        registrar.play(ClientboundOpenKeypadPacket.ID, ClientboundOpenKeypadPacket::new, handler ->
                handler.client((packet, context) -> ClientPacketHandler.INSTANCE.handleOpenKeypadPacket(packet, context)));
        registrar.play(ServerboundTryPasswordPacket.ID, ServerboundTryPasswordPacket::new, handler ->
                handler.server(ServerPacketHandler.INSTANCE::handleTryPasswordPacket));
    }

    private static final List<EntityType<? extends LivingEntity>> transfurrable = List.of(EntityType.PLAYER, EntityType.ZOMBIE,
            EntityType.SKELETON, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.DROWNED, EntityType.PIGLIN_BRUTE,
            EntityType.PIGLIN, EntityType.PILLAGER, EntityType.EVOKER, EntityType.HUSK, EntityType.VILLAGER, EntityType.VINDICATOR,
            EntityType.ZOMBIE_VILLAGER, EntityType.STRAY, EntityType.ZOMBIFIED_PIGLIN, EntityType.ILLUSIONER);

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.registerEntity(GrabCapability.CAPABILITY, EntityType.PLAYER, (player, context) ->
                player.getData(AttachmentRegistry.GRAB_HANDLER));
        for(EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE){
            //Tag DOESN'T WORK for some reason (
            //if(entityType.is(AChanged.TRANSFURRABLE_TAG))
            if(transfurrable.contains(entityType))
                event.registerEntity(CAPABILITY, entityType, (entity, context) -> entity instanceof LivingEntity living
                        ? living.getData(AttachmentRegistry.TRANSFUR_HANDLER) : null);
        }

        //Item
        event.registerItem(Capabilities.EnergyStorage.ITEM, (item, context) ->
                ItemEnergyCapability.getCapability(10000, 128, item), ItemRegistry.POWER_CELL);
        event.registerItem(Capabilities.EnergyStorage.ITEM, (item, context) ->
                ItemEnergyCapability.getCapability(25000, 128, item), ItemRegistry.STUN_BATON);
        event.registerItem(Capabilities.EnergyStorage.ITEM, (item, context) ->
                ItemEnergyCapability.getCapability(50000, 256, item), ItemRegistry.SYRINGE_COIL_GUN);

        event.registerItem(Capabilities.ItemHandler.ITEM, (item, context) ->
                item.getData(AttachmentRegistry.PNEUMATIC_SYRINGE_RIFLE_ITEM_HANDLER), ItemRegistry.PNEUMATIC_SYRINGE_RIFLE);
        event.registerItem(Capabilities.ItemHandler.ITEM, (item, context) ->
                item.getData(AttachmentRegistry.SYRINGE_COIL_GUN_HANDLER), ItemRegistry.SYRINGE_COIL_GUN);

        //BlockEntity
        registerMachineEntityCaps(event, BlockEntityRegistry.CAPACITOR_ENTITY.get());
        registerMachineEntityCaps(event, BlockEntityRegistry.COMPRESSOR_ENTITY.get());
        registerMachineEntityCaps(event, BlockEntityRegistry.DNA_EXTRACTOR_ENTITY.get());
        registerMachineEntityCaps(event, BlockEntityRegistry.GENERATOR_ENTITY.get());
        registerMachineEntityCaps(event, BlockEntityRegistry.LATEX_ENCODER_ENTITY.get());
        registerMachineEntityCaps(event, BlockEntityRegistry.LATEX_PURIFIER_ENTITY.get());

    }

    private static void registerMachineEntityCaps(RegisterCapabilitiesEvent event, BlockEntityType<? extends AbstractMachineEntity<?,?>> type){
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, type, (machine, side) ->
                machine.getCapability(Capabilities.ItemHandler.BLOCK, side));
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, type, (machine, side) ->
                machine.getCapability(Capabilities.EnergyStorage.BLOCK, side));
    }

    @SubscribeEvent
    public static void onAttributeModify(EntityAttributeModificationEvent event){
        event.getTypes().forEach((type)-> {
            if(!event.has(type,AIR_DECREASE_SPEED.get())) event.add(type, AIR_DECREASE_SPEED.get());
            if(!event.has(type,LATEX_RESISTANCE.get())) event.add(type, LATEX_RESISTANCE.get());
        });
    }

    @SubscribeEvent
    public static void onEntityAttributes(@NotNull EntityAttributeCreationEvent event){
        event.put(BEI_FENG.get(), LatexBeast.createAttributes().build());
        event.put(BENIGN.get(), LatexBeast.createAttributes().build());
        event.put(DARK_LATEX_WOLF_MALE.get(), LatexBeast.createAttributes().build());
        event.put(DARK_LATEX_WOLF_FEMALE.get(), LatexBeast.createAttributes().build());
        event.put(GAS_WOLF.get(), LatexBeast.createAttributes().build());

        event.put(HYPNO_CAT.get(), LatexBeast.createAttributes().build());

        event.put(LATEX_SHARK_FEMALE.get(), WaterLatexBeast.createAttributes().build());
        event.put(LATEX_SHARK_MALE.get(), WaterLatexBeast.createAttributes().build());

        event.put(PURE_WHITE_LATEX_WOLF.get(), LatexBeast.createAttributes().build());
        event.put(WHITE_LATEX_WOLF_MALE.get(), LatexBeast.createAttributes().build());
        event.put(WHITE_LATEX_WOLF_FEMALE.get(), LatexBeast.createAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event){
        event.register(BEI_FENG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractLatexBeast::checkLatexBeastSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(DARK_LATEX_WOLF_FEMALE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractLatexBeast::checkLatexBeastSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(DARK_LATEX_WOLF_MALE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractLatexBeast::checkLatexBeastSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(GAS_WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractLatexBeast::checkLatexBeastSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        event.register(HYPNO_CAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractLatexBeast::checkLatexBeastSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        event.register(LATEX_SHARK_FEMALE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterLatexBeast::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_SHARK_MALE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterLatexBeast::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

        event.register(PURE_WHITE_LATEX_WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractLatexBeast::checkLatexBeastSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(WHITE_LATEX_WOLF_FEMALE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractLatexBeast::checkLatexBeastSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(WHITE_LATEX_WOLF_MALE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractLatexBeast::checkLatexBeastSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }
}