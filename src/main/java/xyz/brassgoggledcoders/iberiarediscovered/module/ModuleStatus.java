package xyz.brassgoggledcoders.iberiarediscovered.module;

import org.jetbrains.annotations.Nullable;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.PlayerChoice;

import java.util.function.Predicate;

public enum ModuleStatus {
    ENABLED(playerChoice -> true, true),
    OPT_OUT(PlayerChoice::isOptOut, true),
    OPT_IN(PlayerChoice::isOptIn, false),
    DISABLED(playerChoice -> false, false);

    private final Predicate<PlayerChoice> isEnabled;
    private final boolean defaultEnabled;

    ModuleStatus(Predicate<PlayerChoice> isEnabled, boolean defaultEnabled) {
        this.isEnabled = isEnabled;
        this.defaultEnabled = defaultEnabled;
    }

    public boolean isEnabled(@Nullable PlayerChoice playerChoice) {
        return playerChoice != null ? this.isEnabled.test(playerChoice) : this.defaultEnabled;
    }
}
