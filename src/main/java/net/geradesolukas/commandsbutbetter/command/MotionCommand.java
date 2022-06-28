package net.geradesolukas.commandsbutbetter.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.DefaultPosArgument;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Collections;
import java.util.Set;

public class MotionCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("motion").requires((source) -> {
            return source.hasPermissionLevel(2);
        })
                .then(CommandManager.argument("target", EntityArgumentType.entity())
                .then(CommandManager.argument("motion", Vec3ArgumentType.vec3()).executes((context -> {
                    return run((ServerCommandSource)context.getSource(), EntityArgumentType.getEntity(context,"target"), ((ServerCommandSource)context.getSource()).getWorld(), Vec3ArgumentType.getVec3(context,"motion"), DefaultPosArgument.zero());
                }))))
                );

    }

    private static int run(ServerCommandSource source, Entity target, ServerWorld world, Vec3d motion, DefaultPosArgument zero) throws CommandSyntaxException {
        double motionX = motion.getX();
        double motionY = motion.getY();
        double motionZ = motion.getZ();
        target.setVelocity(motionX, motionY, motionZ);
        String output = "This is the Amount " + motion + " And " + target.getDisplayName();

        source.sendFeedback(Text.literal(output),false);

        return 1;
    }




}
