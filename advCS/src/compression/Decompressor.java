package compression;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.*;
import java.util.*;

public class Decompressor {
	static HashMap<String, Character> table = new HashMap<String, Character>();

	public static void main(String[] args) throws IOException {
		//select input map file
		FileDialog dialog = new FileDialog((Frame)null, "Select Map");
	    dialog.setMode(FileDialog.LOAD);
	    dialog.setVisible(true);
	    String file = dialog.getFile();
	    System.out.println(file + " chosen.");
	    
		FileReader fr = new FileReader(file);

		readMap(fr);
		
		decode(fr);
		
		fr.close();
	}
	
	public static void readMap(FileReader fr) throws IOException {
		//read code table
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
		//set up reader
		BufferedReader br = new BufferedReader(fr);
		
		//skipping a newline character
		br.readLine();
		
		//read in end mark
		String endMark = br.readLine();
		System.out.println(endMark);
		
		//put end mark in the table
		table.put(endMark, null);
		System.out.println(table);
		
//		char ip = (char) fr.read();
//		System.out.println(ip);
//		System.out.println(ip);
			
//		System.out.print(fr.read());
		
		//selecting input data file
		FileDialog dialog = new FileDialog((Frame)null, "Select Data File");
	    dialog.setMode(FileDialog.LOAD);
	    dialog.setVisible(true);
	    String file = dialog.getFile();
	    System.out.println(file + " chosen.");
		
	    //set up output file name
	    String decompressedFile = file.substring(0,file.length()-18) + "Decompressed.txt";
	    
	    //set up reader and writer
		BufferedBitReader bbr = new BufferedBitReader(file);
		PrintWriter pr = new PrintWriter( new File(decompressedFile));
		
		//decoding
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