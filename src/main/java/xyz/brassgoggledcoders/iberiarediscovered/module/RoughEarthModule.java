package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraftforge.common.MinecraftForge;
import xyz.brassgoggledcoders.iberiarediscovered.event.RoughEarthEventHandler;

public class RoughEarthModule extends Module {
    public static final String NAME = "rough_earth";
    public final RoughEarthEventHandler earthEventHandler;
    public RoughEarthModule() {
        super(NAME);
        this.earthEventHandler = new RoughEarthEventHandler();
        MinecraftForge.EVENT_BUS.register(this.earthEventHandler);
    }
}
