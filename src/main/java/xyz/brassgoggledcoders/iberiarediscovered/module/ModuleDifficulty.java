package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraft.world.Difficulty;

import java.util.function.Function;

public enum ModuleDifficulty {
    WORLD(difficulty -> {
        if (difficulty == Difficulty.PEACEFUL) {
            return Difficulty.EASY;
        } else {
            return difficulty;
        }
    }),
    HARD(difficulty -> Difficulty.HARD),
    NORMAL(difficulty -> Difficulty.NORMAL),
    EASY(difficulty -> Difficulty.EASY);

    private final Function<Difficulty, Difficulty> getDifficult;

    ModuleDifficulty(Function<Difficulty, Difficulty> getDifficult) {
        this.getDifficult = getDifficult;
    }

    public Difficulty getDifficulty(Difficulty worldDifficulty) {
        return this.getDifficult.apply(worldDifficulty);
    }
}
