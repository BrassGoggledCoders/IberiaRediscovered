package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;

public class RediscoveredAttributes {

    public static final RegistryEntry<Attribute> AGE_PROGRESSION_SPEED_MODIFIER = IberiaRediscovered.getRegistrate()
            .object("age_progress_speed")
            .simple(Attribute.class, () -> new RangedAttribute(
                    "iberia_rediscovered.age_progress_speed",
                    1.0D,
                    0.0D,
                    Double.MAX_VALUE
            ).setShouldWatch(true));

    public static final RegistryEntry<Attribute> MAX_HEALTH_REGEN_MODIFIER = IberiaRediscovered.getRegistrate()
            .object("max_health_regen")
            .simple(Attribute.class, () -> new RangedAttribute(
                    "iberia_rediscovered.max_health_regen",
                    1.0D,
                    0.0D,
                    Double.MAX_VALUE
            ).setShouldWatch(true));

    public static final RegistryEntry<Attribute> TREATMENT_MODIFIER = IberiaRediscovered.getRegistrate()
            .object("treatment")
            .simple(Attribute.class, () -> new RangedAttribute(
                    "iberia_rediscovered.treatment",
                    1.0D,
                    0.0D,
                    Double.MAX_VALUE
            ).setShouldWatch(true));

    public static void setup(IEventBus modBus) {
        modBus.addListener(RediscoveredAttributes::registerPlayerAttributes);
    }

    private static void registerPlayerAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, MAX_HEALTH_REGEN_MODIFIER.get());
        event.add(EntityType.PLAYER, AGE_PROGRESSION_SPEED_MODIFIER.get());
        event.add(EntityType.PLAYER, TREATMENT_MODIFIER.get());
    }
}
