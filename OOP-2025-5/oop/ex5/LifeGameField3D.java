package oop.ex5;

public class LifeGameField3D extends LifeGameField {
    public LifeGameField3D(LifeGameOption option) {
        super(option);
    }

    @Override
    public LifeGameField nextGeneration() {
        LifeGameField3D nextField = new LifeGameField3D(super.option);
        int size = super.option.getSize();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    boolean nextState = isCellAliveInNextGen(x, y, z);
                    nextField.setCell(x, y, z, nextState);
                }
            }
        }

        return nextField;
    }

    @Override
    protected boolean isCellAliveInNextGen(int x, int y, int z) {
        int aliveNeighbors = 0;

        // 近傍に基づき生きている隣接セル数をカウント
        if (super.option.getNeighborhood().equals(AppConfig.NEIGHBORHOOD_FACES6)) {
            // 6近傍
            int[][] directions = {{1,0,0}, {-1,0,0}, {0,1,0}, {0,-1,0}, {0,0,1}, {0,0,-1}};
            for (int[] dir : directions) {
                if (this.getCell(x + dir[0], y + dir[1], z + dir[2])) aliveNeighbors++;
            }
        } else {
            throw new IllegalArgumentException("Unsupported neighborhood type");
        }

        return applyRule(x, y, z, aliveNeighbors);
    }
}
