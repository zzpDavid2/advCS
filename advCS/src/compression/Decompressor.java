package compression;

import java.io.*;
import java.util.*;

public class Decompressor {
	static HashMap<String, Character> table = new HashMap<String, Character>();

	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("compressorOut.txt");

		readMap(fr);
		
		decode(fr);
		
		fr.close();
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
		
//		char ip = (char) fr.read();
//		System.out.println(ip);
//		System.out.println(ip);
			
//		System.out.print(fr.read());
		
		BufferedBitReader bbr = new BufferedBitReader("compressedData.txt");
		PrintWriter pr = new PrintWriter( new File("decompressorOut.txt"));
		
		String data = "";	

		while(bbr.hasNext()){
			boolean b = bbr.readBit();
			if(b) {
				data += "1";
			}else {
				data += "0";
			}
			if(table.containsKey(data)) {
//				System.out.println(temp);
				System.out.print(table.get(data));
				if(table.get(data) == null) {
					bbr.close();
					br.close();
					pr.close();					
					return;
				}
				pr.print(table.get(data));
				data = "";
			}
		}
		
		bbr.close();
		br.close();
		pr.close();

	}
}