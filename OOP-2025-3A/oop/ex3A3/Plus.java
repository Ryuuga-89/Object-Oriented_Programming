package oop.ex3A3;

public class Plus extends Exp {
	protected Exp left;
	protected Exp right;

	public Plus(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int calcResult() {
		return left.calcResult() + right.calcResult();
	}
}

