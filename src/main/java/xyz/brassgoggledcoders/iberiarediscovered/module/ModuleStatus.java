package xyz.brassgoggledcoders.iberiarediscovered.module;

import xyz.brassgoggledcoders.iberiarediscovered.api.capability.PlayerChoice;

import java.util.function.Predicate;

public enum ModuleStatus {
    ENABLED(playerChoice -> true),
    OPT_OUT(PlayerChoice::isOptOut),
    OPT_IN(PlayerChoice::isOptIn),
    DISABLED(playerInfo -> false);

    private final Predicate<PlayerChoice> isEnabled;

    ModuleStatus(Predicate<PlayerChoice> isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled(PlayerChoice playerChoice) {
        return this.isEnabled.test(playerChoice);
    }
}
