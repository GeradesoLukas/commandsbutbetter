package net.geradesolukas.commandsbutbetter.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class MountRemoveCommand {

    private static final SimpleCommandExceptionType DISMOUNT_NOPASSENGER_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.mount.remove.nopassenger"));
    private static final SimpleCommandExceptionType DISMOUNT_NOVEHICLE_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.mount.remove.novehicle"));



    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("mount").requires((source) -> {
            return source.hasPermissionLevel(2);
        }).then(CommandManager.literal("remove")
                .then(CommandManager.literal("self").then(CommandManager.argument("target", EntityArgumentType.entity()).executes((context -> {
                    return runself((ServerCommandSource) context.getSource(), EntityArgumentType.getEntity(context,"target"));
                }))))
                .then(CommandManager.literal("passenger").then(CommandManager.argument("target", EntityArgumentType.entity()).executes((context -> {
                    return runpassenger((ServerCommandSource) context.getSource(), EntityArgumentType.getEntity(context,"target"));
                }))))
            ));

    }


    public static int runself(ServerCommandSource source, Entity target) throws CommandSyntaxException {
        if (!target.hasVehicle()) {
            throw DISMOUNT_NOPASSENGER_EXCEPTION.create();
        } else {

            target.stopRiding();
            source.sendFeedback(Text.translatable("commands.mount.success.stopped.self", new Object[]{target.getDisplayName()}), false);
        }
        return 1;
    }

    public static int runpassenger(ServerCommandSource source, Entity target) throws CommandSyntaxException {
        if (!target.hasPassengers()) {
            throw DISMOUNT_NOVEHICLE_EXCEPTION.create();
        } else {

        target.removeAllPassengers();
        source.sendFeedback(Text.translatable("commands.mount.success.stopped.passenger", new Object[]{target.getDisplayName()}), false);
        }
        return 1;
    }


}
