package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

import net.minecraft.world.Difficulty;

import java.util.List;

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

    boolean isOptIn(String module);

    boolean isOptOut(String module);
}
