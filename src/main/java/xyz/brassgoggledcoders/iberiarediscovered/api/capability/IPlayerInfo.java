package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

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
}
