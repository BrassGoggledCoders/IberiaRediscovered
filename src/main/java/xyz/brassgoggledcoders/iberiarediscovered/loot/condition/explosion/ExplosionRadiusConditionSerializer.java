package xyz.brassgoggledcoders.iberiarediscovered.loot.condition.explosion;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class ExplosionRadiusConditionSerializer implements Serializer<ExplosionRadiusCondition> {

    @Override
    @ParametersAreNonnullByDefault
    public void serialize(JsonObject pJson, ExplosionRadiusCondition pValue, JsonSerializationContext pSerializationContext) {
        if (pValue.minRadius() > 0) {
            pJson.addProperty("minRadius", pValue.minRadius());
        }
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public ExplosionRadiusCondition deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
        return new ExplosionRadiusCondition(GsonHelper.getAsFloat(pJson, "minRadius", 0F));
    }
}
