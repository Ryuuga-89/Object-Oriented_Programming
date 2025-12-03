package oop.ex5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

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
            // ヘッダー部分をスキップ
            skipHeader(br);

            // 本体部分の読み込み
            for (int z = 0; z < size; z++) {
                read2DLayer(br, z);
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
            writeHeader(writer);

            // 本体の書き出し
            int size = option.getSize();
            for (int z = 0; z < size; z++) {
                write2DLayer(writer, z);

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
