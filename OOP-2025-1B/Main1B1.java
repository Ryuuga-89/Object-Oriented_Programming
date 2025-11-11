class Main1B1 {
  public static void main(String[] args) {
    System.out.println("The number of arguments: " + args.length);
    for (int i = 0; i < args.length; i++) {
      System.out.printf("args[%d]: %s%n", i, args[i]);
    }
  }
}
