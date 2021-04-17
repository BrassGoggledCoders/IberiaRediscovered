package xyz.brassgoggledcoders.iberiarediscovered.item;

import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
    public UseAction getUseAction(@Nonnull ItemStack itemStack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 16 * multiplier;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (player.getHealth() < player.getMaxHealth()) {
            player.setActiveHand(hand);
            return ActionResult.resultConsume(player.getHeldItem(hand));
        } else {
            return super.onItemRightClick(world, player, hand);
        }
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity entityLiving) {
        entityLiving.addPotionEffect(new EffectInstance(
                RediscoveredEffects.TREATED.get(),
                2 * 60 * 20,
                2 * multiplier - 1
        ));
        itemStack.shrink(1);
        return itemStack;
    }
}
