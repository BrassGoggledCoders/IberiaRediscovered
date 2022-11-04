package xyz.brassgoggledcoders.iberiarediscovered.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredAttributes;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;

@Mixin(FoodData.class)
public class FoodDataMixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;heal(F)V"
            ),
            cancellable = true
    )
    private void injectIberiaAgeCheck(Player pPlayer, CallbackInfo ci) {
        if (pPlayer.getHealth() > 0 && pPlayer.isHurt()) {
            int percent = pPlayer.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .map(playerInfo -> playerInfo.getMaxHealthRegenPercent(
                            pPlayer.getAttributeValue(RediscoveredAttributes.MAX_HEALTH_REGEN_MODIFIER.get())
                    ))
                    .orElse(0);
            if (percent > 0 && percent < 100) {
                float maxHealthReg = pPlayer.getMaxHealth() * (percent / 100F);
                if (pPlayer.getHealth() > maxHealthReg) {
                    ci.cancel();
                }
            }
        }
    }
}
