package xyz.brassgoggledcoders.iberiarediscovered.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;

public class PlayerInfo implements IPlayerInfo, INBTSerializable<CompoundNBT> {
    private double ageProgress;
    private int age;
    private int maxHealthRegenPercent;

    @Override
    public void setAgeProgress(int ageProgress) {
        this.ageProgress = Math.max(ageProgress, 0);
    }

    @Override
    public int getAgeProgress() {
        return (int) Math.ceil(this.ageProgress);
    }

    @Override
    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
            this.recalculateMaxHealthRegenPercent();
        } else {
            this.age = 0;
        }
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public int getMaxHealthRegenPercent(double modifier) {
        return this.maxHealthRegenPercent;
    }

    @Override
    public void tickAgeProgress(double modifier) {
        this.ageProgress += modifier;
        this.checkAgeProgress();
    }

    @Override
    public void resetAge() {
        this.age = 0;
        this.ageProgress = 0;
        this.recalculateMaxHealthRegenPercent();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("age", this.age);
        nbt.putDouble("ageProgress", this.ageProgress);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.age = nbt.getInt("age");
        this.ageProgress = nbt.getDouble("ageProgress");
        this.recalculateMaxHealthRegenPercent();
    }

    private void checkAgeProgress() {
        if (this.ageProgress >= 20 * 60 * 5) {
            this.age++;
            this.recalculateMaxHealthRegenPercent();
        }
    }

    private void recalculateMaxHealthRegenPercent() {
        this.maxHealthRegenPercent = Math.max(5, 100 - (this.age * 5));
    }
}
