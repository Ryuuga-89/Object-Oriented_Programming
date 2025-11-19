package oop.ex4B3;

public class EvaluateVisitor implements Visitor {
  private Value v;

  public Value evaluate(Expression e) {
    e.accept(this);
    return v;
  }

  public void visit(Number e) {
    v = e;
  }

  public void visit(Bool e) {
    v = e;
  }

  public void visit(Addition e) {
    e.getLeftExpression().accept(this);
    int left = v.getNumber();
    e.getRightExpression().accept(this);
    int right = v.getNumber();
    v = new Number(left + right);
  }

  public void visit(LessThan e) {
    e.getLeftExpression().accept(this);
    int left = v.getNumber();
    e.getRightExpression().accept(this);
    int right = v.getNumber();
    v = new Bool(left < right);
  }

  public void visit(IfThenElse e) {
    e.getConditional().accept(this);
    boolean cond = v.getBool();
    if(cond) {
      e.getThenPart().accept(this);
    } else {
      e.getElsePart().accept(this);
    }
  }
}