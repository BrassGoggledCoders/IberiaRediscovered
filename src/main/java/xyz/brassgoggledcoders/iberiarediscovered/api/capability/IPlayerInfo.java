package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.Difficulty;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerInfo extends INBTSerializable<CompoundNBT> {
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

    void setLastDeath(GlobalPos worldPos);

    GlobalPos getLastDeath();

    void setLastRebirth(GlobalPos worldPos);

    GlobalPos getLastRebirth();
}
