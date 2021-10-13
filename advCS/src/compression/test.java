package compression;
 
import java.io.*;
import java.util.*;

public class test {

	
	public static void main(String[] args) throws IOException {
		
		PrintWriter pw = new PrintWriter("test.txt");
		int i = 155;
		System.out.println(i + " " + (char) i);
		
		pw.print((char) i);
		
		pw.close();
		
		FileReader fr = new FileReader("test.txt");
		
		int j = fr.read();
		
		System.out.println(j + " " + (char) j);
	}
	
}
