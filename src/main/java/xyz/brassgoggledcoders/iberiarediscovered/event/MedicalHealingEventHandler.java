package xyz.brassgoggledcoders.iberiarediscovered.event;

import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MedicalHealingEventHandler {

    @SubscribeEvent
    public void onDamageReceived(LivingDamageEvent event) {
        if (event.getAmount() >= 2) {
            LivingEntity livingEntity = event.getEntityLiving();
            if (livingEntity.getActivePotionEffect(RediscoveredEffects.TREATED.get()) != null) {
                event.getEntityLiving().removePotionEffect(RediscoveredEffects.TREATED.get());
            }
        }
    }

    @SubscribeEvent
    public void onAwake(PlayerWakeUpEvent event) {
        PlayerEntity playerEntity = event.getPlayer();
        switch (playerEntity.getEntityWorld().getDifficulty()) {
            case HARD:
                playerEntity.heal(3);
                break;
            case NORMAL:
                playerEntity.heal(6);
                break;
            case EASY:
                playerEntity.heal(9);
                break;
        }
    }
}
