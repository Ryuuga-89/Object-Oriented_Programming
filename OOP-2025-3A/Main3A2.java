import oop.ex3A2.Pair;

class Main3A2 {
  public static void main(String[] args) {
    Pair<Integer, String> p1 = new Pair<Integer, String>(21, "Hoge");
    Pair<String, Integer> p2 = new Pair<String, Integer>("ISCT", new Integer(10));

    System.out.println(p1);
    System.out.println(p2);
  }
}
