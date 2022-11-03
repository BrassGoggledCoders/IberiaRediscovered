package xyz.brassgoggledcoders.iberiarediscovered.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredAttributes;

import javax.annotation.Nonnull;

public class TreatedEffect extends MobEffect {
    public TreatedEffect() {
        super(MobEffectCategory.BENEFICIAL, 16777215);
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLiving, int amplifier) {
        AttributeInstance attribute = entityLiving.getAttribute(RediscoveredAttributes.TREATMENT_MODIFIER.get());
        if (attribute != null) {
            entityLiving.heal(((float) (amplifier + 1)) * (float) attribute.getValue());
        } else {
            entityLiving.heal(amplifier + 1);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration <= 1;
    }
}
