package xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.replace;

import com.google.common.collect.Lists;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.add.AddItemStacksLootModifier;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplaceItemStacksModifierBuilder {
    private final List<LootItemCondition> conditions = Lists.newArrayList();
    private final Map<Item,ItemStack> itemStacks = new HashMap<>();

    public ReplaceItemStacksModifierBuilder replaceItemStack(Item original, ItemStack itemStack) {
        this.itemStacks.put(original, itemStack);
        return this;
    }

    @Nonnull
    public ReplaceItemStacksModifierBuilder acceptCondition(LootItemCondition.Builder conditionBuilder) {
        this.conditions.add(conditionBuilder.build());
        return this;
    }

    public ReplaceItemStacksLootModifier build() {
        return new ReplaceItemStacksLootModifier(
                this.conditions.toArray(new LootItemCondition[0]),
                this.itemStacks
        );
    }
}
