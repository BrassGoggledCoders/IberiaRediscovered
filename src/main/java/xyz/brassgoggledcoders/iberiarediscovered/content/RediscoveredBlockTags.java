package xyz.brassgoggledcoders.iberiarediscovered.content;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;

public class RediscoveredBlockTags {
    public static final TagKey<Block> HARD = BlockTags.create(IberiaRediscovered.rl("hard"));
    public static final TagKey<Block> COMPRESSING = BlockTags.create(IberiaRediscovered.rl("compressing"));
}
