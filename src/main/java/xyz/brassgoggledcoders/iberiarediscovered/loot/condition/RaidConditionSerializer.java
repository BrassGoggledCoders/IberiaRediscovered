package xyz.brassgoggledcoders.iberiarediscovered.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class RaidConditionSerializer implements ILootSerializer<RaidCondition> {
    @Override
    @ParametersAreNonnullByDefault
    public void serialize(JsonObject jsonObject, RaidCondition raidCondition, JsonSerializationContext context) {
        jsonObject.add("entity", context.serialize(raidCondition.getEntityTarget()));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public RaidCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
        LootContext.EntityTarget entityTarget = context.deserialize(jsonObject.get("entity"), LootContext.EntityTarget.class);
        if (entityTarget != null) {
            return new RaidCondition(entityTarget);
        } else {
            throw new IllegalArgumentException("Failed to find field 'entity' or was an invalid value");
        }
    }
}
