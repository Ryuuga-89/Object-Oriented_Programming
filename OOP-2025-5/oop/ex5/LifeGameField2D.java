package oop.ex5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

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

    @Override
    public LifeGameField readField(String patternFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(patternFilePath))) {
            skipHeader(br);
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
