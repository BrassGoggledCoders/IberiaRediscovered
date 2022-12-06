package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.age.AgeLootConditionSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.explosion.ExplosionRadiusConditionSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.add.AddItemStacksLootModifier;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.replace.ReplaceItemStacksLootModifier;

@SuppressWarnings("unused")
public class RediscoveredLoot {

    public static final RegistryEntry<Codec<? extends IGlobalLootModifier>> ADD_ITEMSTACKS =
            IberiaRediscovered.getRegistrate()
                    .object("add_itemstacks")
                    .simple(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> AddItemStacksLootModifier.CODEC);

    public static final RegistryEntry<Codec<? extends IGlobalLootModifier>> REPLACE_ITEMSTACKS =
            IberiaRediscovered.getRegistrate()
                    .object("replace_itemstacks")
                    .simple(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> ReplaceItemStacksLootModifier.CODEC);

    private static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_REGISTER = DeferredRegister.create(
            Registry.LOOT_ITEM_REGISTRY,
            IberiaRediscovered.ID
    );

    public final static RegistryObject<LootItemConditionType> AGE_CONDITION = LOOT_CONDITION_REGISTER.register(
            "age",
            () -> new LootItemConditionType(new AgeLootConditionSerializer())
    );

    public final static RegistryObject<LootItemConditionType> EXPLOSION_RADIUS_CONDITION = LOOT_CONDITION_REGISTER.register(
            "explosion_radius",
            () -> new LootItemConditionType(new ExplosionRadiusConditionSerializer())
    );

    public static final LootContextParamSet UNKNOWN_SEEDS = LootContextParamSet.builder()
            .required(LootContextParams.BLOCK_STATE)
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.TOOL)
            .optional(LootContextParams.THIS_ENTITY)
            .optional(LootContextParams.BLOCK_ENTITY)
            .build();

    public static void setup(IEventBus eventBus) {
        LOOT_CONDITION_REGISTER.register(eventBus);
    }
}
