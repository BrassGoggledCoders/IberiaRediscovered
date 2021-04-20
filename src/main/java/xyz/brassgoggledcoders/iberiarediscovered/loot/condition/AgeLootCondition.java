package xyz.brassgoggledcoders.iberiarediscovered.loot.condition;

import net.minecraft.entity.Entity;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredLoot;

import javax.annotation.Nonnull;

public class AgeLootCondition implements ILootCondition {
    private final LootContext.EntityTarget entityTarget;
    private final int maxAge;
    private final int minAge;

    public AgeLootCondition(LootContext.EntityTarget entityTarget, int maxAge, int minAge) {
        this.entityTarget = entityTarget;
        this.maxAge = maxAge;
        this.minAge = minAge;
    }

    @Override
    @Nonnull
    public LootConditionType func_230419_b_() {
        return RediscoveredLoot.AGE_CONDITION;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(this.entityTarget.getParameter());

        if (entity != null) {
            int age = entity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .map(IPlayerInfo::getAge)
                    .orElse(0);

            return age >= minAge && age <= maxAge;
        } else {
            return false;
        }
    }

    public LootContext.EntityTarget getEntityTarget() {
        return entityTarget;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public static IBuilder builder(LootContext.EntityTarget entityTarget, int minAge) {
        return () -> new AgeLootCondition(entityTarget, Integer.MAX_VALUE, minAge);
    }
}
