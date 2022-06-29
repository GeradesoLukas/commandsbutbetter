package net.geradesolukas.commandsbutbetter.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.geradesolukas.commandsbutbetter.command.MotionCommand;
import net.geradesolukas.commandsbutbetter.command.MountAddCommand;
import net.geradesolukas.commandsbutbetter.command.MountRemoveCommand;
import net.geradesolukas.commandsbutbetter.command.PlayerDataCommand;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();

    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(MountAddCommand::register);
        CommandRegistrationCallback.EVENT.register(MountRemoveCommand::register);
        CommandRegistrationCallback.EVENT.register(MotionCommand::register);
        CommandRegistrationCallback.EVENT.register(PlayerDataCommand::register);
    }
}
