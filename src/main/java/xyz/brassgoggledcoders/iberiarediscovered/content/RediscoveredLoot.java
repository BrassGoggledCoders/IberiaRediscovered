package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.AgeLootConditionSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.AddItemStacksLootModifier;

public class RediscoveredLoot {

    public static final RegistryEntry<Codec<? extends IGlobalLootModifier>> LOOT_ENTRY_MODIFIER =
            IberiaRediscovered.getRegistrate()
                    .object("add_itemstacks")
                    .simple(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> AddItemStacksLootModifier.CODEC);

    private static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_REGISTER = DeferredRegister.create(
            Registry.LOOT_ITEM_REGISTRY,
            IberiaRediscovered.ID
    );

    public final static RegistryObject<LootItemConditionType> AGE_CONDITION = LOOT_CONDITION_REGISTER.register(
            "age",
            () -> new LootItemConditionType(new AgeLootConditionSerializer())
    );

    public static void setup(IEventBus eventBus) {
        LOOT_CONDITION_REGISTER.register(eventBus);
    }
}
