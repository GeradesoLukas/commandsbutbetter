package net.geradesolukas.commandsbutbetter.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.geradesolukas.commandsbutbetter.command.MountAddCommand;
import net.geradesolukas.commandsbutbetter.command.MountRemoveCommand;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();

    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(MountAddCommand::register);
        CommandRegistrationCallback.EVENT.register(MountRemoveCommand::register);
    }
}
