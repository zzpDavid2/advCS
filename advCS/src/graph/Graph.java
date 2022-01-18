package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayDeque;

public class Graph<E> {
	HashMap<E, Vertex<E>> vertices;
	
	public Graph() {
		vertices = new HashMap<E, Vertex<E>>();
	}
	
	public boolean add(E info) {
		if(this.vertices.containsKey(info)) {
			return false;
		}
		
		vertices.put(info, new Vertex<E>(info));
		
		return true;
	}
	
	public void connect(E a, E b) {	
		Vertex<E> A = this.vertices.get(a);
		Vertex<E> B = this .vertices.get(b);
		
		A.neighbors.add(B);
		B.neighbors.add(A);
		
		return;
	}
	
	public void disconnect(E a, E b) {
		Vertex<E> A = this.vertices.get(a);
		Vertex<E> B = this .vertices.get(b);
		
		A.neighbors.remove(B);
		B.neighbors.remove(A);
		
		return;
	}
	
	public boolean contains(E info) {
		return vertices.containsKey(info);
	}
	
	public boolean isConnected(Vertex<E> a, Vertex<E> b) {	
		return a.neighbors.contains(b) && b.neighbors.contains(a);
	}
	
	public int size() {
		return this.vertices.size();
	}
	
	public E remove(E info) {
		Vertex<E> a = this.vertices.get(info);
		
		for(Vertex<E> b : a.neighbors) {
			b.neighbors.remove(a);
		}
		
		return info;
	}
	
	public String toString() {
		return vertices.toString();
	}
	
	public static void main(String[] args) {
		Graph<String> g = new Graph<String>();
		
		g.add("D");
		
		g.add("C");
		
		g.add("L");
		
		g.connect("D","L");
		
		g.connect("D", "C");
		
		g.add("H");
		
		g.disconnect("D", "C");
		
		g.disconnect("D", "H");
		
		g.add("David");
		g.add("Jason");
		g.add("Ian");
		g.add("Mr.Friedman");
		g.add("Justin");
		g.add("Ivy");
		
		g.connect("D","David");
		g.connect("David","Jason");
		g.connect("David","Justin");
		g.connect("Jason","Ian");
		g.connect("Jason","Ivy");
		g.connect("Ian","Ivy");
		g.connect("Ivy", "Mr.Friedman");
		
		g.bfs("David","Mr.Friedman");
		
//		System.out.println(g);
	}
	
	public void bfs(String startS, String endS) {
		HashMap<Vertex<E>,Vertex<E>> leadsTo = new HashMap<Vertex<E>,Vertex<E>>();
		
		Vertex<E> start = (Vertex<E>) vertices.get(startS);
		Vertex<E> end = (Vertex<E>) vertices.get(endS);
		
		ArrayDeque<Vertex<E>> toSearch = new ArrayDeque<Vertex<E>>();
		
		toSearch.add(start);
		
		while(!toSearch.isEmpty()) {
			
			Vertex<E> curr = toSearch.pop();
			
//			System.out.println(curr);
			
			if(curr == end) {
				break;
			}
			
			for(Vertex<E> v : (HashSet<Vertex<E>>) curr.neighbors) {
//				System.out.println(v);
				if(!leadsTo.containsValue(v)) {
					leadsTo.put(v, curr);
					toSearch.add(v);
				}
			}	
			
//			System.out.println();

		}
		
		Vertex<E> current = end;
		
		System.out.print(end);
		
		while(current != start) {
			current = leadsTo.get(current);
			System.out.print(" <- " + current);
		}
	}
	
	
}
