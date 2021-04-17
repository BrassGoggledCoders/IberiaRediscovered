package xyz.brassgoggledcoders.iberiarediscovered.module;

import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;

import java.util.function.Predicate;

public enum ModuleStatus {
    ENABLED(playerInfo -> true),
    OPT_OUT(playerInfo -> true),
    OPT_IN(playerInfo -> false),
    DISABLED(playerInfo -> false);

    private final Predicate<IPlayerInfo> isEnabled;

    ModuleStatus(Predicate<IPlayerInfo> isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled(IPlayerInfo playerInfo) {
        return this.isEnabled.test(playerInfo);
    }
}
