import oop.ex2B1.*;

class Main2B1 {
  public static void main(String[] args) {
    Identifiable id1 = null;
    Identifiable id2 = null;

    id1 = new StudentAdapter("Kagaku", 23, 12345);
    id2 = new StudentAdapter("Kobayashi", 3, 24949);

    System.out.println(id1.getName());
    System.out.println(id2.getName());

    System.out.println(id1.getID());
    System.out.println(id2.getID());
  }
}
