package xyz.brassgoggledcoders.iberiarediscovered.loot.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class AddItemStacksLootModifier extends LootModifier {
    public static Codec<AddItemStacksLootModifier> CODEC = RecordCodecBuilder.create(instance -> LootModifier.codecStart(instance)
            .and(Codec.list(ItemStack.CODEC).fieldOf("itemStacks").forGetter(AddItemStacksLootModifier::getItemStacks))
            .apply(instance, AddItemStacksLootModifier::new)
    );

    private final List<ItemStack> itemStacks;

    public AddItemStacksLootModifier(LootItemCondition[] conditions, List<ItemStack> itemStacks) {
        super(conditions);
        this.itemStacks = itemStacks;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        this.generate(generatedLoot::add);
        return generatedLoot;
    }

    private void generate(Consumer<ItemStack> stackConsumer) {
        for (ItemStack itemStack: this.getItemStacks()) {
            stackConsumer.accept(itemStack);
        }
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
