package branch;

public class Branch <E> {
	public E data;

	public Branch<E> left;
	public Branch<E> right;
	
	public boolean isLeaf;
	
	public Branch(E d) {
		data = d;
		left = null;
		right = null;
		isLeaf = true;
	}
	
	public Branch(Branch<E> l, Branch<E> r) {
		data = null;
		left = l;
		right = r;
		isLeaf = false;
	}
	
	public String toString() {
		return data.toString();
	}
}
