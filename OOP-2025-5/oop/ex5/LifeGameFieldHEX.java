package oop.ex5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.stream.Collectors;

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

    @Override
    public LifeGameField readField(String patternFilePath) {
        int size = option.getSize();
        
        try (BufferedReader br = new BufferedReader(new FileReader(patternFilePath))) {
            String line;
            
            // ヘッダー部分をスキップ（最初の空行まで読み飛ばす）
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    break;
                }
            }

            // 本体部分の読み込み
            for (int y = 0; y < size; y++) {
                line = br.readLine();
                
                // ファイル終端チェック
                if (line == null) {
                    throw new IllegalArgumentException("Pattern file body is shorter than SIZE: " + size);
                }

                // バリデーション: 行の長さチェック
                if (line.length() != size) {
                    throw new IllegalArgumentException("Invalid line length at line " + (y + 1) + ". Expected " + size + " but got " + line.length());
                }

                for (int x = 0; x < size; x++) {
                    char c = line.charAt(x);
                    if (c == 'O') {
                        setCell(x, y, 0, true);
                    } else if (c == '.') {
                        setCell(x, y, 0, false);
                    } else {
                        throw new IllegalArgumentException("Invalid character '" + c + "' at (" + x + ", " + y + ")");
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

            // ルール文字列の再構築
            String ruleB = option.getRuleB().stream().sorted().map(String::valueOf).collect(Collectors.joining());
            String ruleS = option.getRuleS().stream().sorted().map(String::valueOf).collect(Collectors.joining());
            writer.println("RULE: B" + ruleB + "/S" + ruleS);

            writer.println("NEIGHBORHOOD: " + option.getNeighborhood());
            writer.println("WRAP: " + option.getWrap());
            writer.println(); // ヘッダーと本体の区切り

            // 本体の書き出し
            int size = option.getSize();
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    writer.print(getCell(x, y, 0) ? 'O' : '.');
                }
                writer.println();
            }

            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException("Failed to write field to: " + outputFilePath, e);
        } finally {
            // ファイル出力の場合のみ閉じる（標準出力を閉じると以降の出力ができなくなるため）
            if (writer != null && !outputFilePath.equals("-")) {
                writer.close();
            }
        }
    }
}