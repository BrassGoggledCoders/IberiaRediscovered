package xyz.brassgoggledcoders.iberiarediscovered.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.module.ModuleStatus;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

import java.util.Map;
import java.util.function.Function;

public class HardStoneFeature extends Feature<HardStoneFeatureConfiguration> {
    public HardStoneFeature() {
        super(HardStoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<HardStoneFeatureConfiguration> pContext) {
        if (Modules.HARD_STONE_MODULE.getStatus() != ModuleStatus.DISABLED) {
            Map<Block, Block> replacements = pContext.config().replacements();
            WorldGenLevel level = pContext.level();
            int placed = 0;
            try (BulkSectionAccess bulkSectionAccess = new BulkSectionAccess(level)) {
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                BlockPos origin = pContext.origin();
                int originX = origin.getX();
                int originY = origin.getY();
                int originZ = origin.getZ();
                for (int x = originX; x < originX + 16; x++) {
                    for (int z = originZ; z < originZ + 16; z++) {
                        for (int y = originY; y < level.getHeight(); y++) {
                            mutableBlockPos.set(x, y, z);
                            if (level.ensureCanWrite(mutableBlockPos)) {
                                LevelChunkSection chunkSection = bulkSectionAccess.getSection(mutableBlockPos);
                                if (chunkSection != null) {
                                    int sectionX = SectionPos.sectionRelative(x);
                                    int sectionY = SectionPos.sectionRelative(y);
                                    int sectionZ = SectionPos.sectionRelative(z);

                                    BlockState currentState = chunkSection.getBlockState(sectionX, sectionY, sectionZ);
                                    Block replacementBlock = replacements.get(currentState.getBlock());
                                    if (replacementBlock != null) {
                                        if (shouldPlace(bulkSectionAccess::getBlockState, pContext.config(), mutableBlockPos)) {
                                            BlockState replacementBlockState = replacementBlock.withPropertiesOf(currentState);
                                            chunkSection.setBlockState(sectionX, sectionY, sectionZ, replacementBlockState, false);
                                            placed++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return placed > 0;
        } else {
            return false;
        }
    }

    private boolean shouldPlace(Function<BlockPos, BlockState> pAdjacentStateAccessor, HardStoneFeatureConfiguration configuration, BlockPos.MutableBlockPos blockPos) {
        return !configuration.checkAir() || !isAdjacentToOpenBlock(pAdjacentStateAccessor, blockPos);
    }

    public static boolean isAdjacentToOpenBlock(Function<BlockPos, BlockState> pAdjacentStateAccessor, BlockPos pPos) {
        return checkNeighbors(pAdjacentStateAccessor, pPos, BlockBehaviour.BlockStateBase::isAir);
    }
}

