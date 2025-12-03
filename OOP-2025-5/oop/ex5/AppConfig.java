package oop.ex5;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

// 設定を格納するクラス
public class AppConfig {
    // ワールドタイプ
    public static final String WORLD_2D = "WORLD_2D";
    public static final String WORLD_HEX = "WORLD_HEX";
    public static final String WORLD_3D = "WORLD_3D";

    // 近傍タイプ
    public static final String NEIGHBORHOOD_MOORE8 = "MOORE8";
    public static final String NEIGHBORHOOD_VONN4 = "VONN4";
    public static final String NEIGHBORHOOD_HEX6 = "HEX6";
    public static final String NEIGHBORHOOD_FACES6 = "FACES6";

    // 有効な設定値
    public static final List<String> VALID_WORLDS = Arrays.asList(WORLD_2D, WORLD_HEX, WORLD_3D);
    public static final int MIN_SIZE = 5;
    public static final int MAX_SIZE = 1000;
    public static final Map<String, List<String>> VALID_NEIGHBORHOODS = new HashMap<>() {{
        put(WORLD_2D, Arrays.asList(NEIGHBORHOOD_MOORE8, NEIGHBORHOOD_VONN4));
        put(WORLD_HEX, Arrays.asList(NEIGHBORHOOD_HEX6));
        put(WORLD_3D, Arrays.asList(NEIGHBORHOOD_FACES6));
    }};

    // デフォルト設定値
    public static final String DEFAULT_RULE = "B3/S23";
    public static final Map<String, String> DEFAULT_NEIGHBORHOOD = new HashMap<>() {{
        put(WORLD_2D, NEIGHBORHOOD_MOORE8);
        put(WORLD_HEX, NEIGHBORHOOD_HEX6);
        put(WORLD_3D, NEIGHBORHOOD_FACES6);
    }};
    public static final boolean DEFAULT_WRAP = false;

    // インスタンス化禁止のためにコンストラクタをprivateにする
    private AppConfig() {}
}