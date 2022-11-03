package xyz.brassgoggledcoders.iberiarediscovered.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredItems;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.AgeLootCondition;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.AddItemStacksModifierBuilder;

public class RediscoveredGLMProvider extends GlobalLootModifierProvider {
    public RediscoveredGLMProvider(DataGenerator gen, String modid) {
        super(gen, modid);
    }

    @Override
    protected void start() {
        this.add("elixir_of_youth_entity", new AddItemStacksModifierBuilder()
                .acceptCondition(AlternativeLootItemCondition.alternative(
                        LootTableIdCondition.builder(EntityType.WITCH.getDefaultLootTable()),
                        LootTableIdCondition.builder(EntityType.GUARDIAN.getDefaultLootTable())
                ))
                .acceptCondition(AgeLootCondition.builder(LootContext.EntityTarget.KILLER_PLAYER, 10))
                .acceptCondition(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.005F, 0.1F))
                .addItemStack(new ItemStack(RediscoveredItems.ELIXIR_OF_YOUTH.get()))
                .build()
        );

        this.add("elixir_of_youth_chest", new AddItemStacksModifierBuilder()
                .acceptCondition(AlternativeLootItemCondition.alternative(
                        LootTableIdCondition.builder(BuiltInLootTables.SHIPWRECK_TREASURE),
                        LootTableIdCondition.builder(BuiltInLootTables.DESERT_PYRAMID)
                ))
                .acceptCondition(AgeLootCondition.builder(LootContext.EntityTarget.THIS, 10))
                .acceptCondition(LootItemRandomChanceCondition.randomChance(0.005F))
                .addItemStack(new ItemStack(RediscoveredItems.ELIXIR_OF_YOUTH.get()))
                .build()
        );
    }


}
