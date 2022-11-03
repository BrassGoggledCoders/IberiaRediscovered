package xyz.brassgoggledcoders.iberiarediscovered.event;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredAttributes;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredEffects;
import xyz.brassgoggledcoders.iberiarediscovered.module.MedicalHealingModule;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

public class MedicalHealingEventHandler {
    @SubscribeEvent
    public void onDamageReceived(LivingDamageEvent event) {
        if (event.getAmount() >= 2) {
            LivingEntity livingEntity = event.getEntity();
            if (livingEntity.getEffect(RediscoveredEffects.TREATED.get()) != null) {
                event.getEntity().removeEffect(RediscoveredEffects.TREATED.get());
            }
        }
    }

    @SubscribeEvent
    public void onAwake(PlayerWakeUpEvent event) {
        Player playerEntity = event.getEntity();
        final Difficulty worldDifficulty = event.getEntity().getLevel().getDifficulty();
        int healing = playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                .filter(Modules.MEDICAL_HEALING_MODULE::isActiveFor)
                .map(playerInfo -> playerInfo.getDifficultyFor(MedicalHealingModule.NAME, worldDifficulty))
                .map(difficulty -> switch (difficulty) {
                    case HARD -> 3;
                    case NORMAL -> 6;
                    default -> 9;
                })
                .orElse(0);

        if (healing > 0) {
            playerEntity.heal(healing);
        }

    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        if (playerTickEvent.phase == TickEvent.Phase.START && playerTickEvent.side == LogicalSide.SERVER) {
            playerTickEvent.player.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .ifPresent(playerInfo -> {
                        if (Modules.MEDICAL_HEALING_MODULE.isActiveFor(playerInfo)) {
                            playerInfo.tickAgeProgress(
                                    playerTickEvent.player.getAttributeValue(RediscoveredAttributes.AGE_PROGRESSION_SPEED_MODIFIER.get())
                            );
                        }
                    });
        }
    }
}
