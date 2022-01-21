package labeledGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayDeque;

public class Graph<E, T> {
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
	
	public void connect(E a, E b, T info) {	
		Vertex<E> A = this.vertices.get(a);
		Vertex<E> B = this .vertices.get(b);
		
		Edge<T> edge = new Edge<T>(A, B, info);
		
		A.neighbors.add(edge);
		B.neighbors.add(edge);
		
		return;
	}
	
	public void disconnect(E a, E b) {
		Vertex<E> A = this.vertices.get(a);
		Vertex<E> B = this .vertices.get(b);
		
		Edge<T> edge = findEdge(A, B);
		
		A.neighbors.remove(edge);
		B.neighbors.remove(edge);
		
		return;
	}
	
	public Edge<T> findEdge(Vertex<E> a, Vertex<E> b) {
		for(Edge e : a.neighbors) {
			if(e.getOpposit(a) == b) {
				return e;
			}
		}
		return null;
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
		
		for(Edge<T> e : a.neighbors) {
			e.getOpposit(a).neighbors.remove(e);
		}
		
		return info;
	}
	
	public String toString() {
		return vertices.toString();
	}
	
	public static void main(String[] args) {
		Graph<String, String> g = new Graph<String, String>();
		
		g.add("D");
		
		g.add("C");
		
		g.add("L");
		
		g.connect("D","L", "Friend");
		
		g.connect("D", "C", "Friend");
		
		g.add("H");
		
		g.disconnect("D", "C");
		
		g.disconnect("D", "H");
		
		g.add("David");
		g.add("Jason");
		g.add("Ian");
		g.add("Mr.Friedman");
		g.add("Justin");
		g.add("Ivy");
		g.add("Taylor");
		
		g.connect("David","Jason", "Friend");
		g.connect("David","Justin", "Friend");
		g.connect("Jason","Ian", "Friend");
		g.connect("Jason","Ivy", "Friend");
		g.connect("Ian","Ivy", "Friend");
		g.connect("Ivy", "Mr.Friedman", "Friend");
		
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
			
			for(Edge<T> e : (HashSet<Edge>) curr.neighbors) {
				
				Vertex<E> v = e.getOpposit(curr);
//				System.out.println(v);
				if(!leadsTo.containsValue(v) && !leadsTo.containsKey(v)) {
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
