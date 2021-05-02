package xyz.brassgoggledcoders.iberiarediscovered.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredAttributes;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(
            method = "shouldHeal",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void overrideShouldHeal(CallbackInfoReturnable<Boolean> callbackInfo) {
        PlayerEntity playerEntity = ((PlayerEntity) (Object) this);
        if (playerEntity.getHealth() > 0 && playerEntity.getHealth() < playerEntity.getMaxHealth()) {
            int percent = playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .map(playerInfo -> playerInfo.getMaxHealthRegenPercent(
                            playerEntity.getAttributeValue(RediscoveredAttributes.MAX_HEALTH_REGEN_MODIFIER.get())
                    ))
                    .orElse(0);
            if (percent > 0 && percent < 100) {
                float maxHealthReg = playerEntity.getMaxHealth() * (percent / 100F);
                if (playerEntity.getHealth() > maxHealthReg) {
                    callbackInfo.setReturnValue(false);
                }
            }
        }


    }
}
