package xyz.brassgoggledcoders.iberiarediscovered.block;

import net.minecraft.world.level.block.Block;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneLocation;

public interface IHardBlock {
    Block getBaseBlock();

    Block getSelf();

    HardStoneLocation getLocation();
}
