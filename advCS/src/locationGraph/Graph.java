package locationGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;

public class Graph<E, T> {
	HashMap<E, Vertex<E, Double>> vertices;
	
	public Graph() {
		vertices = new HashMap<E, Vertex<E, Double>>();
	}
	
	public boolean add(E label) {
		if(this.vertices.containsKey(label)) {
			return false;
		}
		
		vertices.put(label, new Vertex<E, Double>(label, Double.POSITIVE_INFINITY));
		
		return true;
	}
	
	public void connect(E a, E b, T info) {	
		Vertex<E, Double> A = this.vertices.get(a);
		Vertex<E, Double> B = this .vertices.get(b);
		
		Edge<T> edge = new Edge<T>(A, B, info);
		
		A.neighbors.add(edge);
		B.neighbors.add(edge);
		
		return;
	}
	
	public void disconnect(E a, E b) {
		Vertex<E, Double> A = this.vertices.get(a);
		Vertex<E, Double> B = this .vertices.get(b);
		
		Edge<T> edge = findEdge(A, B);
		
		A.neighbors.remove(edge);
		B.neighbors.remove(edge);
		
		return;
	}
	
	public Edge<T> findEdge(Vertex<E, Double> a, Vertex<E, Double> b) {
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
	
	public boolean isConnected(Vertex<E, Double> a, Vertex<E, Double> b) {	
		return a.neighbors.contains(b) && b.neighbors.contains(a);
	}
	
	public int size() {
		return this.vertices.size();
	}
	
	public E remove(E info) {
		Vertex<E, Double> a = this.vertices.get(info);
		
		for(Edge<T> e : a.neighbors) {
			e.getOpposit(a).neighbors.remove(e);
		}
		
		return info;
	}
	
	public String toString() {
		return vertices.toString();
	}
	
	public static void main(String[] args) {
		Graph<String, Double> g = new Graph<String, Double>();
		
		create_graph(g);
		
		g.dijkstrasAlgorithm("a", "d");
	}
	
	/*
	 * test data:
	 * 		 (a)-6(b)
	 * 		/1	     \5
	 * 	   (c)	    (d)
	 * 		\2		 /4
	 * 		 (e)-3(f)
	 * 
	 * (a) -> (d)
	 * 
	 * output: 10 (correct)
	 */
	
	private static void create_graph(Graph<String, Double> g) {
		Double inf = Double.POSITIVE_INFINITY;
		g.add("a");
		g.add("b");
		g.add("c");
		g.add("d");
		g.add("e");
		g.add("f");
		
		g.connect("a", "b", 6.0);
		g.connect("a", "c", 1.0);
		g.connect("b", "d", 5.0);
		g.connect("c", "e", 2.0);
		g.connect("d", "f", 4.0);
		g.connect("e", "f", 3.0);	
	}
	
	public ArrayList<E> dijkstrasAlgorithm(E startS, E endS){
		PriorityQueue<Vertex<E, Double>> toVisit = new PriorityQueue<Vertex<E, Double>>(1, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Vertex<String, Double> t1 = (Vertex<String, Double>) o1;
				Vertex<String, Double> t2 = (Vertex<String, Double>) o2;
				
				return (int) (t1.data - t2.data);
			}
		});
		
		HashSet<Vertex<E, Double>> visited = new HashSet<Vertex<E, Double>>();
		
		HashMap<Vertex<E, Double>,Vertex<E, Double>> leadsTo = new HashMap<Vertex<E, Double>,Vertex<E, Double>>();
		
		Vertex<E, Double> start = (Vertex<E, Double>) vertices.get(startS);
		Vertex<E, Double> end = (Vertex<E, Double>) vertices.get(endS);
		
		start.data = 0.0;
		
		toVisit.add(start);
		
		boolean isFinished = false;
		
		while(!isFinished) {
			Vertex<E, Double> curr = toVisit.poll();
			if(curr == end) {
				isFinished = true;
				break;
			}
			
			for(Edge<Double> e : curr.neighbors) {
				Vertex<E, Double> n = e.getOpposit(curr);
				
				if(visited.contains(n)) continue;
				
				n.data = Math.min(n.data, curr.data + e.data);
				
				// how does dijkstra's record the path we came in?
//				if(!leadsTo.containsKey(v)) {
//					leadsTo.put(v, curr);
//				}
				
				if(!toVisit.contains(n))toVisit.add(n);
			}
			
			visited.add(curr);
		}
		
		System.out.println("End is " + end.data + "distance away.");
		
		return new ArrayList<E>();
		
	}
	
	public ArrayList<E> bfs(E startS, E endS) {
		HashMap<Vertex<E, Double>,Vertex<E, Double>> leadsTo = new HashMap<Vertex<E, Double>,Vertex<E, Double>>();
		
		Vertex<E, Double> start = (Vertex<E, Double>) vertices.get(startS);
		Vertex<E, Double> end = (Vertex<E, Double>) vertices.get(endS);
		
		ArrayDeque<Vertex<E, Double>> toSearch = new ArrayDeque<Vertex<E, Double>>();
		
		toSearch.add(start);
		
		boolean ended = false;
		
		while(!toSearch.isEmpty() && !ended) {
			
			Vertex<E, Double> curr = toSearch.pop();
			
			System.out.println(curr);
			
			for(Edge<T> e : (HashSet<Edge>) curr.neighbors) {
				
				Vertex<E, Double> v = e.getOpposit(curr);
				
				System.out.println(v);
//				if(!leadsTo.containsValue(v) && !leadsTo.containsKey(v)) {
				if(!leadsTo.containsKey(v)) {
					leadsTo.put(v, curr);
					toSearch.add(v);
				}
				
				if(v == end) {
					ended = true;
					break;
				}
			}	
			
			System.out.println();

		}
		
		System.out.println(leadsTo);
		
		ArrayList<E> op = new ArrayList<E>();
		
		Vertex<E, Double> current = end;
		Vertex<E, Double> prev;
		
		System.out.print(end);
		op.add(end.label);
		
		while(current != start) {
			prev = current;
			current = leadsTo.get(current);
			
			T edgeData = findEdge(prev,current).data;
			
			System.out.print(" <-(" + edgeData + ")- " + current);
			op.add(current.label);
		}
		
		return op;
	}
	
	public ArrayList<T> getEdgeDataList(E info){
		Vertex<E, Double> v = (Vertex<E, Double>) vertices.get(info);
		
		ArrayList<T> op = new ArrayList<T>();
		
		for(Edge<T> e : v.neighbors) {
			System.out.println(e.data);
			if(op.contains(e.data)) continue;
			op.add(e.data);
		}
		
		return op;
		
	}
	
	public T getEdgeData(E a, E b){
		Vertex<E, Double> A = this.vertices.get(a);
		Vertex<E, Double> B = this .vertices.get(b);
		
		T op = this.findEdge(A, B).data;
		
		return op;
		
	}
	
}
