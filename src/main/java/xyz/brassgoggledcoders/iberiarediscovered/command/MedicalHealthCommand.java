package xyz.brassgoggledcoders.iberiarediscovered.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;

import java.util.Collection;

public class MedicalHealthCommand {
    public static ArgumentBuilder<CommandSource, ?> create() {
        return Commands.literal("healing")
                .then(age());
    }

    public static ArgumentBuilder<CommandSource, ?> age() {
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

    private static int setAge(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> playerEntities = EntityArgument.getPlayersAllowingNone(context, "players");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        int numberAffected = 0;

        for (ServerPlayerEntity playerEntity : playerEntities) {
            numberAffected += playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .map(playerInfo -> {
                        playerInfo.setAge(amount);
                        return 1;
                    })
                    .orElse(0);
        }

        context.getSource().sendFeedback(new StringTextComponent(numberAffected + ""), true);

        return numberAffected;
    }

    private static int getAge(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> playerEntities = EntityArgument.getPlayersAllowingNone(context, "players");

        for (ServerPlayerEntity playerEntity : playerEntities) {
            int age = playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .map(IPlayerInfo::getAge)
                    .orElse(0);

            context.getSource().sendFeedback(new TranslationTextComponent("%s : %s",
                    playerEntity.getDisplayName(), age), true);
        }

        return playerEntities.size();
    }
}
