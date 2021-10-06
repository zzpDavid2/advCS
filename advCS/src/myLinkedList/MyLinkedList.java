package myLinkedList;

public class MyLinkedList <E> {
	private Node head;
	
	private int size;
	
	public MyLinkedList () {
		head = null;
		size =0;
	}
	
	class Node{
		E data;
		Node next;
		
		Node(E d) {
			data = d;
		}	
		
	}
	
	public E get(int n) {
		
		if(n>=size) {
			return null;			
		}
		
		Node tar = head;
		
		for(int i=0; i<n; i++) {
			tar = tar.next; 
		}
		
		return tar.data;
	}
	
	public E add(E d) {
		
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
	
	public E add(int n, E d) {
		
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
	
	public E remove(int n) {
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
		
		return (E) tar.data;
	}
	
	public int size() {
		return size;
	}
	
}
