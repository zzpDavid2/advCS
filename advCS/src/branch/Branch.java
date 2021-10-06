package branch;

public class Branch <E> {
	public E data;

	public Branch<E> left;
	public Branch<E> right;
	
	public boolean isLeaf;
	
	public Branch(E d, Branch<E> l, Branch<E> r, boolean b) {
		data = d;
		left = l;
		right = r;
		isLeaf = b;
	}
	
	public String toString() {
		return data.toString();
	}
}
