package xyz.brassgoggledcoders.iberiarediscovered.capability;

import net.minecraft.core.Direction;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerInfoProvider implements ICapabilitySerializable<CompoundTag> {
    private final PlayerInfo playerInfo;
    private final LazyOptional<IPlayerInfo> lazyOptional;

    public PlayerInfoProvider() {
        this.playerInfo = new PlayerInfo();
        this.lazyOptional = LazyOptional.of(() -> this.playerInfo);
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.playerInfo.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.playerInfo.deserializeNBT(nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return RediscoveredCapabilities.PLAYER_INFO == cap ? this.lazyOptional.cast() : LazyOptional.empty();
    }

    public void invalidate() {
        this.lazyOptional.invalidate();
    }
}
