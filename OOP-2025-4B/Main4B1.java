import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


public class Main4B1 {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("usage: java Main4B1 <file>");
            return;
        }

        // ファイルを読み込む
        String content = new String(Files.readAllBytes(Paths.get(args[0])));
        
        // 改行文字の個数を計算
        int lineNum = 0;
        for (char c : content.toCharArray()) {
            if (c == '\n') {
                lineNum++;
            }
        }
        
        // 単語数とユニーク単語数を計算
        int wordNum = 0;
        Set<String> uniqueWords = new HashSet<>();
        
        // 英数字以外の文字で分割
        String[] words = content.split("[^a-zA-Z0-9]+");
        
        for (String word : words) {
            // 空文字列でない場合のみカウント
            if (!word.isEmpty()) {
                wordNum++;
                // 大文字小文字を区別せずにユニーク単語として追加
                uniqueWords.add(word.toLowerCase());
            }
        }
        
        int uniqueWordNum = uniqueWords.size();
        
        // 結果を出力
        System.out.println("line num: " + lineNum);
        System.out.println("word num: " + wordNum);
        System.out.println("unique word num: " + uniqueWordNum);
    }
}
