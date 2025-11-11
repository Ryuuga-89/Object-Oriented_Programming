class Main3A1 {
  public static void main(String[] args) {
    for (String s : args) {
      String[] parts = s.split(",");
      for (String part : parts) {
        System.out.println(part.trim());
      }
    }
  }
}
