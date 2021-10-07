package compressor;
 
import java.io.*;
import java.util.*;

import branch.*;
import myPriorityQueue.*;
import tuple.*;

public class Compressor {

	static HashMap<Character, String> table = new HashMap<Character, String> ();
	static String endMark;
	public static void main(String[] args) throws IOException {
		
		FileReader fr1 = new FileReader("Compressor.txt");
		
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for(int i = fr1.read(); i !=-1; i = fr1.read()) {
			char c = (char) i;
			if(map.containsKey(c)) {
				map.put(c, map.get(c)+1);
			}else {
				map.put(c, 1);
			}
		}
		
		for(Map.Entry<Character, Integer> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
		
		fr1.close();
		
		MyPriorityQueue<Branch<Character>> pq = new MyPriorityQueue<Branch<Character>>();
		
		for(Character element : map.keySet()) {
			pq.add(new Branch<Character>(element), map.get(element));
		}	
		
		System.out.println(pq);	
		
		pq.add(new Branch<Character>(null), -1);
		
		while(pq.size()>2) {
			int lp = pq.frontPriority();
			Branch<Character> left = pq.popFront();
			int rp = pq.frontPriority();
			Branch<Character> right = pq.popFront();
			int p = lp+rp;
			Tuple<Character, Integer> t = new Tuple<Character, Integer>(null, p);
			Branch<Character> parent = new Branch<Character>(left, right);

			pq.add(parent, p);
		}		
		
		Branch<Character> root = new Branch<Character>(pq.popFront(),pq.popFront());	
		
		triverse(root,"");
		
		System.out.println(table);
		
		String result = new String();
		
		FileReader fr2 = new FileReader("Compressor.txt");
		
		for(int i = fr2.read(); i !=-1; i = fr2.read()) {
			char c = (char) i;
			String next = table.get(c);
			result += next;
		}
		
		result +=endMark;
		
		System.out.println(endMark);
		
		fr2.close();
		
		while(result.length()%16!=0) {
			result += "0";
		}
		
		System.out.println(result);
		
		
		String op = new String();
		
		for(int i=0; i<result.length(); i+= 8) {
			int next = 0;
			for(int j=0; j<8;j++) {
				if(result.charAt(i+7-j) == '1' ) {
					next += Math.pow(2, 7-j);
				}
			}
			op += (char) next;
		}
		
		System.out.println(op);
	}
	
	public static void triverse(Branch<Character> n, String prev) {
		if(n.isLeaf) {
			if(n.data == null && n.isLeaf) {
				endMark = prev;
				return;
			}
			table.put(n.data, prev);
			return;
		}else {
			triverse(n.left, prev+"0");
			triverse(n.right, prev+"1");
		}
		
	} 
}
