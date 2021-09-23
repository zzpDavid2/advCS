package myLinkedList;

public class MyLinkedList {
	private Node head;
	
	private int size;
	
	public MyLinkedList () {
		head = null;
		size =0;
	}
	
	class Node{
		Object data;
		Node next;
		
		Node(Object d) {
			data = d;
		}	
		
	}
	
	public Object get(int n) {
		
		if(n>=size) {
			return null;			
		}
		
		Node tar = head;
		
		for(int i=0; i<n; i++) {
			tar = tar.next; 
		}
		
		return tar.data;
	}
	
	public Object add(Object d) {
		
		Node newNode = new Node(d);
		newNode.next = null;
		
		if(head == null) {
			head = newNode;
			size++;
			return d;
		}
		
		Node tar = head;
		
		while(tar.next != null) {
			tar = tar.next;
		}
		
		tar.next= newNode;
		
		size ++;
		
		return d;
	}
	
	public Object add(int n, Object d) {
		
		if(n>=size) {
			return null;			
		}
		
		Node tar = head;
		
		for(int i=0; i<n; i++) {
			tar = tar.next; 
		}
		
		Node newNode = new Node(d);
		newNode.next = tar.next;
		
		tar.next= newNode;
		
		size++;
		
		return d;
	}
	
	public Object remove(int n) {
		Node tar ;
		Node prev = head;
		Node next;
		
		if(n>=size) {
			return null;
		}
		
		if(n==0) {
			head = head.next;
			
		}
		
		for(int i=0; i<n-1; i++) {
			prev = prev.next; 
		}
		
		tar = prev.next;
		next = tar.next;
		prev.next = next;
		
		size--;
		
		return tar;
	}
	
	public int size() {
		return size;
	}
	
}
