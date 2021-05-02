package xyz.brassgoggledcoders.iberiarediscovered.content;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.scoreboard.Team;

import javax.annotation.Nonnull;

public class RediscoveredPredicates {
    public static EntityPredicate matchesTeam(@Nonnull Team team) {
        return new EntityPredicate()
                .setCustomPredicate(livingEntity -> team.isSameTeam(livingEntity.getTeam()));
    }
}
