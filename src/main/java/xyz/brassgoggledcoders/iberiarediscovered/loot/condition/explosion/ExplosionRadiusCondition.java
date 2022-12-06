package xyz.brassgoggledcoders.iberiarediscovered.loot.condition.explosion;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredLoot;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.age.AgeLootCondition;

public record ExplosionRadiusCondition(
        float minRadius
) implements LootItemCondition {

    @Override
    @NotNull
    public LootItemConditionType getType() {
        return RediscoveredLoot.EXPLOSION_RADIUS_CONDITION.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Float radius = lootContext.getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
        return radius != null && radius > minRadius;
    }

    public static Builder builder() {
        return builder(0F);
    }

    public static Builder builder(float minRadius) {
        return () -> new ExplosionRadiusCondition(minRadius);
    }
}
