package graph;

import java.util.HashSet;

public class Vertex<E> {
	public E data;
	public HashSet<Vertex<E>> neighbors;
	
	public Vertex(E info) {
		neighbors = new HashSet<Vertex<E>>();
		data = info;
	}
	
	public String toString() {
		return data.toString();
	}
}
