package oop.ex5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.stream.Collectors;

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
        if (option.getWorld().equals(AppConfig.WORLD_2D) || option.getWorld().equals(AppConfig.WORLD_HEX)) {
            field = new boolean[size][size][1];
        } else if (option.getWorld().equals(AppConfig.WORLD_3D)) {
            field = new boolean[size][size][size];
        } else {
            throw new IllegalArgumentException("Unsupported world type" + option.getWorld());
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

    // 盤面をランダムに初期化
    public void randomize(double density) {
        if (density < 0.0 || density > 1.0) {
            throw new IllegalArgumentException("Density must be between 0.0 and 1.0");
        }
        Random rand = new Random();
        int size = option.getSize();
        // 3Dの場合は全層、2D/HEXの場合は1層のみ対象
        int zSize = option.getWorld().equals(AppConfig.WORLD_3D) ? size : 1;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < zSize; z++) {
                    // densityの確率で生存セルを配置
                    setCell(x, y, z, rand.nextDouble() < density);
                }
            }
        }
    }

    // ヘッダー部分をスキップ
    protected void skipHeader(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                break;
            }
        }
    }

    // 1層分のデータを読み込む
    protected void read2DLayer(BufferedReader br, int z) throws IOException {
        int size = option.getSize();
        String line;

        for (int y = 0; y < size; y++) {
            line = br.readLine();

            // 空行やコメント行をスキップして次の有効な行を探す
            while (line != null && (line.trim().isEmpty() || line.trim().startsWith("#"))) {
                line = br.readLine();
            }

            if (line == null) {
                throw new IllegalArgumentException("Pattern file body is shorter than expected size: " + size);
            }

            if (line.length() != size) {
                throw new IllegalArgumentException("Invalid line length at (y=" + y + ", z=" + z + "). Expected " + size + " but got " + line.length());
            }

            for (int x = 0; x < size; x++) {
                char c = line.charAt(x);
                if (c == 'O') {
                    setCell(x, y, z, true);
                } else if (c == '.') {
                    setCell(x, y, z, false);
                } else {
                    throw new IllegalArgumentException("Invalid character '" + c + "' at (" + x + ", " + y + ", " + z + ")");
                }
            }
        }
    }

    // ヘッダー情報を書き出す
    protected void writeHeader(PrintWriter writer) {
        writer.println("WORLD: " + option.getWorld());
        writer.println("SIZE: " + option.getSize());

        // ルール文字列の再構築
        String ruleB = option.getRuleB().stream().sorted().map(String::valueOf).collect(Collectors.joining());
        String ruleS = option.getRuleS().stream().sorted().map(String::valueOf).collect(Collectors.joining());
        writer.println("RULE: B" + ruleB + "/S" + ruleS);

        writer.println("NEIGHBORHOOD: " + option.getNeighborhood());
        writer.println("WRAP: " + option.getWrap());
        writer.println(); // ヘッダーと本体の区切り
    }

    // 1層分のデータを書き出す
    protected void write2DLayer(PrintWriter writer, int z) {
        int size = option.getSize();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                writer.print(getCell(x, y, z) ? 'O' : '.');
            }
            writer.println();
        }
    }

    abstract public LifeGameField nextGeneration(); // 次の盤面を決定するメソッド
    abstract protected boolean isCellAliveInNextGen(int x, int y, int z); // 指定したセルが次世代で生きているかを判定するヘルパーメソッド
    abstract public LifeGameField readField(String patternFilePath); // 盤面をファイルから読み取るメソッド
    abstract public void writeField(String outputFilePath); // 盤面をファイル(または標準出力)に書き出すメソッド
}
