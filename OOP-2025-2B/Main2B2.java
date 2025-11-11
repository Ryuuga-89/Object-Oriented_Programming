import oop.ex2B2.MyBoolean;

class Main2B2 {
  public static void main(String[] args) {
    MyBoolean b1, b2;

    
    b1 = new MyBoolean(0);
    b2 = new MyBoolean(10);
    System.out.println(b1.getValue());
    System.out.println(b1.toString()); // 勝手にtoStringで変換されるので、実は呼び出す必要はない
    System.out.println(b2.getValue());
    System.out.println(b2); // 2行上と同様の意味

    System.out.println("等価性判定とhashCode");

    System.out.println(b1.equals(b2));
    System.out.println(b1.hashCode());
    System.out.println(b2.equals(b1));
    System.out.println(b2.hashCode());

    System.out.println("b1の中身を-1に変更");
    b1.setValue(-1);
    System.out.println(b1);

    System.out.println("再度等価性判定とhashCode");
    System.out.println(b1.equals(b2));
    System.out.println(b1.hashCode());
    System.out.println(b2.equals(b1));
    System.out.println(b2.hashCode());
    
  }
}
