package xyz.brassgoggledcoders.iberiarediscovered.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockProperties;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockTags;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

public class HardBlock extends Block implements IHardBlock {
    public static final BooleanProperty ACTIVE = RediscoveredBlockProperties.ACTIVE;

    private final HardStoneLocation location;
    private final Supplier<Block> baseVersion;

    public HardBlock(Properties pProperties, HardStoneLocation location, Supplier<Block> baseVersion) {
        super(pProperties.randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, true));
        this.location = location;
        this.baseVersion = Lazy.of(baseVersion);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ACTIVE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(ACTIVE);
    }

    @Override
    @NotNull
    public MutableComponent getName() {
        return Component.translatable(this.getDescriptionId(), this.getBaseBlock().getName());
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    @ParametersAreNonnullByDefault
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (!pState.getValue(ACTIVE)) {
            return pState.setValue(ACTIVE, true);
        } else {
            return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    @ParametersAreNonnullByDefault
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(ACTIVE)) {
            boolean allHard = true;
            boolean allCompressing = true;

            for (Direction hardCheckDirection: Direction.values()) {
                BlockPos hardCheckPos = pPos.relative(hardCheckDirection);
                BlockState blockState = pLevel.getBlockState(hardCheckPos);

                allHard &= blockState.is(RediscoveredBlockTags.HARD);
                allCompressing &= blockState.is(RediscoveredBlockTags.COMPRESSING);

                if (IHardBlock.BASE_TO_HARD.get().containsKey(blockState.getBlock())) {
                    boolean compress = true;
                    for (Direction compressingCheckDirection: Direction.values()) {
                        if (compressingCheckDirection.getOpposite() != hardCheckDirection) {
                            compress &= pLevel.getBlockState(hardCheckPos.relative(compressingCheckDirection)).is(RediscoveredBlockTags.COMPRESSING);
                        }
                    }
                    if (compress) {
                        Block hardBlock = IHardBlock.BASE_TO_HARD.get().get(blockState.getBlock());
                        pLevel.setBlock(hardCheckPos, hardBlock.withPropertiesOf(blockState), Block.UPDATE_ALL);
                    }
                }
            }


            if (allHard) {
                pLevel.setBlock(pPos, pState.setValue(ACTIVE, false), Block.UPDATE_ALL);
            } else if (!allCompressing) {
                pLevel.setBlock(pPos, this.getBaseBlock().withPropertiesOf(pState), Block.UPDATE_ALL);
            }
        }
    }

    @Override
    public Block getBaseBlock() {
        return this.baseVersion.get();
    }

    @Override
    public Block getSelf() {
        return this;
    }

    @Override
    public HardStoneLocation getLocation() {
        return location;
    }
}
