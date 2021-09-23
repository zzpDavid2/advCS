package myLinkedList;

public class test {
	public static void main(String[] args) {
		MyLinkedList ll = new MyLinkedList();
		
		ll.add("a");
		
		System.out.print(ll.get(0)); // output a
		
		ll.add("b");
		
		ll.add("c");
		
		System.out.print(ll.get(2)); // output c
		
		ll.remove(0);
		
		System.out.print(ll.get(1)); // output c
		
		ll.remove(1);
		
		System.out.print(ll.size()); // output 1
		
		
		
	}
}
