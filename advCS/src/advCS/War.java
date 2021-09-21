package advCS;

import java.util.*;
import java.io.*;

public class War {
    public static void main(String[] args) throws FileNotFoundException {
    	LinkedList<Card> deck = new LinkedList<Card>();
    	
    	for(int i=1; i<14; i++) {
    		deck.add(new Card(i,"clubs"));
    	}
    	for(int i=1; i<14; i++) {
    		deck.add(new Card(i,"diamonds"));
    	}
    	for(int i=1; i<14; i++) {
    		deck.add(new Card(i,"hearts"));
    	}
    	for(int i=1; i<14; i++) {
    		deck.add(new Card(i,"spades"));
    	}
    	
    	Scanner scn = new Scanner(System.in);
    	
    	System.out.println("Hi, welcome to card game: War");
    	
    	shuffle(deck);
    	
    	LinkedList<Card> P1 = new LinkedList<Card>();
    	LinkedList<Card> P2 = new LinkedList<Card>();
    	
    	for(int i=0; i<27;i++) {
    		P1.add(deck.get(i));
    		P2.add(deck.get(16+i));
    	}
    	
    	System.out.println("Cards are shuffled. Press ENTER to play!");
    	scn.nextLine();
    	
    	int N1 = P1.size();
    	int N2 = P2.size();
    	boolean Win1 = false;
    	boolean Win2 = false;
    	
    	while(!Win1 && !Win2){
    		Card C1 = P1.pollFirst();
    		Card C2 = P2.pollFirst();
    		
    		System.out.print("Player 1 plays the ");
    		System.out.println(C1);
    		System.out.print("Player 2 plays the ");
    		System.out.println(C2);
    		
    		if(C1.getNumber()>C2.getNumber()) {
    			System.out.println("Player 1 wins the round!");
    			P1.addLast(C1);
    			P1.addLast(C2);
    		}else if(C2.getNumber()>C1.getNumber()) {
    			System.out.println("Player 2 wins the round!");
    			P2.addLast(C1);
    			P2.addLast(C2);
    		}else {
    			System.out.println("Draw! Both cards are disgarded.");
    		}
    		
    		N1 = P1.size();
    		N2 = P2.size();
    			
    		if(N2==0) {
    			Win1 = true;
    			break;
    		}
    		
    		if(N1==0) {
    			Win2 = true;
    			break;
    		}
    		
    		System.out.print("Player 1 have ");
    		System.out.print(N1);
    		System.out.print(" cards remaining; Player 2 have ");
    		System.out.print(N2);
    		System.out.print(" cards remaining.");
    		
    		System.out.println("Press ENTER to deal cards.");
        	scn.nextLine();
        	
    	}
    	
    	if(Win1) {
    		System.out.println("Player1 is the winner!");
    	}
    	
    	if(Win2) {
    		System.out.println("Player2 is the winner!");
    	}
    	
    }
    
    public static void shuffle(LinkedList<Card> ll) {
    	Random rand = new Random();
    	int tar;
    	int size=ll.size();
    	for(int i=0; i<size; i++) {
    		Card A = (Card) ll.get(i);
    		tar = rand.nextInt(size);
    		Card B = (Card) ll.get(tar);
    		ll.set(i, B);
    		ll.set(tar, A);
    		
    	}
    	
    }
}
