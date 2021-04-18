package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraftforge.common.ForgeConfigSpec;

public class Module {
    private ForgeConfigSpec.EnumValue<ModuleStatus> status;
    private ForgeConfigSpec.EnumValue<ModuleDifficulty> difficulty;
    private ForgeConfigSpec.BooleanValue playerChoice;

    private final String name;

    public Module(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ModuleStatus getStatus() {
        return status.get();
    }

    public ModuleDifficulty getDifficulty() {
        return difficulty.get();
    }

    public boolean isPlayerChoice() {
        return playerChoice.get();
    }

    public void configureServer(ForgeConfigSpec.Builder builder) {
        this.status = builder.defineEnum("status", ModuleStatus.ENABLED);
        this.difficulty = builder.defineEnum("difficulty", ModuleDifficulty.WORLD);
        this.playerChoice = builder.define("playerChoice", false);
    }
}
