package oop.ex5;

// WORLD_2D用のライフゲームの盤面クラス
public class LifeGameField2D extends LifeGameField {
    public LifeGameField2D(LifeGameOption option) {
        super(option);
    }

    @Override
    public LifeGameField nextGeneration() {
        LifeGameField2D nextField = new LifeGameField2D(super.option);
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
        if (super.option.getNeighborhood().equals(AppConfig.NEIGHBORHOOD_MOORE8)) {
            // 8近傍
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue; // 自分自身はカウントしない
                    if (this.getCell(x + dx, y + dy, 0)) aliveNeighbors++;
                }
            }
        } else if (super.option.getNeighborhood().equals(AppConfig.NEIGHBORHOOD_VONN4)) {
            // Von Neumann 4近傍
            int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
            for (int[] dir : directions) {
                if (this.getCell(x + dir[0], y + dir[1], 0)) aliveNeighbors++;
            }
        } else {
            throw new IllegalArgumentException("Unsupported neighborhood type");
        }

        return applyRule(x, y, z, aliveNeighbors);
    }
}
