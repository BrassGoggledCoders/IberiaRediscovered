package xyz.brassgoggledcoders.iberiarediscovered.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.capability.PlayerInfoProvider;

@Mod.EventBusSubscriber(modid = IberiaRediscovered.ID, bus = Bus.FORGE)
public class ForgeEventHandler {
    private static final ResourceLocation PLAYER_INFO = IberiaRediscovered.rl("player_info");

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> entityAttachCapabilitiesEvent) {
        if (entityAttachCapabilitiesEvent.getObject() instanceof Player) {
            PlayerInfoProvider playerInfoProvider = new PlayerInfoProvider();
            entityAttachCapabilitiesEvent.addCapability(PLAYER_INFO, playerInfoProvider);
            entityAttachCapabilitiesEvent.addListener(playerInfoProvider::invalidate);
        }
    }
}
