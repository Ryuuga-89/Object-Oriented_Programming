import oop.ex5.*;

import java.io.*;
import java.util.*;

public class Main5 {
    public static void main(String[] args) {
        try {
            // コマンドライン引数の解析
            Map<String,String> cli = Util.parseArgs(args);
            String in   = cli.get("--pattern");
            String stepsS = cli.get("--steps");
            String out  = cli.get("--dump-final");
            String randomS = cli.get("--random");
            
            if (stepsS == null || out == null) usage("Missing: --steps or --dump-final");
            if (in == null && randomS == null) usage("Missing: --pattern or --random");

            // オプション解析用
            RawOption rawOption = new RawOption();

            // パターンファイルが指定されている場合、ヘッダーを読み込んでデフォルト設定とする
            // (randomが指定されていても、ワールド設定などをファイルから借りる使い方も許容する)
            if (in != null) {
                Map<String, String> pattern = new HashMap<>();
                try (BufferedReader br = new BufferedReader(new FileReader(in))) {
                    // ヘッダーの読み取り
                    while (true) {
                        String line = br.readLine();
                        if (line == null || line.trim().isEmpty()) break; // 空行(ヘッダー終了)で終了

                        if (line.trim().startsWith("#")) {
                            continue;
                        }

                        String[] parts = line.split(":");
                        if (parts.length == 2) {
                            pattern.put("--" + parts[0].trim().toLowerCase(), parts[1].trim());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                rawOption.mergeOption(pattern);
            }

            // コマンドライン引数で指定されたオプションをマージ
            rawOption.mergeOption(cli);

            // オプションの決定
            LifeGameOption option = new LifeGameOption(
                    rawOption.getWorld(),
                    rawOption.getSize(),
                    rawOption.getRule(),
                    rawOption.getNeighborhood(),
                    rawOption.getWrap()
            );

            // ワールドタイプから盤面オブジェクトを生成
            LifeGameField field;
            if (option.getWorld().equals(AppConfig.WORLD_2D)) {
                field = new LifeGameField2D(option);
            } else if (option.getWorld().equals(AppConfig.WORLD_HEX)) {
                field = new LifeGameFieldHEX(option);
            } else {
                field = new LifeGameField3D(option);
            }

            // 初期化処理の分岐
            if (randomS != null) {
                // ランダム初期化
                try {
                    double density = Double.parseDouble(randomS);
                    field.randomize(density);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Random density must be a number: " + randomS);
                }
            } else {
                // パターンファイルから読み込み
                field.readField(in);
            }
            
            LifeGame lifeGame = new LifeGame(option, field);
            lifeGame.run(Integer.parseInt(stepsS));

            // 最終盤面の出力
            lifeGame.getGeneration(Integer.parseInt(stepsS)).writeField(out);

        } catch (Throwable t) {
            fail(t.getMessage() == null ? t.toString() : t.getMessage());
        }
    }


    private static void usage(String msg) {
        System.err.println(msg);
        System.err.println("Usage: java Main5 [--pattern <input.pattern>] [--random <density>] --steps <N> --dump-final <output.pattern> "
                + "[--rule B3/S23] [--neighborhood MOORE8|VONN4|HEX6|FACES6] [--world WORLD_2D|WORLD_HEX|WORLD_3D] [--size N] [--wrap true|false]");
        System.exit(2);
    }

    private static void fail(String msg){ System.err.println("Error: " + msg); System.exit(1); }
}
