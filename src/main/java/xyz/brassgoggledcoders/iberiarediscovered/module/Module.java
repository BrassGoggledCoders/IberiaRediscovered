package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;

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

    public boolean isActiveFor(Player playerEntity) {
        return playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                .map(this::isActiveFor)
                .orElse(false);
    }

    public boolean isActiveFor(IPlayerInfo playerInfo) {
        return this.getStatus().isEnabled(playerInfo.getChoiceFor(this.getName()));
    }

    public boolean isActive() {
        return this.getStatus().isEnabled(null);
    }

    public void configureServer(ForgeConfigSpec.Builder builder) {
        this.status = builder.defineEnum("status", ModuleStatus.ENABLED);
        this.difficulty = builder.defineEnum("difficulty", ModuleDifficulty.WORLD);
        this.playerChoice = builder.define("playerChoice", false);
    }
}
