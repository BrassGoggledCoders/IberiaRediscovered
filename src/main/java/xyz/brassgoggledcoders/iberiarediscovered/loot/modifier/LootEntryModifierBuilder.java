package xyz.brassgoggledcoders.iberiarediscovered.loot.modifier;

import com.google.common.collect.Lists;
import net.minecraft.loot.ILootConditionConsumer;
import net.minecraft.loot.ILootFunctionConsumer;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;

import javax.annotation.Nonnull;
import java.util.List;

public class LootEntryModifierBuilder implements ILootFunctionConsumer<LootEntryModifierBuilder>,
        ILootConditionConsumer<LootEntryModifierBuilder> {
    private final List<LootEntry> entries = Lists.newArrayList();
    private final List<ILootCondition> conditions = Lists.newArrayList();
    private final List<ILootFunction> functions = Lists.newArrayList();

    @Override
    @Nonnull
    public LootEntryModifierBuilder cast() {
        return this;
    }

    public LootEntryModifierBuilder addEntry(LootEntry.Builder<?> entriesBuilder) {
        this.entries.add(entriesBuilder.build());
        return this;
    }

    @Override
    @Nonnull
    public LootEntryModifierBuilder acceptCondition(ILootCondition.IBuilder conditionBuilder) {
        this.conditions.add(conditionBuilder.build());
        return this;
    }

    @Override
    @Nonnull
    public LootEntryModifierBuilder acceptFunction(ILootFunction.IBuilder functionBuilder) {
        this.functions.add(functionBuilder.build());
        return this;
    }

    public LootEntryLootModifier build() {
        return new LootEntryLootModifier(
                this.conditions.toArray(new ILootCondition[0]),
                this.entries.toArray(new LootEntry[0]),
                this.functions.toArray(new ILootFunction[0])
        );
    }
}
