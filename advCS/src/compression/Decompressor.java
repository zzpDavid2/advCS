package compression;

import java.io.*;
import java.util.*;

import java.io.IOException;

public class Decompressor {
	static HashMap<String, Character> table = new HashMap<String, Character>();

	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("compressorOut.txt");

		readMap(fr);
		
		decode(fr);
	}
	
	public static void readMap(FileReader fr) throws IOException {
		while(true) {
			char c = (char) fr.read();
//			System.out.println(c);
			char space = (char) fr.read();
//			System.out.println(space);
			if(space != ' ') {
				break;
			}
			String key = "";
			for(int i = fr.read(); i!=-1 && (char) i != '\n' && (char) i != '\r'; i = fr.read()) {
				char b = (char) i;
				key += b;
//				System.out.println(b);
			}
			
			table.put(key, c);
			fr.read();
		}
	}
	
	public static void decode(FileReader fr) throws IOException {
		BufferedReader br = new BufferedReader(fr);
		
		br.readLine();
		
		String endMark = br.readLine();
		System.out.println(endMark);
		table.put(endMark, null);
		System.out.println(table);
		
//		String ip = br.readLine();
//		System.out.println(ip);
		
		String data = "";
		
		BufferedBitReader bbr = new BufferedBitReader(br);
		
		PrintWriter out = new PrintWriter("decompressorOut.txt");
		
		while(bbr.hasNext()){
			boolean b = bbr.readBit();
			if(b) {
				data += "1";
				System.out.print("1");
			}else {
				data += "0";
				System.out.print("0");
			}
			if(table.containsKey(data)) {
				if(table.get(data) == null) {
					return;
				}
				char op = table.get(data);
				System.out.print(op + " ");
				out.print(op);
				data = "";
			}
		}
		
		bbr.close();
		
//		for(int i=0;i<ip.length();i++) {
//			int c = (int) ip.charAt(i);
//			System.out.println("New: " +c);
//			for(int j=7; j>=0; j--) {
//				int b = (int) (c / Math.pow(2, j));
//				c %= Math.pow(2, j);
//				System.out.println(Math.pow(2, j) + " " +b);
//				System.out.println(c);
//				if(b == 0) {
//					data += "0";
//				}else if(b == 1) {
//					data += "1";
//				}
//			}
//		}
		
	}
}
