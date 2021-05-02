package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraftforge.common.MinecraftForge;
import xyz.brassgoggledcoders.iberiarediscovered.event.RandomizedRebirthEventHandler;

public class RandomizedRebirthModule extends Module {
    public static final String NAME = "randomized_rebirth";

    private final RandomizedRebirthEventHandler eventHandler;

    public RandomizedRebirthModule() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(eventHandler = new RandomizedRebirthEventHandler());
    }
}
