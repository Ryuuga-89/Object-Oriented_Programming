package oop.ex5;

import java.util.Map;

// ライフゲームの設定が確定するまで、一時的に設定を格納するクラス
public class RawOption {
    private String world = "Default";
    private String size = "Default";
    private String rule = "Default";
    private String neighborhood = "Default";
    private String wrap = "Default";

    public void mergeOption(Map<String, String> map) {
        if (map.containsKey("--world")) {
            this.world = map.get("--world");
        }
        if (map.containsKey("--size")) {
            this.size = map.get("--size");
        }
        if (map.containsKey("--rule")) {
            this.rule = map.get("--rule");
        }
        if (map.containsKey("--neighborhood")) {
            this.neighborhood = map.get("--neighborhood");
        }
        if (map.containsKey("--wrap")) {
            this.wrap = map.get("--wrap");
        }
    }

    // ゲッター群
    public String getWorld() { return world; }
    public String getSize() { return size; }
    public String getRule() { return rule; }
    public String getNeighborhood() { return neighborhood; }
    public String getWrap() { return wrap; }
}