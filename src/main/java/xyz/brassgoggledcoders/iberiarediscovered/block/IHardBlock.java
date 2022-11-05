package xyz.brassgoggledcoders.iberiarediscovered.block;

import net.minecraft.Util;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface IHardBlock {
    Lazy<Map<Block, Block>> BASE_TO_HARD = Lazy.of(() -> Util.make(new HashMap<>(), map -> {
        for (Block block: ForgeRegistries.BLOCKS.getValues()) {
            if (block instanceof IHardBlock hardBlock) {
                map.put(hardBlock.getBaseBlock(), block);
            }
        }
    }));

    Block getBaseBlock();

    Block getSelf();

    HardStoneLocation getLocation();
}
