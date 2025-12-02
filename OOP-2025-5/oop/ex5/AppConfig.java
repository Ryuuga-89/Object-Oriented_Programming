package oop.ex5;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

// 設定を格納するクラス
public class AppConfig {
    // 有効な設定値
    public static final List<String> VALID_WORLDS = Arrays.asList("WORLD_2D", "WORLD_HEX", "WORLD_3D");
    public static final int MIN_SIZE = 5;
    public static final int MAX_SIZE = 1000;
    public static final Map<String, List<String>> VALID_NEIGHBORHOODS = new HashMap<>() {{
        put("WORLD_2D", Arrays.asList("MOORE8", "VONN4"));
        put("WORLD_HEX", Arrays.asList("HEX6"));
        put("WORLD_3D", Arrays.asList("FACES6"));
    }};

    // デフォルト設定値
    public static final String DEFAULT_RULE = "B3/S23";
    public static final Map<String, String> DEFAULT_NEIGHBORHOOD = new HashMap<>() {{
        put("WORLD_2D", "MOORE8");
        put("WORLD_HEX", "HEX6");
        put("WORLD_3D", "FACES6");
    }};
    public static final boolean DEFAULT_WRAP = false;
}