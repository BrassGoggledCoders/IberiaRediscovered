package xyz.brassgoggledcoders.iberiarediscovered.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.saveddata.ClearedBlockLevelData;

public class ClearedBlockItem extends Item {
    private final Item blockItem;

    public ClearedBlockItem(Item blockItem, Properties pProperties) {
        super(pProperties);
        this.blockItem = blockItem;
    }

    @Override
    @NotNull
    public InteractionResult useOn(@NotNull UseOnContext pContext) {
        InteractionResult result = InteractionResult.FAIL;
        if (blockItem instanceof BlockItem actualBlockItem) {
            result = actualBlockItem.place(new BlockPlaceContext(pContext));
        }
        if (result.consumesAction()) {
            if (pContext.getLevel() instanceof ServerLevel serverLevel) {
                ClearedBlockLevelData.getFrom(serverLevel)
                        .addCleared(pContext.getClickedPos());
            }
        }
        return result;
    }

    public Block getUnclearedBlock() {
        return blockItem instanceof BlockItem actualBlockItem ? actualBlockItem.getBlock() : Blocks.AIR;
    }
}
