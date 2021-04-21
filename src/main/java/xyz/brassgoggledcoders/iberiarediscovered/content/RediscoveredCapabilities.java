package xyz.brassgoggledcoders.iberiarediscovered.content;

import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class RediscoveredCapabilities {
    @CapabilityInject(IPlayerInfo.class)
    public static Capability<IPlayerInfo> PLAYER_INFO;
}
