package xyz.brassgoggledcoders.iberiarediscovered.content;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import xyz.brassgoggledcoders.iberiarediscovered.command.MedicalHealthCommand;
import xyz.brassgoggledcoders.iberiarediscovered.command.PlayerChoiceCommand;

public class RediscoveredCommands {
    public static void setup(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("iberia")
                .then(MedicalHealthCommand.create())
                .then(PlayerChoiceCommand.create())
        );
    }
}
