import oop.ex3A3.*;

class Main3A3 {
  public static void printResults(Exp[] exps) {
    for(Exp exp : exps) {
      System.out.println(exp.calcResult());
    }
  }
  public static void main(String[] args) {
    Exp exps[] = new Exp[3];
    exps[0] = new Num(1);
    exps[1] = new Num(-1);
    exps[2] = new Num(4);
    printResults(exps);
    // 以下をPlus実装後に有効にする
    exps = new Exp[5];
    exps[0] = new Num(2);
    exps[1] = new Num(-3);
    exps[2] = new Num(7);
    exps[3] = new Plus(exps[0], exps[1]);
    exps[4] = new Plus(exps[2], exps[3]);
    printResults(exps);
  }
}
