package oop.ex5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.stream.Collectors;

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

    @Override
    public LifeGameField readField(String patternFilePath) {
        int size = option.getSize();

        try (BufferedReader br = new BufferedReader(new FileReader(patternFilePath))) {
            String line;

            // ヘッダー部分をスキップ
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    break;
                }
            }

            // 本体部分の読み込み (Z -> Y -> X)
            for (int z = 0; z < size; z++) {
                for (int y = 0; y < size; y++) {
                    line = br.readLine();

                    while (line != null && (line.trim().isEmpty() || line.trim().startsWith("#"))) {
                        line = br.readLine();
                    }

                    // ファイル終端チェック
                    if (line == null) {
                        throw new IllegalArgumentException("Pattern file body is shorter than expected for 3D world of size: " + size);
                    }

                    // 行の長さチェック
                    if (line.length() != size) {
                        throw new IllegalArgumentException("Invalid line length at (z=" + z + ", y=" + y + "). Expected " + size + " but got " + line.length());
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

        } catch (IOException e) {
            throw new RuntimeException("Failed to read pattern file: " + patternFilePath, e);
        }

        return this;
    }

    @Override
    public void writeField(String outputFilePath) {
        PrintWriter writer = null;
        try {
            if (outputFilePath.equals("-")) {
                writer = new PrintWriter(new OutputStreamWriter(System.out));
            } else {
                writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)));
            }

            // ヘッダーの書き出し
            writer.println("WORLD: " + option.getWorld());
            writer.println("SIZE: " + option.getSize());

            String ruleB = option.getRuleB().stream().sorted().map(String::valueOf).collect(Collectors.joining());
            String ruleS = option.getRuleS().stream().sorted().map(String::valueOf).collect(Collectors.joining());
            writer.println("RULE: B" + ruleB + "/S" + ruleS);

            writer.println("NEIGHBORHOOD: " + option.getNeighborhood());
            writer.println("WRAP: " + option.getWrap());
            writer.println();

            // 本体の書き出し
            int size = option.getSize();
            for (int z = 0; z < size; z++) {
                for (int y = 0; y < size; y++) {
                    for (int x = 0; x < size; x++) {
                        writer.print(getCell(x, y, z) ? 'O' : '.');
                    }
                    writer.println();
                }

                // 区切り
                if (z < size - 1) {
                    writer.println();
                }
            }

            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException("Failed to write field to: " + outputFilePath, e);
        } finally {
            if (writer != null && !outputFilePath.equals("-")) {
                writer.close();
            }
        }
    }
}
