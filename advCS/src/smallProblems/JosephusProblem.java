package smallProblems;

import java.util.Scanner;

public class JosephusProblem {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
		sc.close();
		
		Person p = new Person(0, n);
		
		System.out.println(p.kill());
		
	}
	
	private static class Person{
		Person next;
		int index;
		
		static int tar;
		
		public Person(Person first, int i, int t) {
			index = i;
			tar = t;
			
			if(index != tar-1) {
				next = new Person(first, ++i, t);
			}else if (index == tar-1){
				next = first;
			}
		}
		
		public Person(int i, int t) {
			Person first = this;
			index = i;
			tar = t;
			
			if(index != tar-1) {
				next = new Person(first, ++i, t);
			}else if (index == tar-1){
				next = first;
			}
		}
		
		public int kill() {
			if(next.next.equals(this)) {
				return index;
			}
			next = next.next;
			return next.kill();
		}
	}
}
