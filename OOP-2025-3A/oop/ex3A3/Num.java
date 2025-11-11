package oop.ex3A3;

public class Num extends Exp {
  protected int num;
  public Num(int num) {
    this.num = num;
  }
  public int calcResult() {
    return num;
  }
}
