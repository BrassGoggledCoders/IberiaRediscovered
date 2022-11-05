package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.block.IHardBlock;

public class RediscoveredData {
    public static void blockTags(RegistrateTagsProvider<Block> tagsProvider) {
        TagsProvider.TagAppender<Block> mossReplaceableAppender = tagsProvider.tag(BlockTags.MOSS_REPLACEABLE);

        for (RegistryEntry<Block> block : IberiaRediscovered.getRegistrate().getAll(ForgeRegistries.Keys.BLOCKS)) {
            block.filter(IHardBlock.class::isInstance)
                    .ifPresent(mossReplaceableAppender::remove);
        }

        tagsProvider.tag(RediscoveredBlockTags.COMPRESSING)
                .addTag(RediscoveredBlockTags.HARD)
                .addTag(Tags.Blocks.STONE)
                .addTag(Tags.Blocks.ORES)
                .addTag(BlockTags.DIRT)
                .add(Blocks.BEDROCK);
    }
}
