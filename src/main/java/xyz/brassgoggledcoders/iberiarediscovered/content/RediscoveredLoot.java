package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.loot.LootConditionType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.AgeLootConditionSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.RaidConditionSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.loot.condition.TeamConditionSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.LootEntryLootModifier;
import xyz.brassgoggledcoders.iberiarediscovered.loot.modifier.LootEntryLootModifierSerializer;

public class RediscoveredLoot {

    public final static LootConditionType AGE_CONDITION = new LootConditionType(new AgeLootConditionSerializer());
    public final static LootConditionType TEAM_CONDITION = new LootConditionType(new TeamConditionSerializer());
    public final static LootConditionType RAID_CONDITION = new LootConditionType(new RaidConditionSerializer());

    public final static RegistryEntry<GlobalLootModifierSerializer<LootEntryLootModifier>> LOOT_ENTRY_MODIFIER =
            IberiaRediscovered.getRegistrate()
                    .object("loot_entry")
                    .simple(GlobalLootModifierSerializer.class, LootEntryLootModifierSerializer::new);

    public static void setup() {
        IberiaRediscovered.getRegistrate()
                .<GlobalLootModifierSerializer<?>>addRegisterCallback(GlobalLootModifierSerializer.class,
                        () -> {
                            Registry.register(
                                    Registry.LOOT_CONDITION_TYPE,
                                    IberiaRediscovered.rl("age"),
                                    AGE_CONDITION
                            );
                            Registry.register(
                                    Registry.LOOT_CONDITION_TYPE,
                                    IberiaRediscovered.rl("team"),
                                    TEAM_CONDITION
                            );
                            Registry.register(
                                    Registry.LOOT_CONDITION_TYPE,
                                    IberiaRediscovered.rl("raid"),
                                    RAID_CONDITION
                            );
                        });
    }
}
