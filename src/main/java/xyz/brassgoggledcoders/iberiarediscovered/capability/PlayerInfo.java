package xyz.brassgoggledcoders.iberiarediscovered.capability;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.Difficulty;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.PlayerChoice;
import xyz.brassgoggledcoders.iberiarediscovered.module.Module;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

import java.util.Map;
import java.util.Map.Entry;

public class PlayerInfo implements IPlayerInfo, INBTSerializable<CompoundNBT> {
    private double ageProgress;
    private int age;
    private int maxHealthRegenPercent;
    private Map<String, PlayerChoice> playerChoice = Maps.newHashMap();
    private final Map<String, Difficulty> chosenDifficulty = Maps.newHashMap();

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
    public PlayerChoice getChoiceFor(String module) {
        return this.playerChoice.getOrDefault(module, PlayerChoice.DEFAULT);
    }

    @Override
    public void setChoiceFor(String module, PlayerChoice playerChoice) {
        this.playerChoice.put(module, playerChoice);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("age", this.age);
        nbt.putDouble("ageProgress", this.ageProgress);
        CompoundNBT playerChoiceNBT = new CompoundNBT();
        for (Entry<String, PlayerChoice> playerChoiceEntry : playerChoice.entrySet()) {
            playerChoiceNBT.putString(playerChoiceEntry.getKey(), playerChoiceEntry.getValue().toString());
        }
        nbt.put("playerChoice", playerChoiceNBT);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.age = nbt.getInt("age");
        this.ageProgress = nbt.getDouble("ageProgress");
        CompoundNBT playerChoiceNBT = nbt.getCompound("playerChoice");
        for (String key : playerChoiceNBT.keySet()) {
            this.playerChoice.put(key, PlayerChoice.valueOf(playerChoiceNBT.getString(key)));
        }
        this.recalculateMaxHealthRegenPercent();
    }

    private void checkAgeProgress() {
        if (this.ageProgress >= Modules.MEDICAL_HEALING_MODULE.getTimeBetweenAge()) {
            this.ageProgress = 0;
            this.age++;
            this.recalculateMaxHealthRegenPercent();
        }
    }

    private void recalculateMaxHealthRegenPercent() {
        this.maxHealthRegenPercent = Math.max(5, 100 - (this.age * Modules.MEDICAL_HEALING_MODULE.getHealthRegenPercentLost()));
    }
}
