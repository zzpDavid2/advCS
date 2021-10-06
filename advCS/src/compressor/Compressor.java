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
		
		MyPriorityQueue<Branch<Tuple<Character, Integer>>> pq = new MyPriorityQueue<Branch<Tuple<Character, Integer>>> ();
		
		for(Character element : map.keySet()) {
			Tuple<Character, Integer> t = new Tuple<Character, Integer>(element, map.get(element));
			pq.add(new Branch<Tuple<Character, Integer>>(t, null, null, true), t.b);
		}	
		
		System.out.println(pq);	
		
		pq.add(new Branch<Tuple<Character, Integer>>(new Tuple<Character, Integer>(null, 0), null, null, true), 0);
		
		while(pq.size()>2) {
			Branch<Tuple<Character, Integer>> left = pq.popFront();
			Branch<Tuple<Character, Integer>> right = pq.popFront();
			int p = left.data.b + right.data.b;
			Tuple<Character, Integer> t = new Tuple<Character, Integer>(null, p);
			Branch<Tuple<Character, Integer>> parent = new Branch<Tuple<Character, Integer>>(t, left, right, false);

			pq.add(parent, parent.data.b);
		}		
		
		Branch<Tuple<Character, Integer>> root = new Branch<Tuple<Character, Integer>>(null, pq.popBack(),pq.popBack(), false);	
		
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
	
	public static void triverse(Branch<Tuple<Character, Integer>> n, String prev) {
		if(n.isLeaf) {
			if(n.data.a == null) {
				endMark = prev;
				return;
			}
			table.put(n.data.a, prev);
			return;
		}else {
			triverse(n.left, prev+"0");
			triverse(n.right, prev+"1");
		}
		
	} 
}
