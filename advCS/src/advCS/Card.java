package advCS;

import java.util.*;
import java.io.*;
import java.util.HashMap;

public class Card {

    public static HashMap<Integer,String> numToLet = new HashMap<Integer,String>();
	
	private int n;	private String suit;
	
	public Card(int x, String s){
		n=x;
		suit=s;
		for(int i=2; i<=10; i++) {
			numToLet.put(i,String.valueOf(i));
		
		}
		numToLet.put(1,"ace");
		numToLet.put(11,"jack");
		numToLet.put(12,"queen");
		numToLet.put(13,"king");
		
	}
	
	public int getNumber() {
		return n;
		
	}
	
	public String getSuit() {
		return suit;
		
	}
	
	public String toString() {
		return String.valueOf(numToLet.get(n)) + " of " + suit;
	}
	
}
