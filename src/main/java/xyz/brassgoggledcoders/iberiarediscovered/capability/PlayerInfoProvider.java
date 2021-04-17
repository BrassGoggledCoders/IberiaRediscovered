package xyz.brassgoggledcoders.iberiarediscovered.capability;

import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerInfoProvider implements ICapabilitySerializable<CompoundNBT> {
    private final PlayerInfo playerInfo;
    private final LazyOptional<IPlayerInfo> lazyOptional;

    public PlayerInfoProvider() {
        this.playerInfo = new PlayerInfo();
        this.lazyOptional = LazyOptional.of(() -> this.playerInfo);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return this.playerInfo.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
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
