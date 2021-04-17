package xyz.brassgoggledcoders.iberiarediscovered;

import com.google.common.collect.Lists;
import com.tterrag.registrate.Registrate;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.capability.PlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.capability.PlayerInfoStorage;
import xyz.brassgoggledcoders.iberiarediscovered.config.ServerConfig;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredAttributes;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCommands;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredEffects;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredItems;
import xyz.brassgoggledcoders.iberiarediscovered.module.MedicalHealingModule;
import xyz.brassgoggledcoders.iberiarediscovered.module.Module;

import java.util.List;

@Mod(IberiaRediscovered.ID)
public class IberiaRediscovered {
    public static final String ID = "iberia_rediscovered";

    private static final NonNullLazy<Registrate> REGISTRATE = NonNullLazy.of(() -> Registrate.create(ID));

    public IberiaRediscovered() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.CONFIG_PAIR.getRight());

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.addListener(this::commandSetup);

        RediscoveredAttributes.setup(modBus);
        RediscoveredItems.setup();
        RediscoveredEffects.setup();
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerInfo.class, new PlayerInfoStorage(), PlayerInfo::new);
    }

    public void commandSetup(FMLServerAboutToStartEvent event) {
        RediscoveredCommands.setup(event.getServer().getCommandManager());
    }

    public static Registrate getRegistrate() {
        return REGISTRATE.get();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID, path);
    }
}
