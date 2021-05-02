package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldPos {
    private final RegistryKey<World> world;
    private final BlockPos blockPos;

    public WorldPos(RegistryKey<World> world, BlockPos blockPos) {
        this.world = world;
        this.blockPos = blockPos;
    }

    public RegistryKey<World> getWorld() {
        return world;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
