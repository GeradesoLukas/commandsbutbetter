package net.geradesolukas.commandsbutbetter.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

import java.util.List;

public class MountAddCommand {
    private static final SimpleCommandExceptionType MOUNT_SELF_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.mount.self"));
    private static final DynamicCommandExceptionType MOUNT_HASRIDER_EXCEPTION =new DynamicCommandExceptionType((entityname) -> {
        return Text.translatable("commands.mount.hasrider", new Object[]{entityname});
    });
    private static final DynamicCommandExceptionType MOUNT_HASVEHICLE_EXCEPTION =new DynamicCommandExceptionType((entityname) -> {
        return Text.translatable("commands.mount.hasvehicle", new Object[]{entityname});
    });
    private static final SimpleCommandExceptionType MOUNT_CANTSWAP_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.mount.cantswap"));
    private static final SimpleCommandExceptionType MOUNT_HASVEHICLEANDRIDER_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.mount.hasriderandvehicle"));
    private static final SimpleCommandExceptionType MOUNT_ALREADY_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.mount.already"));



    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("mount").requires((source) -> {
            return source.hasPermissionLevel(2);
        }).then(CommandManager.literal("add")
            .then(CommandManager.argument("vehicle", EntityArgumentType.entity()).executes((context -> {
            return run((ServerCommandSource) context.getSource(), EntityArgumentType.getEntity(context,"vehicle"), ((ServerCommandSource)context.getSource()).getEntityOrThrow());
        })).then(CommandManager.argument("passenger", EntityArgumentType.entity()).executes((context -> {
            return run((ServerCommandSource) context.getSource(), EntityArgumentType.getEntity(context,"vehicle"), EntityArgumentType.getEntity(context, "passenger"));
        }))))));

    }


    public static int run(ServerCommandSource source, Entity vehicle, Entity passenger) throws CommandSyntaxException {
        if(vehicle == passenger || passenger.hasPassenger(vehicle) || vehicle.hasPassenger(passenger) || vehicle.hasPassengers() || passenger.hasVehicle())  {
            if (vehicle == passenger) {
                throw MOUNT_SELF_EXCEPTION.create();
            } else if (passenger.hasPassenger(vehicle)) {
                throw MOUNT_CANTSWAP_EXCEPTION.create();
            } else if (vehicle.hasPassenger(passenger)) {
                throw MOUNT_ALREADY_EXCEPTION.create();
            } else if (vehicle.hasPassengers() && !passenger.hasVehicle()) {
                throw MOUNT_HASRIDER_EXCEPTION.create(vehicle.getDisplayName());
            } else if (passenger.hasVehicle() && !vehicle.hasPassengers()) {
                throw MOUNT_HASVEHICLE_EXCEPTION.create(passenger.getDisplayName());
            } else if (passenger.hasVehicle() && vehicle.hasPassengers()) {
                throw MOUNT_HASVEHICLEANDRIDER_EXCEPTION.create();
            }
        } else {
            passenger.startRiding(vehicle);
            source.sendFeedback(Text.translatable ("commands.mount.success.started", new  Object[]{passenger.getDisplayName(), vehicle.getDisplayName()}), false);

        }
        return 1;

    }


}
