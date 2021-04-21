package xyz.brassgoggledcoders.iberiarediscovered.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
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
    public UseAction getUseAction(@Nonnull ItemStack itemStack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 32;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        player.setActiveHand(hand);
        return ActionResult.resultConsume(player.getHeldItem(hand));
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity entityLiving) {
        entityLiving.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                .ifPresent(IPlayerInfo::resetAge);
        itemStack.shrink(1);
        return itemStack;
    }
}
