package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraftforge.common.ForgeConfigSpec;

public class Module {
    private ForgeConfigSpec.EnumValue<ModuleStatus> status;

    private final String name;

    public Module(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ModuleStatus getStatus() {
        return status.get();
    }

    public void configureServer(ForgeConfigSpec.Builder builder) {
        this.status = builder.defineEnum("status", ModuleStatus.ENABLED);
    }
}
