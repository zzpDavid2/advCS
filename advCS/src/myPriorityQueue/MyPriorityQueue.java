package myPriorityQueue;

import java.util.LinkedList;

public class MyPriorityQueue<E> {
	LinkedList<Node> ll ;
	public MyPriorityQueue (){
		ll = new LinkedList<Node>();
		return;
	}
	
	public E add(E d, Integer p) {
		int i = binarySearch(0,ll.size()-1, p);
		Node n = new Node(d, p);
		if(i>=ll.size()) {
			ll.add(n);
		}else {
			ll.add(i, n);
		}
		
		return d;
	}
	
	public E pop() {
		return (E) ll.remove(0).data;
	}
	
	public int size() {
		return ll.size();
	}
	
	public String toString() {
		return ll.toString();
	}
	
	public class Node{
		E data;
		int prio;
		public Node(E d, int p) {
			data = d;
			prio = p;
		}
		public String toString() {
			String op = data + " " + prio;
			return op;
		} 
	}
	
	int binarySearch(int l, int r, int x){
	    if (r >= l) {
	        int mid = l + (r - l) / 2;
	  
	        if (ll.get(mid).prio == x)
	            return mid;
	  
	        if (ll.get(mid).prio > x)
	            return binarySearch(l, mid - 1, x);

	        return binarySearch(mid + 1, r, x);
	    }
	    return l;
	}
}
