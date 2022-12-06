package xyz.brassgoggledcoders.iberiarediscovered.loot.condition.age;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.Serializer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class AgeLootConditionSerializer implements Serializer<AgeLootCondition> {
    @Override
    @ParametersAreNonnullByDefault
    public void serialize(JsonObject jsonObject, AgeLootCondition ageLootCondition, JsonSerializationContext context) {
        if (ageLootCondition.minAge() > 0) {
            jsonObject.addProperty("minAge", ageLootCondition.minAge());
        }
        if (ageLootCondition.maxAge() < Integer.MAX_VALUE) {
            jsonObject.addProperty("maxAge", ageLootCondition.maxAge());
        }
        jsonObject.add("entity", context.serialize(ageLootCondition.entityTarget()));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public AgeLootCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
        EntityTarget entityTarget = context.deserialize(jsonObject.get("entity"), EntityTarget.class);
        if (entityTarget != null) {
            return new AgeLootCondition(
                    entityTarget,
                    GsonHelper.getAsInt(jsonObject, "minAge", 0),
                    GsonHelper.getAsInt(jsonObject, "maxAge", Integer.MAX_VALUE)
            );
        } else {
            throw new IllegalArgumentException("Failed to find field 'entity' or was an invalid value");
        }
    }
}
