package xyz.brassgoggledcoders.iberiarediscovered.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredEffects;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class MedicalSuppliesItem extends Item {
    private final int multiplier;

    public MedicalSuppliesItem(int multiplier, Properties properties) {
        super(properties);
        this.multiplier = multiplier;
    }

    @Override
    @Nonnull
    public UseAnim getUseAnimation(@Nonnull ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 16 * multiplier;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.getHealth() < player.getMaxHealth()) {
            player.startUsingItem(hand);
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        } else {
            return super.use(level, player, hand);
        }
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entityLiving) {
        entityLiving.addEffect(new MobEffectInstance(
                RediscoveredEffects.TREATED.get(),
                2 * 60 * 20,
                2 * multiplier - 1
        ));
        itemStack.shrink(1);
        return itemStack;
    }
}
