package oop.ex5;

// WORLD_HEX用のライフゲームの盤面クラス
public class LifeGameFieldHEX extends LifeGameField {
    public LifeGameFieldHEX(LifeGameOption option) {
        super(option);
    }

    @Override
    public LifeGameField nextGeneration() {
        LifeGameFieldHEX nextField = new LifeGameFieldHEX(super.option);
        int size = super.option.getSize();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                boolean nextState = isCellAliveInNextGen(x, y, 0);
                nextField.setCell(x, y, 0, nextState);
            }
        }

        return nextField;
    }

    @Override
    protected boolean isCellAliveInNextGen(int x, int y, int z) {
        int aliveNeighbors = 0;

        // 近傍に基づき生きている隣接セル数をカウント
        if (super.option.getNeighborhood().equals(AppConfig.NEIGHBORHOOD_HEX6)) {
            // 6近傍
            int[][] directionsEven = {{1,0}, {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}};
            int[][] directionsOdd  = {{1,0}, {1,1}, {0,1}, {-1,0}, {0,-1}, {1,-1}};
            int[][] directions = (y % 2 == 0) ? directionsEven : directionsOdd;
            for (int[] dir : directions) {
                if (this.getCell(x + dir[0], y + dir[1], 0)) aliveNeighbors++;
            }
        } else {
            throw new IllegalArgumentException("Unsupported neighborhood type");
        }

        return applyRule(x, y, z, aliveNeighbors);
    }
}