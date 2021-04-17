package xyz.brassgoggledcoders.iberiarediscovered.capability;

import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerInfo implements IPlayerInfo, INBTSerializable<CompoundNBT> {
    private int ageProgress;
    private int age;
    private int maxHealthRegenPercent;

    @Override
    public void setAgeProgress(int ageProgress) {
        if (ageProgress > 0 && ageProgress < 20 * 60 * 5) {
            this.ageProgress = ageProgress;
        } else {
            this.ageProgress = 0;
        }
    }

    @Override
    public int getAgeProgress() {
        return this.ageProgress;
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
    public int getMaxHealthRegenPercent() {
        return this.maxHealthRegenPercent;
    }

    @Override
    public void tickAgeProgress() {
        this.ageProgress++;
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
        nbt.putInt("ageProgress", this.ageProgress);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.age = nbt.getInt("age");
        this.ageProgress = nbt.getInt("ageProgress");
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
