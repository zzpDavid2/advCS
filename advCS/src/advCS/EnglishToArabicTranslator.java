package advCS;

import java.util.*;
import java.io.*;

public class EnglishToArabicTranslator {
	public static void main(String[] args) throws IOException {
		HashMap<String, String> engToArab = new HashMap<String, String>();
		HashMap<String, String> arabToEng = new HashMap<String, String>();

		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("EnglishToArabicDictionary.txt"));
			String english = null;
			for(String line = in.readLine(); line != null; line = in.readLine() ) {
				if(english == null) {
					english = line;
				}else {
					engToArab.put(english, line);
					arabToEng.put(line,english);
					english = null;
				}
			}
			in.close();
		}finally {}
		
		System.out.println("Input word in English or Arabic to get its couterpart in the other language.");
		
    	Scanner scn = new Scanner(System.in);
		while(true) {
			String input = scn.next();
			if(engToArab.containsKey(input)) {
				System.out.print(engToArab.get(input));
			}else if(arabToEng.containsKey(input)) {
				System.out.print(arabToEng.get(input));
			}else {
				System.out.println("Word not found in dictionary.");
			}
		}
	}

}
