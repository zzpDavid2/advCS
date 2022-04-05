package locationGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;

public class Graph<E, R, T> {
	private HashMap<E, Vertex<E, R>> vertices;
	
	private HashSet<Edge<T>> edges;
	
	public Graph() {
		vertices = new HashMap<E, Vertex<E, R>>();
		edges = new HashSet<Edge<T>>();
	}
	
	public HashMap<E, Vertex<E, R>> getVertices () {
		return vertices;
	}
	
	public HashSet<Edge<T>> getEdges () {
		return edges;
	}
	
	public boolean add(E label, R data) {
		if(this.vertices.containsKey(label)) {
			return false;
		}
		
		vertices.put(label, new Vertex<E, R>(label, data));
		
		return true;
	}
	
	public void connect(E a, E b, T info) {	
		Vertex<E, R> A = this.vertices.get(a);
		Vertex<E, R> B = this .vertices.get(b);
		
		Edge<T> edge = new Edge<T>(A, B, info);
		
		A.neighbors.add(edge);
		B.neighbors.add(edge);
		
		edges.add(edge);
		
		return;
	}
	
	public void disconnect(E a, E b) {
		Vertex<E, R> A = this.vertices.get(a);
		Vertex<E, R> B = this .vertices.get(b);
		
		Edge<T> edge = findEdge(A, B);
		
		A.neighbors.remove(edge);
		B.neighbors.remove(edge);
		
		edges.remove(edge);
		
		return;
	}
	
	public Edge<T> findEdge(Vertex<E, R> a, Vertex<E, R> b) {
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
	
	public boolean isConnected(Vertex<E, R> a, Vertex<E, R> b) {	
		return a.neighbors.contains(b) && b.neighbors.contains(a);
	}
	
	public int size() {
		return this.vertices.size();
	}
	
	public E remove(E info) {
		Vertex<E, R> a = this.vertices.get(info);
		
		for(Edge<T> e : a.neighbors) {
			e.getOpposit(a).neighbors.remove(e);
		}
		
		return info;
	}
	
	public String toString() {
		return vertices.toString();
	}
	
	public static void main(String[] args) {
		Graph<String, Double, Double> g = new Graph<String, Double, Double>();
		
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
	
	private static void create_graph(Graph<String, Double, Double> g) {
		Double inf = Double.POSITIVE_INFINITY;
		g.add("a", 0.0);
		g.add("b", 0.0);
		g.add("c", 0.0);
		g.add("d", 0.0);
		g.add("e", 0.0);
		g.add("f", 0.0);
		
		g.connect("a", "b", 6.0);
		g.connect("a", "c", 1.0);
		g.connect("b", "d", 5.0);
		g.connect("c", "e", 2.0);
		g.connect("d", "f", 4.0);
		g.connect("e", "f", 3.0);	
	}
	
	private HashMap<Vertex<E, R> ,Double> vertexToDistance = new HashMap<Vertex<E, R>,Double>();
	
	public ArrayList<E> dijkstrasAlgorithm(E startS, E endS){

		PriorityQueue<Vertex<E, R>> toVisit = new PriorityQueue<Vertex<E, R>>(1, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Double d1 = vertexToDistance.get(o1);
				Double d2 = vertexToDistance.get(o2);

				return (int) (d1 - d2);
			}
		});
		
		HashSet<Vertex<E, R>> visited = new HashSet<Vertex<E, R>>();
		
		HashMap<Vertex<E, R>,Vertex<E, R>> leadsTo = new HashMap<Vertex<E, R>,Vertex<E, R>>();
		
		Vertex<E, R> start = (Vertex<E, R>) vertices.get(startS);
		Vertex<E, R> end = (Vertex<E, R>) vertices.get(endS);
		
		vertexToDistance.put(start, 0.0);
		
		toVisit.add(start);
		
		boolean isFinished = false;
		
		while(!isFinished) {
			Vertex<E, R> curr = toVisit.poll();
			if(curr == end) {
				isFinished = true;
				break;
			}
			
			double currDis = vertexToDistance.get(curr);
			
			for(Edge<Double> e : curr.neighbors) {
				Vertex<E, R> n = e.getOpposit(curr);
				
				if(visited.contains(n)) continue;
				
				Double distance = currDis + e.data;
				
				if(!vertexToDistance.containsKey(n)) {
					vertexToDistance.put(n, Double.POSITIVE_INFINITY);
				}
				
				if(vertexToDistance.get(n)> distance) {
					leadsTo.put(n, curr);
					vertexToDistance.put(n, distance);
				}
				
				if(!toVisit.contains(n))toVisit.add(n);
			}
			
			visited.add(curr);
		}
		
		System.out.println("End is " + vertexToDistance.get(end) + " distance away.");
		

//		System.out.println(leadsTo);
		
		ArrayList<E> op = new ArrayList<E>();
		
		Vertex<E, R> current = end;
		Vertex<E, R> prev;
		
		System.out.print(end);
		op.add(end.label);
		
		while(current != start) {
			prev = current;
			current = leadsTo.get(current);
			
//			System.out.println(prev + " " + current);
			T edgeData = findEdge(prev,current).data;
			
			System.out.print(" <-(" + edgeData + ")- " + current + "(" + vertexToDistance.get(current) + ")");
			op.add(current.label);
		}
		
		return op;
		
	}
	
	public ArrayList<E> bfs(E startS, E endS) {
		HashMap<Vertex<E, R>,Vertex<E, R>> leadsTo = new HashMap<Vertex<E, R>,Vertex<E, R>>();
		
		Vertex<E, R> start = (Vertex<E, R>) vertices.get(startS);
		Vertex<E, R> end = (Vertex<E, R>) vertices.get(endS);
		
		ArrayDeque<Vertex<E, R>> toSearch = new ArrayDeque<Vertex<E, R>>();
		
		toSearch.add(start);
		
		boolean ended = false;
		
		while(!toSearch.isEmpty() && !ended) {
			
			Vertex<E, R> curr = toSearch.pop();
			
			System.out.println(curr);
			
			for(Edge<T> e : (HashSet<Edge>) curr.neighbors) {
				
				Vertex<E, R> v = e.getOpposit(curr);
				
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
		
		Vertex<E, R> current = end;
		Vertex<E, R> prev;
		
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
		Vertex<E, R> v = (Vertex<E, R>) vertices.get(info);
		
		ArrayList<T> op = new ArrayList<T>();
		
		for(Edge<T> e : v.neighbors) {
			System.out.println(e.data);
			if(op.contains(e.data)) continue;
			op.add(e.data);
		}
		
		return op;
		
	}
	
	public T getEdgeData(E a, E b){
		Vertex<E, R> A = this.vertices.get(a);
		Vertex<E, R> B = this .vertices.get(b);
		
		T op = this.findEdge(A, B).data;
		
		return op;
		
	}
	
}
