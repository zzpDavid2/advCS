package compressor;
 
import java.io.*;
import java.util.*;

import branch.*;
import myPriorityQueue.*;
import tuple.*;

public class Compressor {
	public static void main(String[] args) throws IOException {
		
		FileReader fr = new FileReader("Compressor.txt");
		
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for(int i = fr.read(); i !=-1; i = fr.read()) {
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
		
		fr.close();
		
		MyPriorityQueue<Branch<Tuple<Character, Integer>>> pq = new MyPriorityQueue<Branch<Tuple<Character, Integer>>> ();
		
		for(Character element : map.keySet()) {
			Tuple<Character, Integer> t = new Tuple<Character, Integer>(element, map.get(element));
			pq.add(new Branch<Tuple<Character, Integer>>(t, null, null, true), t.b);
		}	
		
		System.out.print(pq);	
		
		while(pq.size()>2) {
			Branch<Tuple<Character, Integer>> left = pq.popBack();
			Branch<Tuple<Character, Integer>> right = pq.popBack();
			int p = left.data.b + right.data.b;
			Tuple<Character, Integer> t = new Tuple<Character, Integer>(null, p);
			Branch<Tuple<Character, Integer>> parent = new Branch<Tuple<Character, Integer>>(t, left, right, false);

			pq.add(parent, parent.data.b);
		}		
		
		Branch<Tuple<Character, Integer>> root = new Branch<Tuple<Character, Integer>>(null, pq.popBack(),pq.popBack(), false);
		
		
	}
}
