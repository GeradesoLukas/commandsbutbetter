package net.geradesolukas.commandsbutbetter.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.geradesolukas.commandsbutbetter.command.MountAddCommand;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();

    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(MountAddCommand::register);
    }
}
