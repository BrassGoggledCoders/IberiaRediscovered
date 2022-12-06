package xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.add;

import com.google.common.collect.Lists;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import javax.annotation.Nonnull;
import java.util.List;

public class AddItemStacksModifierBuilder {
    private final List<LootItemCondition> conditions = Lists.newArrayList();
    private final List<ItemStack> itemStacks = Lists.newArrayList();

    public AddItemStacksModifierBuilder addItemStack(ItemStack itemStack) {
        this.itemStacks.add(itemStack);
        return this;
    }

    @Nonnull
    public AddItemStacksModifierBuilder acceptCondition(LootItemCondition.Builder conditionBuilder) {
        this.conditions.add(conditionBuilder.build());
        return this;
    }

    public AddItemStacksLootModifier build() {
        return new AddItemStacksLootModifier(
                this.conditions.toArray(new LootItemCondition[0]),
                this.itemStacks
        );
    }
}
