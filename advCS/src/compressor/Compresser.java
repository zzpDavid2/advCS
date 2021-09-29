package compressor;

import java.io.*;
import java.util.*;

public class Compresser {
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
		
//		for(Map.Entry<Character, Integer> entry : map.entrySet()) {
//			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
//		}
		
		
	}
}
