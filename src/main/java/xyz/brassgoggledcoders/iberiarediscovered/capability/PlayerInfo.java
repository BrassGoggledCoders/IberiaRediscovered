package xyz.brassgoggledcoders.iberiarediscovered.capability;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.world.Difficulty;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.module.Module;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

import java.util.List;
import java.util.Map;

public class PlayerInfo implements IPlayerInfo, INBTSerializable<CompoundNBT> {
    private List<String> optIn = Lists.newArrayList();
    private List<String> optOut = Lists.newArrayList();
    private double ageProgress;
    private int age;
    private int maxHealthRegenPercent;
    private Map<String, Difficulty> chosenDifficulty = Maps.newHashMap();

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
    public Difficulty getDifficultyFor(String moduleName, Difficulty worldDifficulty) {
        Difficulty difficulty = this.chosenDifficulty.get(moduleName);
        if (difficulty != null) {
            return difficulty;
        } else {
            Module module = Modules.get(moduleName);
            if (module != null) {
                return module.getDifficulty().getDifficulty(worldDifficulty);
            } else {
                return worldDifficulty;
            }
        }
    }

    @Override
    public void setChosenDifficultyFor(String module, Difficulty chosenDifficulty) {
        this.chosenDifficulty.put(module, chosenDifficulty);
    }

    @Override
    public boolean isOptIn(String module) {
        return this.optIn.contains(module);
    }

    @Override
    public boolean isOptOut(String module) {
        return this.optOut.contains(module);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("age", this.age);
        nbt.putDouble("ageProgress", this.ageProgress);
        nbt.put("optIn", convertList(this.optIn));
        nbt.put("optOut", convertList(this.optOut));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.age = nbt.getInt("age");
        this.ageProgress = nbt.getDouble("ageProgress");
        this.optIn = convertList(nbt.getList("optIn", Constants.NBT.TAG_STRING));
        this.optOut = convertList(nbt.getList("optOut", Constants.NBT.TAG_STRING));
        this.recalculateMaxHealthRegenPercent();
    }

    private void checkAgeProgress() {
        if (this.ageProgress >= Modules.MEDICAL_HEALING.getTimeBetweenAge()) {
            this.ageProgress = 0;
            this.age++;
            this.recalculateMaxHealthRegenPercent();
        }
    }

    private void recalculateMaxHealthRegenPercent() {
        this.maxHealthRegenPercent = Math.max(5, 100 - (this.age * Modules.MEDICAL_HEALING.getHealthRegenPercentLost()));
    }

    private ListNBT convertList(List<String> list) {
        ListNBT listNBT = new ListNBT();
        for (String value : list) {
            listNBT.add(StringNBT.valueOf(value));
        }
        return listNBT;
    }

    private List<String> convertList(ListNBT listNBT) {
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < listNBT.size(); i++) {
            list.add(listNBT.getString(i));
        }
        return list;
    }
}
