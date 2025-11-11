package oop.ex3A2;

public class Pair<X, Y> {
	X x;
	Y y;

	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}

