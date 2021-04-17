package xyz.brassgoggledcoders.iberiarediscovered;

import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.capability.PlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCommands;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredEffects;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredItems;
import xyz.brassgoggledcoders.iberiarediscovered.module.MedicalHealingModule;
import com.tterrag.registrate.Registrate;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

@Mod(IberiaRediscovered.ID)
public class IberiaRediscovered {
    public static final String ID = "iberia_rediscovered";

    private static final NonNullLazy<Registrate> REGISTRATE = NonNullLazy.of(() -> Registrate.create(ID));

    public final MedicalHealingModule medicalHealingModule;

    public IberiaRediscovered() {
        this.medicalHealingModule = new MedicalHealingModule();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.addListener(this::commandSetup);

        RediscoveredItems.setup();
        RediscoveredEffects.setup();
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerInfo.class, new Capability.IStorage<IPlayerInfo>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<IPlayerInfo> capability, IPlayerInfo instance, Direction side) {
                return null;
            }

            @Override
            public void readNBT(Capability<IPlayerInfo> capability, IPlayerInfo instance, Direction side, INBT nbt) {

            }
        }, PlayerInfo::new);
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
