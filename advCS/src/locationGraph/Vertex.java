package locationGraph;

import java.util.HashSet;

public class Vertex<E, R> {
	public E label;
	public R data;
	public HashSet<Edge> neighbors;
//	public HashMap<Vertex, T> neighbors;
	
	public Vertex(E label, R data) {
		neighbors = new HashSet<Edge>();
		this.label = label;
		this.data = data;
	}
	
	public String toString() {
		return label.toString() + ": " + data.toString();
	}
}
