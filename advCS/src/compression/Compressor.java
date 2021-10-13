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
		FileReader fr = new FileReader("compressorIn.txt");
		
		for(int i = fr.read(); i !=-1; i = fr.read()) {
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
		
		fr.close();
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
		
		BufferedBitWriter bbw = new BufferedBitWriter("compressedData.txt");
		
		System.out.println(endMark);
		
		PrintWriter out = new PrintWriter(new File("compressorOut.txt"));
		
		for(Character element : table.keySet()) {
		    out.println(element + " " + table.get(element));
		}
		out.println("fin");
		out.println(endMark);

		for(int i = fr.read(); i !=-1; i = fr.read()) {
			char c = (char) i;
			String next = table.get(c);
//			System.out.println(next);
			for(int j=0; j<next.length(); j++){
				if(next.charAt(j) == '1'){
					bbw.writeBit(true);
					System.out.print("1");
				}else {
					bbw.writeBit(false);
					System.out.print("0");
				}
			}
		}
		
		for(int i=0; i< endMark.length(); i++){
			if(endMark.charAt(i) == '1'){
				bbw.writeBit(true);
			}else {
				bbw.writeBit(false);
			}
		}
	    
//	    String temp = "";
//	    
//		for(int i = fr.read(); i !=-1; i = fr.read()) {
//			char c = (char) i;
//			String next = table.get(c);
////			System.out.println(next);
//			temp += next;
//			temp = writeChar(temp,out);
//		}
		
//		temp += endMark;
		
//		while(temp.length()<8) {
//			temp += "0";
//		}
		
//		writeChar(temp, out);
		
	    out.close();
		fr.close();
		bbw.close();
	}
	
//	public static String writeChar(String temp, PrintWriter out) {
//		System.out.println("writeChar");
//		System.out.println(temp);
//		while(temp.length()>8) {
//			int next = 0;
////			System.out.println("New char");
//			for(int i=0; i<8;i++) {
////				System.out.println(temp.charAt(i));
//				if(temp.charAt(i) == '1' ) {
//					next += Math.pow(2, 7-i);
//				}
//			}
//			System.out.println(next);
//			char op = (char) next;
//			out.print(op);
//			
//			System.out.println(op);
////			System.out.println(next);
////			System.out.println((char) next);
//			temp = temp.substring(8);
//		}
//		System.out.println(temp);
//		return temp;
//	}
}
