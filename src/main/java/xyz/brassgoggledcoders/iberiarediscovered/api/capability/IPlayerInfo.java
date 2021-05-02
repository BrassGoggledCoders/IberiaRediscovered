package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;

public interface IPlayerInfo {
    void setAgeProgress(int ageProgress);

    int getAgeProgress();

    void setAge(int age);

    int getAge();

    int getMaxHealthRegenPercent(double modifier);

    void tickAgeProgress(double modifier);

    void resetAge();

    Difficulty getDifficultyFor(String module, Difficulty worldDifficulty);

    void setChosenDifficultyFor(String module, Difficulty chosenDifficulty);

    PlayerChoice getChoiceFor(String module);

    void setChoiceFor(String module, PlayerChoice playerChoice);

    void setOriginRebirth(WorldPos worldPos);

    WorldPos getOriginRebirth();

    void setLastRebirth(WorldPos worldPos);

    WorldPos getLastRebirth();
}
