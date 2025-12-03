package oop.ex5;

import java.util.List;

// ライフゲームのメインクラス
public class LifeGame {
    private LifeGameOption option;
    private List<LifeGameField> generations;

    public LifeGame(LifeGameOption option, LifeGameField firstGeneration) {
        this.option = option;
        this.generations = new java.util.ArrayList<>();
        this.generations.add(firstGeneration);
    }

    public void allRun(int generationsCount) {
        for (int i = 1; i < generationsCount; i++) {
            LifeGameField nextGen = generations.get(i - 1).nextGeneration();
            generations.add(nextGen);
        }
    }

    public List<LifeGameField> getGenerations() {
        return generations;
    }

    public LifeGameOption getOption() {
        return option;
    }
}
