package xyz.brassgoggledcoders.iberiarediscovered.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext.EntityTarget;
import net.minecraft.util.JSONUtils;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class AgeLootConditionSerializer implements ILootSerializer<AgeLootCondition> {
    @Override
    @ParametersAreNonnullByDefault
    public void serialize(JsonObject jsonObject, AgeLootCondition ageLootCondition, JsonSerializationContext context) {
        if (ageLootCondition.getMinAge() > 0) {
            jsonObject.addProperty("minAge", ageLootCondition.getMinAge());
        }
        if (ageLootCondition.getMaxAge() < Integer.MAX_VALUE) {
            jsonObject.addProperty("maxAge", ageLootCondition.getMaxAge());
        }
        jsonObject.add("entity", context.serialize(ageLootCondition.getEntityTarget()));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public AgeLootCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
        EntityTarget entityTarget = context.deserialize(jsonObject.get("entity"), EntityTarget.class);
        if (entityTarget != null) {
            return new AgeLootCondition(
                    entityTarget,
                    JSONUtils.getInt(jsonObject, "minAge", 0),
                    JSONUtils.getInt(jsonObject, "maxAge", Integer.MAX_VALUE)
            );
        } else {
            throw new IllegalArgumentException("Failed to find field 'entity' or was an invalid value");
        }
    }
}
