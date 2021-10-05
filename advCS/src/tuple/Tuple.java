package tuple;

public class Tuple<E, F> {
	public E a;
	public F b;
	public Tuple(E x, F y) {
		a = x;
		b = y;
	}
	public String toString() {
		return "(" + a.toString() + ", " + b.toString() + ")";		
	}
}
