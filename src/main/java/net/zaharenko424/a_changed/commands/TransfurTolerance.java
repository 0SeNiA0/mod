package net.zaharenko424.a_changed.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zaharenko424.a_changed.network.PacketHandler;
import net.zaharenko424.a_changed.network.packets.ClientboundTransfurToleranceUpdatePacket;
import net.zaharenko424.a_changed.transfurSystem.TransfurDamageSource;
import net.zaharenko424.a_changed.transfurSystem.TransfurEvent;
import net.zaharenko424.a_changed.transfurSystem.TransfurManager;
import org.jetbrains.annotations.NotNull;

import static net.zaharenko424.a_changed.AChanged.LOGGER;

public class TransfurTolerance {

    public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(
                Commands.literal("transfur_tolerance")
                        .then(
                                Commands.literal("get")
                                        .executes(
                                                context->get(context.getSource())
                                        )
                        )
                        .requires(source->source.hasPermission(Commands.LEVEL_ADMINS))
                        .then(
                                Commands.literal("set")
                                        .then(
                                                Commands.argument("tolerance", FloatArgumentType.floatArg(0))
                                                        .executes(
                                                                context->set(context.getSource(), FloatArgumentType.getFloat(context,"tolerance"))
                                                        )
                                        )
                                        .then(
                                                Commands.literal("DEFAULT")
                                                        .executes(
                                                                context->set(context.getSource(),20)
                                                        )
                                        )
                        )

        );
    }

    private static int get(@NotNull CommandSourceStack source){
        if(source.isPlayer()) source.sendSystemMessage(Component.translatable("command.transfur_tolerance.get").append(String.valueOf(TransfurManager.TRANSFUR_TOLERANCE))); else LOGGER.info("Transfur tolerance is "+TransfurManager.TRANSFUR_TOLERANCE);
        return Command.SINGLE_SUCCESS;
    }

    private static int set(@NotNull CommandSourceStack source , float tolerance){
        if(TransfurManager.TRANSFUR_TOLERANCE==tolerance) return Command.SINGLE_SUCCESS;
        TransfurManager.TRANSFUR_TOLERANCE=tolerance;
        if(source.isPlayer()) source.sendSystemMessage(Component.translatable("command.transfur_tolerance.set").append(String.valueOf(tolerance)));
        LOGGER.info("Transfur tolerance is set to "+tolerance);
        source.getLevel().getServer().getAllLevels().forEach(level->level.getAllEntities().forEach(entity->{
            if(TransfurDamageSource.checkTarget(entity)) TransfurEvent.RECALCULATE_PROGRESS.accept((LivingEntity) entity);
        }));
        PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),new ClientboundTransfurToleranceUpdatePacket());
        return Command.SINGLE_SUCCESS;
    }
}
