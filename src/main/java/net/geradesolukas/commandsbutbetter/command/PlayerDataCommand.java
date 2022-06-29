package net.geradesolukas.commandsbutbetter.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PlayerDataCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("playerdata").requires((source) -> {
            return source.hasPermissionLevel(2);
        }).then(CommandManager.argument("target", EntityArgumentType.player())
                .then(CommandManager.literal("fireTick").then(CommandManager.argument("value", IntegerArgumentType.integer()).executes((context -> {

                    return runFireTick((ServerCommandSource)context.getSource(), EntityArgumentType.getPlayer(context,"target"), IntegerArgumentType.getInteger(context,"value"));
                }))))
                .then(CommandManager.literal("NoGravity").then(CommandManager.argument("value", BoolArgumentType.bool()).executes((context -> {

                    return runNoGravity((ServerCommandSource)context.getSource(), EntityArgumentType.getPlayer(context,"target"), BoolArgumentType.getBool(context,"value"));
                }))))
                .then(CommandManager.literal("Silent").then(CommandManager.argument("value", BoolArgumentType.bool()).executes((context -> {

                    return runSilent((ServerCommandSource)context.getSource(), EntityArgumentType.getPlayer(context,"target"), BoolArgumentType.getBool(context,"value"));
                }))))
                .then(CommandManager.literal("Glowing").then(CommandManager.argument("value", BoolArgumentType.bool()).executes((context -> {

                    return runGlowing((ServerCommandSource)context.getSource(), EntityArgumentType.getPlayer(context,"target"), BoolArgumentType.getBool(context,"value"));
                }))))
                .then(CommandManager.literal("fireTick").then(CommandManager.argument("value", IntegerArgumentType.integer()).executes((context -> {

                    return runPortalCooldown((ServerCommandSource)context.getSource(), EntityArgumentType.getPlayer(context,"target"), IntegerArgumentType.getInteger(context,"value"));
                }))))
        ));

    }

    private static int runFireTick(ServerCommandSource source, PlayerEntity target, int value) throws CommandSyntaxException {
        target.setFireTicks(value);
        String type = "FireTick";
        source.sendFeedback(Text.translatable ("commands.playerdata.success", target.getDisplayName(), type, value), false);
        return 1;
    }

    private static int runNoGravity(ServerCommandSource source, PlayerEntity target, boolean value) throws CommandSyntaxException {
        target.setNoGravity(value);
        String type = "NoGravity";
        source.sendFeedback(Text.translatable ("commands.playerdata.success", target.getDisplayName(), type, value), false);
        return 1;
    }

    private static int runSilent(ServerCommandSource source, PlayerEntity target, boolean value) throws CommandSyntaxException {
        target.setSilent(value);
        String type = "Silent";
        source.sendFeedback(Text.translatable ("commands.playerdata.success", target.getDisplayName(), type, value), false);
        return 1;
    }

    private static int runGlowing(ServerCommandSource source, PlayerEntity target, boolean value) throws CommandSyntaxException {
        target.setGlowing(value);
        String type = "Glowing";
        source.sendFeedback(Text.translatable ("commands.playerdata.success", target.getDisplayName(), type, value), false);
        return 1;
    }

    private static int runPortalCooldown(ServerCommandSource source, PlayerEntity target, int value) throws CommandSyntaxException {
        //target.writeNbt(new NbtCompound(this.).putInt("PortalCooldown",value));
        String type = "PortalCooldown";
        source.sendFeedback(Text.translatable ("commands.playerdata.success", target.getDisplayName(), type, value), false);
        return 1;
    }







}
