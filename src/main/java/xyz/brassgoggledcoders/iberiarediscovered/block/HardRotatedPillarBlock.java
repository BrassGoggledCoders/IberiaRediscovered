package xyz.brassgoggledcoders.iberiarediscovered.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

public class HardRotatedPillarBlock extends HardBlock {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public HardRotatedPillarBlock(Properties pProperties, HardStoneLocation location, Supplier<Block> baseVersion) {
        super(pProperties, location, baseVersion);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return RotatedPillarBlock.rotatePillar(pState, pRot);
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AXIS);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(AXIS, pContext.getClickedFace().getAxis());
    }

}
