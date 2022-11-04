package xyz.brassgoggledcoders.iberiarediscovered.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockProperties;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneLocation;

import java.util.function.Supplier;

public class HardRotatedPillarBlock extends RotatedPillarBlock implements IHardBlock {
    public static final BooleanProperty ACTIVE = RediscoveredBlockProperties.ACTIVE;

    private final HardStoneLocation location;
    private final Supplier<Block> baseVersion;

    public HardRotatedPillarBlock(Properties pProperties, HardStoneLocation location, Supplier<Block> baseVersion) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, true));
        this.location = location;
        this.baseVersion = baseVersion;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ACTIVE);
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
        return this.location;
    }
}
