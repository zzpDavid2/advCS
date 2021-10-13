package compression;
 
import java.io.*;
import java.util.*;

import branch.*;
import myPriorityQueue.*;

public class Compressor {

	static HashMap<Character, String> table = new HashMap<Character, String> ();
	static String endMark;
	static HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	
	public static void main(String[] args) throws IOException {
		
		input();
		
		createCode();
		
		toFile();
	}
	
	public static void input() throws IOException {
		FileReader fr1 = new FileReader("compressorIn.txt");
		
		for(int i = fr1.read(); i !=-1; i = fr1.read()) {
			char c = (char) i;
			if(map.containsKey(c)) {
				map.put(c, map.get(c)+1);
			}else {
				map.put(c, 1);
			}
		}
		
//		for(Map.Entry<Character, Integer> entry : map.entrySet()) {
//			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
//		}
		
		fr1.close();
	}
	
	public static void createCode() {
MyPriorityQueue<Branch<Character>> pq = new MyPriorityQueue<Branch<Character>>();
		
		for(Character element : map.keySet()) {
			pq.add(new Branch<Character>(element), map.get(element));
		}	
		
//		System.out.println(pq);	
		
		pq.add(new Branch<Character>(null), -1);
		
		while(pq.size()>2) {
			int lp = pq.frontPriority();
			Branch<Character> left = pq.popFront();
			int rp = pq.frontPriority();
			Branch<Character> right = pq.popFront();
			int p = lp+rp;
			Branch<Character> parent = new Branch<Character>(left, right);

			pq.add(parent, p);
		}	
		
		Branch<Character> root = new Branch<Character>(pq.popFront(),pq.popFront());	
		
		triverse(root,"");
		
		System.out.println(table);
		
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
	
	public static void toFile() throws IOException {
		
		FileReader fr = new FileReader("compressorIn.txt");
		
		BufferedBitWriter bw = new BufferedBitWriter("compressorOut.txt");
		
		System.out.println(endMark);
		
		PrintWriter out = new PrintWriter(new File("compressorOut.txt"));
		
		for(Character element : table.keySet()) {
		    out.println(element + " " + table.get(element));
		}
		out.println("fin");
		out.println(endMark);
//	    out.println(op);
	    out.close();
	    
	    String temp = "";
	    
		for(int i = fr.read(); i !=-1; i = fr.read()) {
			char c = (char) i;
			String next = table.get(c);
//			System.out.println(next);
			temp += next;
			writeChar(temp,out);
		}
		
		for(int i=0; i<endMark.length(); i++) {
			if(endMark.charAt(i) == '1') {
				bw.writeBit(true);
			}else {
				bw.writeBit(false);
			}
		}
		
		bw.close();
		fr.close();
	}
	
	public static void writeChar(String temp, PrintWriter out) {
		while(temp.length()>8) {
			int next = 0;
//			System.out.println("New char");
			for(int i=0; i<8;i++) {
//				System.out.println(temp.charAt(i));
				if(temp.charAt(i) == '1' ) {
					next += Math.pow(2, 7-i);
				}
			}
			char op = (char) next;
			out.print(op);
//			System.out.println(next);
//			System.out.println((char) next);
			temp = temp.substring(8);
		}
	}
}
