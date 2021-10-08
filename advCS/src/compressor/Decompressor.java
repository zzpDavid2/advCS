package compressor;

import java.io.*;
import java.util.*;

import java.io.IOException;

public class Decompressor {

	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("compressorOut.txt");
		
		HashMap<String, Character> table = new HashMap<String, Character>();
		
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
		System.out.println(table);
		
		BufferedReader br = new BufferedReader(fr);
		
		br.readLine();
		
		String endMark = br.readLine();
		
		table.put(endMark, null);
		
		String ip = br.readLine();
		
		System.out.println(ip);
		
		br.close();
		
		String data = "";
		
		for(int i=0;i<ip.length();i++) {
			int c = (int) ip.charAt(i);
			for(int j=7; j>=0; j--) {
				int b = (int) (c / Math.pow(2, j));
				c %= Math.pow(2, j);
				if(b == 0) {
					data += "0";
				}else if(b ==1 ) {
					data += "1";
				}
			}
		}
		
		System.out.println(data);
		
		String op = "";
		String temp = "";
		
		for(int i=0; i<data.length(); i++) {
			temp += data.charAt(i);
			if(table.containsKey(temp)) {
				if(table.get(temp) == null) {
					return;
				}
				op += table.get(temp);
				temp = "";
			}
		}
		
		System.out.println(op);
	}
}
