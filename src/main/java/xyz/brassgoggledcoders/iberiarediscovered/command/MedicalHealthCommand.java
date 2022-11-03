package xyz.brassgoggledcoders.iberiarediscovered.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;

import java.util.Collection;

public class MedicalHealthCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> create() {
        return Commands.literal("healing")
                .then(age());
    }

    public static ArgumentBuilder<CommandSourceStack, ?> age() {
        return Commands.literal("age")
                .then(Commands.argument("players", EntityArgument.players())
                        .then(Commands.literal("set")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(MedicalHealthCommand::setAge)
                                )
                        )
                        .then(Commands.literal("get")
                                .executes(MedicalHealthCommand::getAge)
                        )
                );
    }

    private static int setAge(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> playerEntities = EntityArgument.getOptionalPlayers(context, "players");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        int numberAffected = 0;

        for (ServerPlayer playerEntity : playerEntities) {
            numberAffected += playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .map(playerInfo -> {
                        playerInfo.setAge(amount);
                        return 1;
                    })
                    .orElse(0);
        }

        context.getSource().sendSuccess(Component.literal(numberAffected + ""), true);

        return numberAffected;
    }

    private static int getAge(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> playerEntities = EntityArgument.getOptionalPlayers(context, "players");

        for (ServerPlayer playerEntity : playerEntities) {
            int age = playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .map(IPlayerInfo::getAge)
                    .orElse(0);

            context.getSource().sendSuccess(Component.translatable("%s : %s", playerEntity.getDisplayName(), age), true);
        }

        return playerEntities.size();
    }
}
