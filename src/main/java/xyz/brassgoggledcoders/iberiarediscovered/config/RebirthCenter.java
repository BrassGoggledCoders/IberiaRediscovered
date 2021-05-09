package xyz.brassgoggledcoders.iberiarediscovered.config;

import net.minecraft.data.BlockStateVariantBuilder.ITriFunction;
import net.minecraft.util.math.GlobalPos;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;

public enum RebirthCenter {
    ORIGIN((originPos, deathPos, lastRebirthPos) -> originPos),
    DEATH((originPos, deathPos, lastRebirthPos) -> deathPos),
    LAST_REBIRTH((originPos, deathPos, lastRebirthPos) -> lastRebirthPos);

    private final ITriFunction<GlobalPos, GlobalPos, GlobalPos, GlobalPos> selector;

    RebirthCenter(ITriFunction<GlobalPos, GlobalPos, GlobalPos, GlobalPos> selector) {
        this.selector = selector;
    }

    public GlobalPos getPosition(GlobalPos originPos, GlobalPos deathPos, GlobalPos lastRebirth) {
        return this.selector.apply(originPos, deathPos, lastRebirth);
    }

    public GlobalPos getPosition(GlobalPos originPos, IPlayerInfo playerInfo) {
        return this.getPosition(originPos, playerInfo.getLastDeath(), playerInfo.getLastRebirth());
    }
}
