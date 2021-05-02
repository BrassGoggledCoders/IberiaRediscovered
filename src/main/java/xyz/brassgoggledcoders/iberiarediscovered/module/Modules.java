package xyz.brassgoggledcoders.iberiarediscovered.module;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum Modules {
    MEDICAL_HEALING(Modules::getHealing),
    RANDOMIZED_SPAWN(Modules::getRebirth);

    public static final RandomizedRebirthModule RANDOMIZED_SPAWN_MODULE = new RandomizedRebirthModule();
    public static final MedicalHealingModule MEDICAL_HEALING_MODULE = new MedicalHealingModule();

    private final Supplier<Module> getModule;

    Modules(Supplier<Module> getModule) {
        this.getModule = getModule;
    }

    public Module get() {
        return this.getModule.get();
    }

    @Nullable
    public static Module get(String name) {
        switch (name) {
            case MedicalHealingModule.NAME:
                return MEDICAL_HEALING_MODULE;
            default:
                return null;
        }
    }

    public static Module getHealing() {
        return MEDICAL_HEALING_MODULE;
    }

    public static Module getRebirth() {
        return RANDOMIZED_SPAWN_MODULE;
    }
}
