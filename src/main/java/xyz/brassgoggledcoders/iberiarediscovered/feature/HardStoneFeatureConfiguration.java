package xyz.brassgoggledcoders.iberiarediscovered.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.brassgoggledcoders.iberiarediscovered.block.HardBlock;
import xyz.brassgoggledcoders.iberiarediscovered.block.IHardBlock;

import java.util.*;
import java.util.stream.Collectors;

public record HardStoneFeatureConfiguration(
        Map<Block, Block> replacements,
        boolean checkAir
) implements FeatureConfiguration {
    public static final Codec<HardStoneFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.pair(
                    ForgeRegistries.BLOCKS.getCodec().fieldOf("original").codec(),
                    ForgeRegistries.BLOCKS.getCodec().fieldOf("replacement").codec()
            )).fieldOf("replacements").forGetter(HardStoneFeatureConfiguration::getReplacementsForCodec),
            Codec.BOOL.optionalFieldOf("checkAir", true).forGetter(HardStoneFeatureConfiguration::checkAir)
    ).apply(instance, HardStoneFeatureConfiguration::fromCodec));

    public List<Pair<Block, Block>> getReplacementsForCodec() {
        return this.replacements()
                .entrySet()
                .stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static HardStoneFeatureConfiguration fromCodec(List<Pair<Block, Block>> replacements, boolean checkAir) {
        return new HardStoneFeatureConfiguration(
                replacements.stream()
                        .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)),
                checkAir
        );
    }

    public static HardStoneFeatureConfiguration fromHardStone(Collection<IHardBlock> hardBlocks) {
        return new HardStoneFeatureConfiguration(
                hardBlocks.stream()
                        .map(block -> Pair.of(block.getBaseBlock(), block.getSelf()))
                        .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)),
                true
        );
    }
}
