package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

public interface IPlayerInfo {
    void setAgeProgress(int ageProgress);

    int getAgeProgress();

    void setAge(int age);

    int getAge();

    int getMaxHealthRegenPercent(double modifier);

    void tickAgeProgress(double modifier);

    void resetAge();
}
