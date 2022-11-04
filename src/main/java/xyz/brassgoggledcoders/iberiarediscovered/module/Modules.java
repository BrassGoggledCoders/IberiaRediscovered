package xyz.brassgoggledcoders.iberiarediscovered.module;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum Modules {
    MEDICAL_HEALING(Modules::getHealing),
    HARD_STONE(Modules::getHardStone);

    private final Supplier<Module> getModule;

    Modules(Supplier<Module> getModule) {
        this.getModule = getModule;
    }

    public Module get() {
        return this.getModule.get();
    }

    @Nullable
    public static Module get(String name) {
        return switch (name) {
            case MedicalHealingModule.NAME -> MEDICAL_HEALING_MODULE;
            case HardStoneModule.NAME -> HARD_STONE_MODULE;
            default -> null;
        };
    }

    public static Module getHealing() {
        return MEDICAL_HEALING_MODULE;
    }

    public static Module getHardStone() {
        return HARD_STONE_MODULE;
    }

    public static final MedicalHealingModule MEDICAL_HEALING_MODULE = new MedicalHealingModule();
    public static final HardStoneModule HARD_STONE_MODULE = new HardStoneModule();
}
