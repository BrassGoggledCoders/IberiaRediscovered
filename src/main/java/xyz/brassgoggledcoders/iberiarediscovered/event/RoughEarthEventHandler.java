package xyz.brassgoggledcoders.iberiarediscovered.event;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.BlockEvent.BlockToolModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockTags;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredLootTables;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;
import xyz.brassgoggledcoders.iberiarediscovered.saveddata.ClearedBlockLevelData;

public class RoughEarthEventHandler {


    @SubscribeEvent
    public void handleHoeing(BlockToolModificationEvent event) {
        if (event.getToolAction() == ToolActions.HOE_TILL && event.getLevel() instanceof ServerLevel serverLevel &&
            event.getFinalState().is(RediscoveredBlockTags.ROUGH_EARTH)) {
            boolean isActive;
            if (event.getPlayer() != null) {
                isActive = event.getPlayer()
                        .getCapability(RediscoveredCapabilities.PLAYER_INFO)
                        .filter(Modules.getRoughEarth()::isActiveFor)
                        .isPresent();
            } else {
                isActive = Modules.getRoughEarth().isActive();
            }

            ClearedBlockLevelData clearedBlockLevelData = ClearedBlockLevelData.getFrom(serverLevel);

            if (isActive && !clearedBlockLevelData.isCleared(event.getPos())) {
                if (event.getLevel().getServer() != null) {
                    LootTable lootTable = event.getLevel().getServer()
                            .getLootTables()
                            .get(RediscoveredLootTables.ROUGH_EARTH_HOEING);

                    LootContext lootContext = new LootContext.Builder(serverLevel)
                            .withParameter(LootContextParams.BLOCK_STATE, event.getFinalState())
                            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(event.getPos()))
                            .withParameter(LootContextParams.TOOL, event.getHeldItemStack())
                            .withOptionalParameter(LootContextParams.BLOCK_ENTITY, serverLevel.getBlockEntity(event.getPos()))
                            .withOptionalParameter(LootContextParams.THIS_ENTITY, event.getPlayer())
                            .create(LootContextParamSets.BLOCK);

                    NonNullList<ItemStack> itemStacks = NonNullList.create();
                    itemStacks.addAll(lootTable.getRandomItems(lootContext));
                    Containers.dropContents(serverLevel, event.getPos(), itemStacks);

                    clearedBlockLevelData.tryClear(event.getPos(), event.getHeldItemStack(), serverLevel.getRandom());
                }
            }
        }
    }

    @SubscribeEvent
    public void handleBlockBreak(BlockEvent.BreakEvent breakEvent) {
        if (breakEvent.getLevel() instanceof ServerLevel serverLevel && breakEvent.getState().is(RediscoveredBlockTags.ROUGH_EARTH)) {
            ClearedBlockLevelData.getFrom(serverLevel)
                    .remove(breakEvent.getPos());
        }
    }
}
