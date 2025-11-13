import oop.ex4A1.C1;
import oop.ex4A1.C2;
import java.util.Vector;

class Main4A1 {
  public static void main(String[] args) {
    Vector<C1> cs = new Vector<C1>();

    for(int i = -10; i <= 10; ++i) {
      if(i % 2 == 0) {
        cs.add(new C1(i));
      }
      else {
        cs.add(new C2(i));
      }
    }

    for(C1 c : cs) {
      System.out.println(c);
    }
  }
}
