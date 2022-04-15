package util;

public class Tuple<E, F> implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3841772016633263197L;
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
