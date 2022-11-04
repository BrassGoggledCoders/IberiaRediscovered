package xyz.brassgoggledcoders.iberiarediscovered.block;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockProperties;
import xyz.brassgoggledcoders.iberiarediscovered.feature.HardStoneLocation;

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
