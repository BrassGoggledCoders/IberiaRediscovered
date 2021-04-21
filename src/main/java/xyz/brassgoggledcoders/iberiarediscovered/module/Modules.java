package xyz.brassgoggledcoders.iberiarediscovered.module;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum Modules {
    MEDICAL_HEALING(Modules::getHealing);

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

    public static final MedicalHealingModule MEDICAL_HEALING_MODULE = new MedicalHealingModule();
}
