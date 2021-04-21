package xyz.brassgoggledcoders.iberiarediscovered.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraftforge.server.command.EnumArgument;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.PlayerChoice;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredTexts;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class PlayerChoiceCommand {
    private static final SimpleCommandExceptionType NOT_PLAYERS_CHOICE = new SimpleCommandExceptionType(
            RediscoveredTexts.NOT_PLAYER_CHOICE
    );

    public static ArgumentBuilder<CommandSource, ?> create() {
        return Commands.literal("choices")
                .then(Commands.argument("target", EntityArgument.player())
                        .then(choice(context -> {
                            try {
                                return Collections.singleton(EntityArgument.getPlayer(context, "target"));
                            } catch (CommandSyntaxException e) {
                                return Lists.newArrayList();
                            }
                        }))
                )
                .then(choice(context -> {
                    Entity entity = context.getSource().getEntity();
                    if (entity != null) {
                        return Collections.singleton(entity);
                    } else {
                        return Collections.emptyList();
                    }
                }));
    }

    public static ArgumentBuilder<CommandSource, ?> choice(Function<CommandContext<CommandSource>, Collection<Entity>> targetFinding) {
        return Commands.literal("choice")
                .then(Commands.argument("choice", EnumArgument.enumArgument(PlayerChoice.class))
                        .then(Commands.argument("module", EnumArgument.enumArgument(Modules.class))
                                .executes(context -> {
                                    Modules modules = context.getArgument("module", Modules.class);
                                    Collection<Entity> entities = targetFinding.apply(context);
                                    if (!entities.isEmpty()) {
                                        boolean justSelf = entities.stream()
                                                .allMatch(entity -> entity == context.getSource().getEntity());

                                        if (!justSelf && !context.getSource().hasPermissionLevel(2)) {
                                            throw EntityArgument.SELECTOR_NOT_ALLOWED.create();
                                        }
                                        if (justSelf) {
                                            if (!modules.get().isPlayerChoice()) {
                                                throw NOT_PLAYERS_CHOICE.create();
                                            }
                                        }
                                    }

                                    PlayerChoice choice = context.getArgument("choice", PlayerChoice.class);
                                    return entities.stream()
                                            .mapToInt(entity -> entity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                                                    .map(playerInfo -> {
                                                        playerInfo.setChoiceFor(modules.get().getName(), choice);
                                                        return 1;
                                                    })
                                                    .orElse(0)
                                            ).sum();
                                })
                        )
                );

    }
}
