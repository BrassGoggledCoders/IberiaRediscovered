package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

public class RediscoveredData {
    public static void blockTags(RegistrateTagsProvider<Block> tagsProvider) {
        tagsProvider.tag(RediscoveredBlockTags.COMPRESSING)
                .addTag(RediscoveredBlockTags.HARD)
                .addTag(Tags.Blocks.STONE)
                .addTag(Tags.Blocks.ORES)
                .addTag(BlockTags.DIRT)
                .add(Blocks.BEDROCK);
    }
}
