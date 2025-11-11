import oop.ex2B3.Data;

public class Main2B3 {
  public static void main(String[] args) {
    Data d1 = new Data();
    d1.field = -1;
    d1.intarray = new int[2];
    d1.next = new Data();
    d1.next.field = -2;
    d1.next.intarray = new int[1];
    d1.intarray[0] = 0;
    d1.intarray[1] = 1;
    d1.next.intarray[0] = 2;
    
    Data d2 = null;
    Data d3 = null;

    // shallowかdeepの一方を呼び出し代入
    // d2 = d1.shallow or d1.deep
    d2 = d1.shallow();

    d2.field = -3;
    d2.intarray[1] = 3;
    d2.next.field = -4;
    d2.next.intarray[0] = 4;

    // shallowかdeepの一方を呼び出し代入
    // d3 = d2.shallow or d2.deep
    d3 = d2.deep();

    d3.field = -5;
    d3.intarray[0] = 5;
    d3.next.field = -6;
    d3.next.intarray[0] = 6;

    System.out.println(d1);
    System.out.println(d2);
    System.out.println(d3);
  }
}
