package xyz.brassgoggledcoders.iberiarediscovered.saveddata;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;

import java.util.HashMap;
import java.util.Map;

public class ClearedBlockLevelData extends SavedData {
    private static final String NAME = IberiaRediscovered.rl("cleared_blocks").toString().replace(":", "_");
    private static final ClearedData CLEARED = new ClearedData(true, 0F, 0F);
    private final Map<BlockPos, ClearedData> clearedDataMap;

    public ClearedBlockLevelData() {
        this.clearedDataMap = new HashMap<>();
    }

    public ClearedBlockLevelData(CompoundTag tag) {
        this();
        ListTag clearedBlockData = tag.getList("ClearedDataMap", Tag.TAG_COMPOUND);
        for (int i = 0; i < clearedBlockData.size(); i++) {
            CompoundTag data = clearedBlockData.getCompound(i);
            this.clearedDataMap.put(
                    NbtUtils.readBlockPos(data.getCompound("BlockPos")),
                    ClearedData.fromTag(data.getCompound("ClearedData"))
            );
        }
    }

    public void remove(BlockPos blockPos) {
        clearedDataMap.remove(blockPos);
    }

    public void add(BlockPos blockPos, float baseChance) {
        this.add(blockPos, baseChance, 0.05F);
    }

    public void add(BlockPos blockPos, float baseChance, float tierChange) {
        this.clearedDataMap.put(blockPos, new ClearedData(false, baseChance, tierChange));
    }

    public void addCleared(BlockPos blockPos) {
        this.clearedDataMap.put(blockPos, new ClearedData(true, 0F, 0F));
    }

    public boolean isCleared(BlockPos blockPos) {
        return this.clearedDataMap.getOrDefault(blockPos, CLEARED).cleared();
    }

    @SuppressWarnings("deprecation")
    public void tryClear(BlockPos blockPos, ItemStack itemStack, RandomSource random) {
        ClearedData clearedData = this.clearedDataMap.computeIfAbsent(blockPos, pos -> new ClearedData(false, 0.25F, 0.05F));

        float tierChange = 0F;
        if (itemStack.getItem() instanceof TieredItem tieredItem) {
            tierChange = tieredItem.getTier().getLevel() * clearedData.tierChange();
        }
        float totalChance = clearedData.baseClearChance() + tierChange;
        if (random.nextFloat() < totalChance) {
            this.clearedDataMap.put(blockPos, new ClearedData(true, clearedData.baseClearChance, clearedData.tierChange));
        }
    }

    @Override
    @NotNull
    public CompoundTag save(@NotNull CompoundTag pCompoundTag) {
        ListTag clearedDataNBT = new ListTag();
        for (Map.Entry<BlockPos, ClearedData> entry : this.clearedDataMap.entrySet()) {
            CompoundTag clearedData = new CompoundTag();
            clearedData.put("BlockPos", NbtUtils.writeBlockPos(entry.getKey()));
            clearedData.put("ClearedData", entry.getValue().toTag());
            clearedDataNBT.add(clearedData);
        }
        pCompoundTag.put("ClearedDataMap", clearedDataNBT);
        return pCompoundTag;
    }

    @NotNull
    public static ClearedBlockLevelData getFrom(ServerLevel serverLevel) {
        return serverLevel.getDataStorage()
                .computeIfAbsent(
                        ClearedBlockLevelData::new,
                        ClearedBlockLevelData::new,
                        NAME
                );
    }

    private record ClearedData(
            boolean cleared,
            float baseClearChance,
            float tierChange
    ) {

        public CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("Cleared", this.cleared());
            tag.putFloat("BaseClearChance", this.baseClearChance());
            tag.putFloat("TierChange", this.tierChange());
            return tag;
        }

        public static ClearedData fromTag(CompoundTag tag) {
            return new ClearedData(
                    tag.getBoolean("Cleared"),
                    tag.getFloat("BaseClearChance"),
                    tag.getFloat("TierChange")
            );
        }
    }
}
