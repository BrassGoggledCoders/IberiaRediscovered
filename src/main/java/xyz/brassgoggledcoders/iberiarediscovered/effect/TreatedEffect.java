package xyz.brassgoggledcoders.iberiarediscovered.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredAttributes;

import javax.annotation.Nonnull;

public class TreatedEffect extends Effect {
    public TreatedEffect() {
        super(EffectType.BENEFICIAL, 16777215);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLiving, int amplifier) {
        ModifiableAttributeInstance attribute = entityLiving.getAttribute(RediscoveredAttributes.TREATMENT_MODIFIER.get());
        if (attribute != null) {
            entityLiving.heal(((float) (amplifier + 1)) * (float) attribute.getValue());
        } else {
            entityLiving.heal(amplifier + 1);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration <= 1;
    }
}
