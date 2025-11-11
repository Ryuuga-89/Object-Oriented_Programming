public class Main3B2 {
  public static String buildString() {
    // fill here (without StringBuilder)
    return null;
  }

  public static String buildStringByBuilder() {
    // fill here (with StringBuilder)
    return null;
  }

  public static String measureString() {
    System.out.println("String append");
    long start = System.nanoTime();
    String s = buildString();
    long end = System.nanoTime();
    System.out.println("Process time (us) : " + ((end - start) / 1000));
    return s;
  }

  public static String measureStringBuilder() {
    System.out.println("StringBuilder append");
    long start = System.nanoTime();
    String s = buildStringByBuilder();
    long end = System.nanoTime();
    System.out.println("Process time (us) : " + ((end - start) / 1000));
    return s;
  }

  public static void main(String[] args) {
    String s1 = measureStringBuilder();
    String s2 = measureString();
    System.out.println("Identity check : " + s1.equals(s2));
  }
}
