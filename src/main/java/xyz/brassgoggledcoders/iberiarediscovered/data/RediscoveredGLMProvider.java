package xyz.brassgoggledcoders.iberiarediscovered.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredItems;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredLoot;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.AgeLootCondition;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.LootEntryModifierBuilder;

public class RediscoveredGLMProvider extends GlobalLootModifierProvider {
    public RediscoveredGLMProvider(DataGenerator gen, String modid) {
        super(gen, modid);
    }

    @Override
    protected void start() {
        this.add("elixir_of_youth_entity", RediscoveredLoot.LOOT_ENTRY_MODIFIER.get(), new LootEntryModifierBuilder()
                .acceptCondition(Alternative.builder(
                        LootTableIdCondition.builder(EntityType.WITCH.getLootTable()),
                        LootTableIdCondition.builder(EntityType.GUARDIAN.getLootTable())
                ))
                .acceptCondition(AgeLootCondition.builder(LootContext.EntityTarget.KILLER_PLAYER, 10))
                .acceptCondition(RandomChanceWithLooting.builder(0.005F, 0.1F))
                .addEntry(ItemLootEntry.builder(RediscoveredItems.ELIXIR_OF_YOUTH.get()))
                .build()
        );

        this.add("elixir_of_youth_chest", RediscoveredLoot.LOOT_ENTRY_MODIFIER.get(), new LootEntryModifierBuilder()
                .acceptCondition(Alternative.builder(
                        LootTableIdCondition.builder(LootTables.CHESTS_SHIPWRECK_TREASURE),
                        LootTableIdCondition.builder(LootTables.CHESTS_DESERT_PYRAMID)
                ))
                .acceptCondition(AgeLootCondition.builder(LootContext.EntityTarget.THIS, 10))
                .acceptCondition(RandomChance.builder(0.005F))
                .addEntry(ItemLootEntry.builder(RediscoveredItems.ELIXIR_OF_YOUTH.get()))
                .build()
        );
    }


}
