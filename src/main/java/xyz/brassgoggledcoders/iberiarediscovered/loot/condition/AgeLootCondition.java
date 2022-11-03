package xyz.brassgoggledcoders.iberiarediscovered.loot.condition;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredLoot;

import javax.annotation.Nonnull;

public record AgeLootCondition(
        LootContext.EntityTarget entityTarget,
        int maxAge,
        int minAge
) implements LootItemCondition {

    @Override
    @Nonnull
    public LootItemConditionType getType() {
        return RediscoveredLoot.AGE_CONDITION.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(this.entityTarget.getParam());

        if (entity != null) {
            int age = entity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .map(IPlayerInfo::getAge)
                    .orElse(0);

            return age >= minAge && age <= maxAge;
        } else {
            return false;
        }
    }

    public static Builder builder(LootContext.EntityTarget entityTarget, int minAge) {
        return () -> new AgeLootCondition(entityTarget, Integer.MAX_VALUE, minAge);
    }
}
