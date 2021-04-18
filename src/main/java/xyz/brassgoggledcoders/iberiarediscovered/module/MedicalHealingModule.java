package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import xyz.brassgoggledcoders.iberiarediscovered.event.MedicalHealingEventHandler;

public class MedicalHealingModule extends Module {
    public static final String NAME = "medical_healing";
    public final MedicalHealingEventHandler healingEventHandler;

    private ForgeConfigSpec.IntValue healthRegenPercentLost;
    private ForgeConfigSpec.IntValue timeBetweenAge;

    public MedicalHealingModule() {
        super(NAME);
        this.healingEventHandler = new MedicalHealingEventHandler(this::getStatus);
        MinecraftForge.EVENT_BUS.register(healingEventHandler);
    }

    @Override
    public void configureServer(ForgeConfigSpec.Builder builder) {
        super.configureServer(builder);
        healthRegenPercentLost = builder
                .translation("config.iberia_rediscovered.health_regen_percent_lost")
                .defineInRange("health_regen_percent_lost", 5, 0, Integer.MAX_VALUE);
        timeBetweenAge = builder
                .translation("config.iberia_rediscovered.time_between_age")
                .defineInRange("time_between_age", 20 * 60 * 60 * 5, 20, Integer.MAX_VALUE);
    }

    public int getHealthRegenPercentLost() {
        return healthRegenPercentLost.get();
    }

    public int getTimeBetweenAge() {
        return timeBetweenAge.get();
    }
}
