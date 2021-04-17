package xyz.brassgoggledcoders.iberiarediscovered.content;


import xyz.brassgoggledcoders.iberiarediscovered.command.MedicalHealthCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class RediscoveredCommands {
    public static void setup(Commands commands) {
        commands.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("iberia")
                .then(MedicalHealthCommand.create())
        );
    }
}
