package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

import java.util.List;

public interface IPlayerInfo {
    void setAgeProgress(int ageProgress);

    int getAgeProgress();

    void setAge(int age);

    int getAge();

    int getMaxHealthRegenPercent(double modifier);

    void tickAgeProgress(double modifier);

    void resetAge();

    List<String> getOptIn();

    List<String> getOptOut();
}
