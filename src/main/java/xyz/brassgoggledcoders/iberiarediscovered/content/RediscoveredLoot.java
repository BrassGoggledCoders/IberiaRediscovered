package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.AgeLootConditionSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.AddItemStacksLootModifier;

public class RediscoveredLoot {

    public final static RegistryEntry<Codec<? extends IGlobalLootModifier>> LOOT_ENTRY_MODIFIER =
            IberiaRediscovered.getRegistrate()
                    .object("add_itemstacks")
                    .simple(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> AddItemStacksLootModifier.CODEC);

    public final static RegistryEntry<LootItemConditionType> AGE_CONDITION = IberiaRediscovered.getRegistrate()
            .object("age")
            .simple(Registry.LOOT_ITEM_REGISTRY, () -> new LootItemConditionType(new AgeLootConditionSerializer()));

    public static void setup() {

    }
}
