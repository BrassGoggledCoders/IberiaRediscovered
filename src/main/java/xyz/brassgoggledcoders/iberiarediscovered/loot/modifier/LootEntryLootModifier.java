package xyz.brassgoggledcoders.iberiarediscovered.loot.modifier;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootGenerator;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraftforge.common.loot.LootModifier;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class LootEntryLootModifier extends LootModifier {
    private final LootEntry[] lootEntries;
    private final ILootFunction[] functions;
    private final BiFunction<ItemStack, LootContext, ItemStack> combinedFunctions;

    public LootEntryLootModifier(ILootCondition[] conditions, LootEntry[] lootEntries, ILootFunction[] functions) {
        super(conditions);
        this.lootEntries = lootEntries;
        this.functions = functions;
        this.combinedFunctions = LootFunctionManager.combine(functions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        this.generate(generatedLoot::add, context);
        return generatedLoot;
    }

    private void generate(Consumer<ItemStack> stackConsumer, LootContext lootContext) {
        Consumer<ItemStack> consumer = ILootFunction.func_215858_a(this.combinedFunctions, stackConsumer, lootContext);
        this.generateRoll(consumer, lootContext);
    }

    private void generateRoll(Consumer<ItemStack> stackConsumer, LootContext lootContext) {
        Random random = lootContext.getRandom();
        List<ILootGenerator> list = Lists.newArrayList();
        MutableInt mutableInt = new MutableInt();

        for (LootEntry lootEntry : this.lootEntries) {
            lootEntry.expand(lootContext, (p_216097_3_) -> {
                int k = p_216097_3_.getEffectiveWeight(lootContext.getLuck());
                if (k > 0) {
                    list.add(p_216097_3_);
                    mutableInt.add(k);
                }

            });
        }

        int i = list.size();
        if (mutableInt.intValue() != 0 && i != 0) {
            if (i == 1) {
                list.get(0).func_216188_a(stackConsumer, lootContext);
            } else {
                int j = random.nextInt(mutableInt.intValue());

                for (ILootGenerator ilootgenerator : list) {
                    j -= ilootgenerator.getEffectiveWeight(lootContext.getLuck());
                    if (j < 0) {
                        ilootgenerator.func_216188_a(stackConsumer, lootContext);
                        return;
                    }
                }

            }
        }
    }

    public ILootFunction[] getFunctions() {
        return functions;
    }

    public LootEntry[] getLootEntries() {
        return lootEntries;
    }

    public ILootCondition[] getConditions() {
        return conditions;
    }
}
