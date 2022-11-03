package xyz.brassgoggledcoders.iberiarediscovered.content;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;

public class RediscoveredCapabilities {
    public static Capability<IPlayerInfo> PLAYER_INFO = CapabilityManager.get(new CapabilityToken<>() {
    });
}
