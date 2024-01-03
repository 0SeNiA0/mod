package net.zaharenko424.a_changed.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.zaharenko424.a_changed.transfurSystem.TransfurEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class UnTransfur {

    public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(
                Commands.literal("untransfur")
                        .requires(source-> source.hasPermission(Commands.LEVEL_ADMINS))
                        .executes(command->command.getSource().isPlayer()?execute(command.getSource().getPlayer()):0)
                        .then(
                                Commands.argument("target", EntityArgument.player())
                                        .executes(command-> execute(EntityArgument.getPlayer(command,"target")))));

    }

    private static final Consumer<ServerPlayer> unTransfur = TransfurEvent.unTransfur().build();

    private static int execute(@NotNull ServerPlayer player){
        unTransfur.accept(player);
        return Command.SINGLE_SUCCESS;
    }
}