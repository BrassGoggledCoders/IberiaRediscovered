package xyz.brassgoggledcoders.iberiarediscovered.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;

import javax.annotation.Nullable;

public class PlayerInfoStorage implements Capability.IStorage<IPlayerInfo> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IPlayerInfo> capability, IPlayerInfo instance, Direction side) {
        if (instance instanceof PlayerInfo) {
            return ((PlayerInfo) instance).serializeNBT();
        } else {
            return null;
        }
    }

    @Override
    public void readNBT(Capability<IPlayerInfo> capability, IPlayerInfo instance, Direction side, INBT nbt) {
        if (instance instanceof PlayerInfo && nbt instanceof CompoundNBT) {
            ((PlayerInfo) instance).deserializeNBT((CompoundNBT) nbt);
        }
    }
}
