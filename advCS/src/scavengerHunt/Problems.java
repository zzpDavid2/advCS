package scavengerHunt;

import java.util.HashMap;

public class Problems {
	public void main(String[] args) {
		
	}
	
	public void p3(String ip) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for(int i=0; i<ip.length();i++) {
			char c = ip.charAt(i);
			if(!map.containsKey(c)) {
				map.put(c, 0);
			}
			
			map.put(c, map.get(c)+1);
		}
		
		for(int i=0; i<ip.length(); i++) {
			
		}
	}
		
	class CharFre {
		int f;
		char c;
		
		public CharFre(int a, char b) {
			f = a;
			c = b;
		}
	}
}
