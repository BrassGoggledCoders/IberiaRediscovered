package xyz.brassgoggledcoders.iberiarediscovered.loot.condition;

import net.minecraft.entity.Entity;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredLoot;

import javax.annotation.Nonnull;

public class TeamCondition implements ILootCondition {
    private final LootContext.EntityTarget entityTarget;
    private final String team;

    public TeamCondition(LootContext.EntityTarget entityTarget, String team) {
        this.entityTarget = entityTarget;
        this.team = team;
    }

    @Override
    @Nonnull
    public LootConditionType func_230419_b_() {
        return RediscoveredLoot.TEAM_CONDITION;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(entityTarget.getParameter());
        if (entity != null && entity.getTeam() != null) {
            return team == null || entity.getTeam().getName().equalsIgnoreCase(team);
        }
        return false;
    }

    public LootContext.EntityTarget getEntityTarget() {
        return entityTarget;
    }

    public String getTeam() {
        return team;
    }
}
