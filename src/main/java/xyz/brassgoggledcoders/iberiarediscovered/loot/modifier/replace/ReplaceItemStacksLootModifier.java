package xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.replace;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Map;

public class ReplaceItemStacksLootModifier extends LootModifier {
    public static Codec<ReplaceItemStacksLootModifier> CODEC = RecordCodecBuilder.create(instance -> LootModifier.codecStart(instance)
            .and(Codec.unboundedMap(ForgeRegistries.ITEMS.getCodec(), ItemStack.CODEC).fieldOf("itemStacks").forGetter(ReplaceItemStacksLootModifier::getItemStacks))
            .apply(instance, ReplaceItemStacksLootModifier::new)
    );

    private final Map<Item, ItemStack> itemStacks;

    public ReplaceItemStacksLootModifier(LootItemCondition[] conditions, Map<Item, ItemStack> itemStacks) {
        super(conditions);
        this.itemStacks = itemStacks;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (int i = 0; i < generatedLoot.size(); i++) {
            ItemStack existing = generatedLoot.get(i);
            ItemStack swapStack = this.getItemStacks().get(existing.getItem());
            if (swapStack != null) {
                CompoundTag swapTag = null;
                if (existing.getTag() != null) {
                    swapTag = existing.getTag().copy();
                    if (swapStack.getTag() != null) {
                        swapTag = swapTag.merge(swapStack.getTag());
                    }
                } else if (swapStack.getTag() != null) {
                    swapTag = swapStack.getTag().copy();
                }
                generatedLoot.set(i, new ItemStack(
                        swapStack.getItem(),
                        existing.getCount(),
                        swapTag
                ));
            }
        }
        return generatedLoot;
    }

    public Map<Item, ItemStack> getItemStacks() {
        return itemStacks;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
