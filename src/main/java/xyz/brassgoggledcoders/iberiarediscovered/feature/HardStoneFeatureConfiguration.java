package xyz.brassgoggledcoders.iberiarediscovered.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record HardStoneFeatureConfiguration(
        Map<BlockState, BlockState> replacements
) implements FeatureConfiguration {
    public static final Codec<HardStoneFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Codec.pair(
                    BlockState.CODEC.fieldOf("original").codec(),
                    BlockState.CODEC.fieldOf("replacement").codec()
            )).fieldOf("replacements").forGetter(HardStoneFeatureConfiguration::getReplacementsForCodec)
    ).apply(instance, HardStoneFeatureConfiguration::fromCodec));

    public List<Pair<BlockState, BlockState>> getReplacementsForCodec() {
        return this.replacements()
                .entrySet()
                .stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static HardStoneFeatureConfiguration fromCodec(List<Pair<BlockState, BlockState>> replacements) {
        return new HardStoneFeatureConfiguration(
                replacements.stream()
                        .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
        );
    }
}
