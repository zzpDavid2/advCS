package gameTree;

import java.util.ArrayList;

public class Hanoi {
	private ArrayList<Integer>[] poles;
	
	private static int n, x;
	
	public Hanoi(int n, int x) {
		this.n = n;
		this.x = x;
		this.poles = new ArrayList[x];
		for(int i=0; i<x; i++) {
			poles[i] = new ArrayList<Integer>();
		}
		for(int i=n-1; i>=0; i--) {
			poles[0].add(i);
		}

	}
	
	public ArrayList<Hanoi> next(){
		return null;
	}
	
	public boolean isEnd() {
		if(poles[x-1].size() == n) return true;
		return false;
	}
	
	public String toString() {
		String ret = "";
		for(int i=n-1; i>=0; i--) {
			for(int j=0; j<x; j++) {
				if(poles[j].size()>i) {
					ret += "[" + poles[j].get(i) + "] ";
				}else {
					ret += " |  ";
				}
			}
			ret += "\n";
			if(i == 0) {
				for(int k=0 ;k<x*4-1; k++) {
					ret += "—";
				}
			}
		}
		return ret;
	}
	
	public static void main(String[] args) {
		Hanoi start = new Hanoi(3, 3);
		
		System.out.println(start);
		System.out.println(start.hashCode());
	}
	
	public int hashCode() {
		Integer ret = 0;
		int index = 1;
		for(int i=0; i<x; i++) {
			for(int j=poles[i].size()-1; j>=0; j--) {
				int r = poles[i].get(j);
				ret += r * index;
				index *= 10;
			}
		}
		return ret;
	}
}
