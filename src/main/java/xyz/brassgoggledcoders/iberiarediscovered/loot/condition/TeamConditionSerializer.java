package xyz.brassgoggledcoders.iberiarediscovered.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class TeamConditionSerializer implements ILootSerializer<TeamCondition> {
    @Override
    @ParametersAreNonnullByDefault
    public void serialize(JsonObject jsonObject, TeamCondition teamCondition, JsonSerializationContext context) {
        jsonObject.add("entity", context.serialize(teamCondition.getEntityTarget()));
        if (teamCondition.getTeam() != null) {
            jsonObject.addProperty("team", teamCondition.getTeam());
        }
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public TeamCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
        LootContext.EntityTarget entityTarget = context.deserialize(jsonObject.get("entity"), LootContext.EntityTarget.class);
        if (entityTarget != null) {
            return new TeamCondition(
                    entityTarget,
                    jsonObject.has("team") ? jsonObject.get("team").getAsString() : null
            );
        } else {
            throw new IllegalArgumentException("Failed to find field 'entity' or was an invalid value");
        }
    }
}
