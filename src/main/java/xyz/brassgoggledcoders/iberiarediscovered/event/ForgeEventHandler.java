package xyz.brassgoggledcoders.iberiarediscovered.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.capability.PlayerInfoProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = IberiaRediscovered.ID, bus = Bus.FORGE)
public class ForgeEventHandler {
    private static final ResourceLocation PLAYER_INFO = IberiaRediscovered.rl("player_info");

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> entityAttachCapabilitiesEvent) {
        if (entityAttachCapabilitiesEvent.getObject() instanceof PlayerEntity) {
            PlayerInfoProvider playerInfoProvider = new PlayerInfoProvider();
            entityAttachCapabilitiesEvent.addCapability(PLAYER_INFO, playerInfoProvider);
            entityAttachCapabilitiesEvent.addListener(playerInfoProvider::invalidate);
        }
    }
}
