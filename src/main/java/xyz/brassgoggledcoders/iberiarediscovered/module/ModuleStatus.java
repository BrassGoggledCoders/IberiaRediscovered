package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraft.entity.player.PlayerEntity;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;

import java.util.function.Predicate;

public enum ModuleStatus {
    ENABLED(true, playerInfo -> true),
    OPT_OUT(true, playerInfo -> true),
    OPT_IN(false, playerInfo -> false),
    DISABLED(false, playerInfo -> false);

    private final Predicate<IPlayerInfo> isEnabled;
    private final boolean defaultValue;

    ModuleStatus(boolean defaultValue, Predicate<IPlayerInfo> isEnabled) {
        this.defaultValue = defaultValue;
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled(IPlayerInfo playerInfo) {
        return this.isEnabled.test(playerInfo);
    }

    public boolean isEnabled(PlayerEntity playerEntity) {
        return playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                .map(this::isEnabled)
                .orElse(defaultValue);
    }
}
