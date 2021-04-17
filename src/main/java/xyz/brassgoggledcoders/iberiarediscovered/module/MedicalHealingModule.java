package xyz.brassgoggledcoders.iberiarediscovered.module;

import xyz.brassgoggledcoders.iberiarediscovered.event.MedicalHealingEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class MedicalHealingModule {
    public final MedicalHealingEventHandler healingEventHandler;

    public MedicalHealingModule() {
        this.healingEventHandler = new MedicalHealingEventHandler();
        MinecraftForge.EVENT_BUS.register(healingEventHandler);
    }
}
