package xyz.brassgoggledcoders.iberiarediscovered.module;

import javax.annotation.Nullable;

public class Modules {
    public static final MedicalHealingModule MEDICAL_HEALING = new MedicalHealingModule();

    public static final Module[] MODULES = new Module[]{
            MEDICAL_HEALING
    };

    @Nullable
    public static Module get(String name) {
        switch (name) {
            case MedicalHealingModule.NAME:
                return MEDICAL_HEALING;
            default:
                return null;
        }
    }
}
