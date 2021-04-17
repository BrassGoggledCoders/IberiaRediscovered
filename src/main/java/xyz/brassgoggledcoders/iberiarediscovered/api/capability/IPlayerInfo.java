package xyz.brassgoggledcoders.iberiarediscovered.api.capability;

public interface IPlayerInfo {
    void setAgeProgress(int ageProgress);

    int getAgeProgress();

    void setAge(int age);

    int getAge();

    int getMaxHealthRegenPercent();

    void tickAgeProgress();

    void resetAge();
}
