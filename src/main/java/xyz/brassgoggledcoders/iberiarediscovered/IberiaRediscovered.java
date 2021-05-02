package xyz.brassgoggledcoders.iberiarediscovered;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.capability.PlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.capability.PlayerInfoStorage;
import xyz.brassgoggledcoders.iberiarediscovered.config.ServerConfig;
import xyz.brassgoggledcoders.iberiarediscovered.content.*;
import xyz.brassgoggledcoders.iberiarediscovered.data.RediscoveredGLMProvider;

import javax.annotation.Nonnull;

@Mod(IberiaRediscovered.ID)
public class IberiaRediscovered {
    public static final String ID = "iberia_rediscovered";

    private static final NonNullLazy<Registrate> REGISTRATE = NonNullLazy.of(() -> Registrate.create(ID)
            .addDataGenerator(ProviderType.BLOCK_TAGS, RediscoveredBlockTags::generateTags)
            .itemGroup(() -> new ItemGroup(ID) {
                @Override
                @Nonnull
                public ItemStack createIcon() {
                    return RediscoveredItems.ELIXIR_OF_YOUTH.asStack();
                }
            }, "Iberia Rediscovered")
    );

    public IberiaRediscovered() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.CONFIG_PAIR.getRight());

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::onData);

        MinecraftForge.EVENT_BUS.addListener(this::commandSetup);

        RediscoveredAttributes.setup(modBus);
        RediscoveredItems.setup();
        RediscoveredEffects.setup();
        RediscoveredLoot.setup();
        RediscoveredRecipes.setup();
        RediscoveredTexts.setup();
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerInfo.class, new PlayerInfoStorage(), PlayerInfo::new);
    }

    public void onData(GatherDataEvent event) {
        event.getGenerator().addProvider(new RediscoveredGLMProvider(event.getGenerator(), ID));
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
