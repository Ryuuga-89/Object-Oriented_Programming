package oop.ex5;

import java.util.HashSet;
import java.util.Set;

// ライフゲームの設定を格納するクラス
public class LifeGameOption {
    private String world;
    private int size;
    private Set<Integer> ruleB;
    private Set<Integer> ruleS;
    private String neighborhood;
    private boolean wrap;

    // worldは、全てのフィールドの中で最初に設定されることを想定している
    private void setWorld(String world) {
        if (AppConfig.VALID_WORLDS.contains(world)) {
            this.world = world;
        } else {
            throw new IllegalArgumentException("Invalid world type: " + world);
        }
    }

    private void setSize(String size) {
        try {
            int parsedSize = Integer.parseInt(size);
            if (parsedSize >= AppConfig.MIN_SIZE && parsedSize <= AppConfig.MAX_SIZE) {
                this.size = parsedSize;
            } else {
                throw new IllegalArgumentException("Size must be between " + AppConfig.MIN_SIZE + " and " + AppConfig.MAX_SIZE);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Size must be an integer: " + size);
        }
    }

    private void setRule(String rule) {
        if (rule.equals("Default")) {
            rule = AppConfig.DEFAULT_RULE;
        }

        String[] parts = rule.split("/");
        if (parts.length != 2 || !parts[0].startsWith("B") || !parts[1].startsWith("S")) {
            throw new IllegalArgumentException("Invalid rule format: " + rule);
        }

        try {
            this.ruleB = parseRulePart(parts[0].substring(1)); // "3" -> {3}
            this.ruleS = parseRulePart(parts[1].substring(1)); // "23" -> {2, 3}
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid rule numbers in: " + rule);
        }
    }

    // 文字列から数値のセットを作るヘルパーメソッド
    private Set<Integer> parseRulePart(String part) {
        Set<Integer> rules = new HashSet<>();
        for (char c : part.toCharArray()) {
            if (!Character.isDigit(c)) {
                // 数字以外が含まれていたら例外を投げて、呼び出し元のcatchで拾わせる
                throw new NumberFormatException("Non-digit character found: " + c);
            }
            rules.add(Character.getNumericValue(c));
        }
        return rules;
    }

    private void setNeighborhood(String neighborhood) {
        // デフォルト設定使用時
        if (neighborhood.equals("Default")) {
            neighborhood = AppConfig.DEFAULT_NEIGHBORHOOD.get(this.world);;
        }

        boolean isValid = false;
        // 現在のworldが設定されていることを確認
        if (this.world == null) {
            throw new IllegalStateException("World must be set before setting neighborhood.");
        }

        // 現在のworldに対応する有効な近傍タイプかをチェック
        for (String validNeighborhood : AppConfig.VALID_NEIGHBORHOODS.get(this.world)) {
            if (validNeighborhood.equals(neighborhood)) {
                isValid = true;
                break;
            }
        }

        if (isValid) {
            this.neighborhood = neighborhood;
        } else {
            throw new IllegalArgumentException("Invalid neighborhood type: " + neighborhood + " for world: " + this.world);
        }
    }

    private void setWrap(String wrap) {
        if (wrap.equals("true")) {
            this.wrap = true;
        } else if (wrap.equals("false")) {
            this.wrap = false;
        } else if (wrap.equals("Default")) {
            this.wrap = AppConfig.DEFAULT_WRAP;
        } else {
            throw new IllegalArgumentException("Invalid wrap value: " + wrap);
        }
    }

    // コンストラクタの全引数は文字列で受け取る。
    // デフォルト設定を用いるときは"Default"を渡す。
    public LifeGameOption(String world, String size, String rule, String neighborhood, String wrap) {
        setWorld(world); // worldは必ず最初に設定する
        setSize(size);
        setRule(rule);
        setNeighborhood(neighborhood);
        setWrap(wrap);
    }

    // ゲッター群
    public String getWorld() { return world; } 
    public int getSize() { return size; }
    public Set<Integer> getRuleB() { return ruleB; }
    public Set<Integer> getRuleS() { return ruleS; }
    public String getNeighborhood() { return neighborhood; }
    public boolean getWrap() { return wrap; }
}
