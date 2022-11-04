package xyz.brassgoggledcoders.iberiarediscovered.feature;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.module.ModuleStatus;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

import java.util.Map;
import java.util.stream.Stream;

public class HardStoneFeature extends Feature<HardStoneFeatureConfiguration> {
    public HardStoneFeature() {
        super(HardStoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<HardStoneFeatureConfiguration> pContext) {
        if (Modules.HARD_STONE_MODULE.getStatus() != ModuleStatus.DISABLED) {
            Map<BlockState, BlockState> replacements = pContext.config().replacements();
            WorldGenLevel level = pContext.level();
            BlockPos.betweenClosedStream(new AABB(pContext.origin(), pContext.origin().offset(16, 200, 16)))
                            .flatMap(blockPos -> {
                                BlockState blockState = level.getBlockState(blockPos);
                                BlockState replacement = replacements.get(blockState);
                                if (replacement != null) {
                                    return Stream.of(Pair.of(blockPos, replacement));
                                } else {
                                    return Stream.empty();
                                }
                            })
                    .forEach(pair -> level.setBlock(pair.key(), pair.value(), Block.UPDATE_NONE));
            return true;
        } else {
            return false;
        }
    }
}
