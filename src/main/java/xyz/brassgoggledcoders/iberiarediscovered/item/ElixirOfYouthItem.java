package xyz.brassgoggledcoders.iberiarediscovered.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ElixirOfYouthItem extends Item {
    public ElixirOfYouthItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public UseAnim getUseAnimation(@Nonnull ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 32;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entityLiving) {
        entityLiving.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                .ifPresent(IPlayerInfo::resetAge);
        itemStack.shrink(1);
        return itemStack;
    }
}
