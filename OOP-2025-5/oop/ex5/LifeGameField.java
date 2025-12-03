package oop.ex5;

// ライフゲームの各回の盤面を表す抽象クラス。
// 生をtrue、死をfalseで表す3次元配列fieldを持つ。
public abstract class LifeGameField {
    protected LifeGameOption option;
    protected boolean[][][] field;

    public final void setCell(int x, int y, int z, boolean alive) {
        field[x][y][z] = alive;
    }

    public final void setField(boolean[][][] field) {
        this.field = field;
    }

    public final void setOption(LifeGameOption option) {
        this.option = option;
    }

    public LifeGameField(LifeGameOption option) {
        this.option = option;
        int size = option.getSize();
        if (option.getWorld() == AppConfig.WORLD_2D || option.getWorld() == AppConfig.WORLD_HEX) {
            field = new boolean[size][size][1];
        } else if (option.getWorld() == AppConfig.WORLD_3D) {
            field = new boolean[size][size][size];
        } else {
            throw new IllegalArgumentException("Unsupported world type");
        }
    }

    public final boolean getCell(int x, int y, int z) {
        // wrapを確認
        if (option.getWrap()) {
            int size = option.getSize();
            x = (x + size) % size;
            y = (y + size) % size;
            z = (z + size) % size;
        } else {
            int size = option.getSize();
            if (x < 0 || x >= size || y < 0 || y >= size || z < 0 || z >= size) {
                return false; // フィールド外は死
            }
        }

        return field[x][y][z];
    }

    public final LifeGameOption getOption() {
        return option;
    }

    public final boolean[][][] getField() {
        return field;
    }

    // 周囲の生きているセル数に基づきルールを適用して次世代の状態を決定
    protected final boolean applyRule(int x, int y, int z, int aliveNeighbors) {
        if (this.getCell(x, y, z)) {
            // 生きているセル: RuleSを適用
            return option.getRuleS().contains(aliveNeighbors);
        } else {
            // 死んでいるセル: RuleBを適用
            return option.getRuleB().contains(aliveNeighbors);
        }
    }

    abstract public LifeGameField nextGeneration(); // 次の盤面を決定するメソッド
    abstract protected boolean isCellAliveInNextGen(int x, int y, int z); // 指定したセルが次世代で生きているかを判定するヘルパーメソッド
    abstract public LifeGameField readField(String patternFilePath); // 盤面をファイルから読み取るメソッド
    abstract public void writeField(String outputFilePath); // 盤面をファイル(または標準出力)に書き出すメソッド
}
