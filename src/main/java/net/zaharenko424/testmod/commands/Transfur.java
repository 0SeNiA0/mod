package net.zaharenko424.testmod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zaharenko424.testmod.TestMod;
import net.zaharenko424.testmod.TransfurManager;
import net.zaharenko424.testmod.registry.TransfurRegistry;
import org.jetbrains.annotations.NotNull;

public class Transfur {

    private static final SuggestionProvider<CommandSourceStack> suggestions= SuggestionProviders.register(
            new ResourceLocation(TestMod.MODID,"transfur_types"),
            (context,builder)->SharedSuggestionProvider.suggestResource(TransfurRegistry.TRANSFUR_REGISTRY.keySet().stream(),builder));

    public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(
                Commands.literal("transfur")
                        .requires(source->source.hasPermission(Commands.LEVEL_ADMINS))
                        .then(
                                Commands.argument("transfurType", ResourceLocationArgument.id())
                                        .suggests(suggestions)
                                        .executes(
                                                context->context.getSource().isPlayer()? execute(ResourceLocationArgument.getId(context,"transfurType"),context.getSource().getPlayer()) :0
                                        )
                                        .then(
                                                Commands.argument("target", EntityArgument.player())
                                                        .executes(context->execute(ResourceLocationArgument.getId(context,"transfurType"),EntityArgument.getPlayer(context,"target"))
                                                        )
                                        )
                        )
        );
    }

    private static int execute(@NotNull ResourceLocation transfurType, @NotNull ServerPlayer player){
        TransfurManager.transfur(player,transfurType);
        return Command.SINGLE_SUCCESS;
    }
}