package xyz.brassgoggledcoders.iberiarediscovered.loot.condition;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredLoot;

import javax.annotation.Nonnull;

public class RaidCondition implements ILootCondition {
    private final LootContext.EntityTarget entityTarget;

    public RaidCondition(LootContext.EntityTarget entityTarget) {
        this.entityTarget = entityTarget;
    }

    @Override
    @Nonnull
    public LootConditionType func_230419_b_() {
        return RediscoveredLoot.RAID_CONDITION;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(entityTarget.getParameter());
        return entity instanceof AbstractRaiderEntity && ((AbstractRaiderEntity) entity).getRaid() != null;
    }

    public LootContext.EntityTarget getEntityTarget() {
        return this.entityTarget;
    }
}
