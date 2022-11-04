package xyz.brassgoggledcoders.iberiarediscovered;

import com.tterrag.registrate.Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.config.ServerConfig;
import xyz.brassgoggledcoders.iberiarediscovered.content.*;
import xyz.brassgoggledcoders.iberiarediscovered.data.RediscoveredGLMProvider;

import javax.annotation.Nonnull;

@Mod(IberiaRediscovered.ID)
public class IberiaRediscovered {
    public static final String ID = "iberia_rediscovered";

    private static final NonNullLazy<Registrate> REGISTRATE = NonNullLazy.of(() -> Registrate.create(ID)
            .creativeModeTab(() -> new CreativeModeTab(ID) {
                @Override
                @Nonnull
                public ItemStack makeIcon() {
                    return RediscoveredItems.ELIXIR_OF_YOUTH.asStack();
                }
            }, "Iberia Rediscovered")
    );

    public IberiaRediscovered() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.CONFIG_PAIR.getRight());

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::capabilityRegister);
        modBus.addListener(this::onData);

        MinecraftForge.EVENT_BUS.addListener(this::commandSetup);

        RediscoveredAttributes.setup(modBus);
        RediscoveredItems.setup();
        RediscoveredEffects.setup();
        RediscoveredLoot.setup(modBus);
        RediscoveredRecipes.setup();
        RediscoveredTexts.setup();
    }

    public void capabilityRegister(RegisterCapabilitiesEvent event) {
        event.register(IPlayerInfo.class);
    }

    public void onData(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), new RediscoveredGLMProvider(event.getGenerator(), ID));
    }

    public void commandSetup(RegisterCommandsEvent event) {
        RediscoveredCommands.setup(event.getDispatcher());
    }

    public static Registrate getRegistrate() {
        return REGISTRATE.get();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID, path);
    }
}
