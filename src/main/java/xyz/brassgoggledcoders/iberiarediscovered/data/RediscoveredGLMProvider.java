package xyz.brassgoggledcoders.iberiarediscovered.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredItems;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredLoot;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.age.AgeLootCondition;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.explosion.ExplosionRadiusCondition;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.add.AddItemStacksModifierBuilder;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.replace.ReplaceItemStacksLootModifier;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.replace.ReplaceItemStacksModifierBuilder;

public class RediscoveredGLMProvider extends GlobalLootModifierProvider {
    public RediscoveredGLMProvider(DataGenerator gen, String modId) {
        super(gen, modId);
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

        this.add("cleared_dirt", new ReplaceItemStacksModifierBuilder()
                .acceptCondition(LootTableIdCondition.builder(Blocks.DIRT.getLootTable()))
                .acceptCondition(ExplosionRadiusCondition.builder())
                .replaceItemStack(Items.DIRT, RediscoveredItems.CLEARED_DIRT.asStack())
                .build()
        );

        this.add("cleared_grass", new ReplaceItemStacksModifierBuilder()
                .acceptCondition(LootTableIdCondition.builder(Blocks.GLASS.getLootTable()))
                .acceptCondition(ExplosionRadiusCondition.builder())
                .replaceItemStack(Items.GLASS, RediscoveredItems.CLEARED_GRASS.asStack())
                .build()
        );
    }


}
