package labeledGraph;

import java.util.HashSet;

public class Vertex<E> {
	public E data;
	public HashSet<Edge> neighbors;
//	public HashMap<Vertex, T> neighbors;
	
	public Vertex(E info) {
		neighbors = new HashSet<Edge>();
		data = info;
	}
	
	public String toString() {
		return String.valueOf(neighbors.size());
	}
}
