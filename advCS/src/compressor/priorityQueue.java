package compressor;
  
import java.util.*;

public class priorityQueue {
	ArrayList<Character> a;
	HashMap<Character, Integer> map;
	public priorityQueue(HashMap<Character, Integer> m){
		a = new ArrayList<Character>();
		map = m;
	}
	
	public char add(char c) {
		int priority = map.get(c);
		int i=0;
		while(map.get(a.get(i))>priority) {
			i++;
		}
		return c;
	}
}
