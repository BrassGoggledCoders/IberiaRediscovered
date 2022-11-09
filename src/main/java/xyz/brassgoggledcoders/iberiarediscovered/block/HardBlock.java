package xyz.brassgoggledcoders.iberiarediscovered.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockProperties;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockTags;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneLocation;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
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

            for (Direction hardCheckDirection : Direction.values()) {
                BlockPos hardCheckPos = pPos.relative(hardCheckDirection);
                BlockState blockState = pLevel.getBlockState(hardCheckPos);

                allHard &= blockState.is(RediscoveredBlockTags.HARD);
                allCompressing &= blockState.is(RediscoveredBlockTags.COMPRESSING);

                if (IHardBlock.BASE_TO_HARD.get().containsKey(blockState.getBlock())) {
                    boolean compress = true;
                    for (Direction compressingCheckDirection : Direction.values()) {
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
    @SuppressWarnings("deprecation")
    @ParametersAreNonnullByDefault
    public float getDestroyProgress(BlockState pState, Player pPlayer, BlockGetter pLevel, BlockPos pPos) {
        Optional<Integer> difficultyModifier = this.getDifficultModifierFor(pPlayer, pPlayer.getLevel());
        if (difficultyModifier.isPresent()) {
            float f = this.getBaseWithState(pState)
                    .getDestroySpeed(pLevel, pPos);
            if (f == -1.0F) {
                return 0.0F;
            } else {
                f *= (4 + (difficultyModifier.get() * 2));
                int i = ForgeHooks.isCorrectToolForDrops(pState, pPlayer) ? 30 : 100;
                return pPlayer.getDigSpeed(pState, pPos) / f / (float) i;
            }
        } else {
            return this.getBaseWithState(pState)
                    .getDestroyProgress(pPlayer, pLevel, pPos);
        }

    }

    @Override
    @ParametersAreNonnullByDefault
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
        int furtherExhaustion = pPlayer.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                .filter(Modules.HARD_STONE_MODULE::isActiveFor)
                .map(playerInfo -> playerInfo.getDifficultyFor(Modules.HARD_STONE_MODULE.getName(), pLevel.getDifficulty()))
                .map(Difficulty::getId)
                .orElse(0);

        if (furtherExhaustion > 0) {
            pPlayer.causeFoodExhaustion(furtherExhaustion * 0.005F);
        }
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return 0.6F * this.getBaseWithState(state)
                .getExplosionResistance(level, pos, explosion);
    }

    protected Optional<Integer> getDifficultModifierFor(Player player, LevelAccessor level) {
        return player.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                .filter(Modules.HARD_STONE_MODULE::isActiveFor)
                .map(playerInfo -> playerInfo.getDifficultyFor(Modules.HARD_STONE_MODULE.getName(), level.getDifficulty()))
                .map(Difficulty::getId);
    }

    @Override
    public Block getBaseBlock() {
        return this.baseVersion.get();
    }

    public BlockState getBaseWithState(BlockState blockState) {
        return this.getBaseBlock()
                .withPropertiesOf(blockState);
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
