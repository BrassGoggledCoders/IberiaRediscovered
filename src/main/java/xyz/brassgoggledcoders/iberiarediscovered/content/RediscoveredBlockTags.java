package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;

public class RediscoveredBlockTags {
    public static final ITag.INamedTag<Block> REBIRTH_VALID = BlockTags.createOptional(IberiaRediscovered.rl("rebirth_valid"));


    public static void generateTags(RegistrateTagsProvider<Block> tagsProvider) {
        tagsProvider.getOrCreateBuilder(REBIRTH_VALID);
    }
}
