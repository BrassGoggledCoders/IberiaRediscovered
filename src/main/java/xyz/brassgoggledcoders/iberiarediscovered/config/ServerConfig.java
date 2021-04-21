package xyz.brassgoggledcoders.iberiarediscovered.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import xyz.brassgoggledcoders.iberiarediscovered.module.Module;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

public class ServerConfig {
    public static final Pair<ServerConfig, ForgeConfigSpec> CONFIG_PAIR = new ForgeConfigSpec.Builder()
            .configure(ServerConfig::new);

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        for (Modules moduleEnum : Modules.values()) {
            Module module = moduleEnum.get();
            builder.push(module.getName());
            module.configureServer(builder);
            builder.pop();
        }
    }
}
