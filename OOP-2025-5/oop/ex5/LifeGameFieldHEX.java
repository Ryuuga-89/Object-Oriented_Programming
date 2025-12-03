package oop.ex5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

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
        try (BufferedReader br = new BufferedReader(new FileReader(patternFilePath))) {
            // ヘッダー部分をスキップ
            skipHeader(br);
            // 本体の読み込み
            read2DLayer(br, 0);
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
            writeHeader(writer);
            // 本体の書き出し
            write2DLayer(writer, 0);

            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException("Failed to write field to: " + outputFilePath, e);
        } finally {
            // ファイル出力の場合のみ閉じる
            if (writer != null && !outputFilePath.equals("-")) {
                writer.close();
            }
        }
    }
}