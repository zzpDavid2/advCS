package advCS;

import java.util.*;

public class MapExample {
	public static void main(String [] args) {
		
		HashMap<String, Boolean> myMap = new HashMap<String, Boolean>();
		
		myMap.put("Jason",false);
		myMap.put("James",true);
		myMap.put("Jake",true);
		
		for(String s : myMap.keySet()) {
			
			
		}
		
		System.out.println(myMap.get("Diana"));
		
		String bob = "bob";
		
		int hash = bob.hashCode();
		
		myMap.clear();
		
	}
}
