class Main4A2 {
  public static void main(String[] args) throws java.io.IOException {
    try (java.io.BufferedReader br = new java.io.BufferedReader(
            new java.io.InputStreamReader(System.in, java.nio.charset.StandardCharsets.UTF_8))) {
      while (true) {
        String line = br.readLine();
        if (line == null) {
          // EOF でも終了
          break;
        }
        if (line.matches("^\\s*$")) {
          // 空白のみの行で終了
          break;
        }
        System.out.println("INPUT: " + line);
      }
    }
  }
}
