package xyz.brassgoggledcoders.iberiarediscovered.loot.modifier;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootSerializers;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;

public class LootEntryLootModifierSerializer extends GlobalLootModifierSerializer<LootEntryLootModifier> {
    private final Gson GSON = LootSerializers.func_237388_c_()
            .create();

    @Override
    public LootEntryLootModifier read(ResourceLocation location, JsonObject jsonObject, ILootCondition[] lootConditions) {
        ILootFunction[] lootFunctions;
        if (jsonObject.has("functions")) {
            lootFunctions = GSON.fromJson(jsonObject.get("functions"), ILootFunction[].class);
        } else {
            lootFunctions = new ILootFunction[0];
        }
        return new LootEntryLootModifier(
                GSON.fromJson(JSONUtils.getJsonArray(jsonObject, "conditions"), ILootCondition[].class),
                GSON.fromJson(JSONUtils.getJsonArray(jsonObject, "entries"), LootEntry[].class),
                lootFunctions
        );
    }

    @Override
    public JsonObject write(LootEntryLootModifier instance) {
        JsonObject object = makeConditions(instance.getConditions());
        object.add("entries", GSON.toJsonTree(instance.getLootEntries()));
        if (instance.getFunctions().length > 0) {
            object.add("functions", GSON.toJsonTree(instance.getFunctions()));
        }
        return object;
    }
}
