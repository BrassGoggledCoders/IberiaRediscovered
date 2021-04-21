package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

public enum PlayerChoice {
    OPT_IN(true, false),
    OPT_OUT(false, true),
    DEFAULT(false, false);

    private final boolean optIn;
    private final boolean optOut;

    PlayerChoice(boolean optIn, boolean optOut) {
        this.optIn = optIn;
        this.optOut = optOut;
    }

    public boolean isOptIn() {
        return optIn;
    }

    public boolean isOptOut() {
        return optOut;
    }
}
