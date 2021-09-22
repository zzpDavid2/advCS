package war;
  
import java.util.*;
import java.io.*;

public class War {
    public static void main(String[] args) throws FileNotFoundException {
    	play();
    	
    }
    
    public static void play() {
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
    	
    	System.out.println("How many cards should each player get? (26 max)");
    	
    	int numCards;
    	
    	try {
    		numCards = scn.nextInt();
    	}
    	catch(InputMismatchException e) {
        	System.out.println("Invalid input. Defaulted to 26.");
        	scn.next();
        	numCards = 26;
    	}
    	
    	if(numCards >=26) {
    		numCards = 26;
    	}
    	
    	for(int i=0; i<numCards;i++) {
    		P1.add(deck.get(i));
    		P2.add(deck.get(numCards+i));
    	}
    	
    	boolean haveWar = true;
    	
    	System.out.println("Play the game with war rules? (Y/N)");
    	String s = scn.next();
    	
    	if(s.equals("y") || s.equals("Y")) {
        	System.out.println("War rules are in play.");
        	haveWar = true;
    	}else if(s.equals("n") || s.equals("N")) {
        	System.out.println("War rules are deactivated, both cards are disgarded for a draw.");
    		haveWar = false;
    	}else {
        	System.out.println("Invalid input. War rules are in play by default.");
        	haveWar = true;
    	}
    	
    	scn.nextLine();
    	
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
    			if(haveWar) {
    				cardWar(P1,P2,C1,C2);
    			}else{
    				System.out.println("Draw! Both cards are disgarded.");
    			}
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
    		System.out.println(" cards remaining.");
    		
    		System.out.println("Press ENTER to deal cards.");
        	scn.nextLine();
        	
    	}
    	
    	if(Win1) {
    		System.out.println("Player1 is the winner!");
    	}
    	
    	if(Win2) {
    		System.out.println("Player2 is the winner!");
    	}
    	
    	scn.close();
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
    
    public static void cardWar(LinkedList p1, LinkedList p2, Card o1, Card o2) {
    	System.out.println("War! Each player draws 4 cards, the last card decides the winner.");
    	
    	LinkedList<Card> warCards = new LinkedList<Card>();
    	
    	warCards.add(o1);
    	warCards.add(o2);
    	
    	boolean end = false;

    	while(!end) {
        	System.out.println("P1 draws the following cards to war:");
    		for(int i=0; i<3; i++) {
    			Card polled = (Card) p1.pollFirst();
    			warCards.add(polled);
    			System.out.println(polled);
    		}
    		
        	System.out.println("P2 draws the following cards to war:");
    		for(int i=0; i<3; i++) {
    			Card polled = (Card) p2.pollFirst();
    			warCards.add(polled);
    			System.out.println(polled);
    		}
    		
    		Card C1 = (Card) p1.pollFirst();
			warCards.add(C1);
    		Card C2 = (Card) p2.pollFirst();
			warCards.add(C2);
    		
    		System.out.print("Player 1 plays the ");
    		System.out.println(C1);
    		System.out.print("Player 2 plays the ");
    		System.out.println(C2);
    		
    		if(C1.getNumber()>C2.getNumber()) {
    			System.out.println("Player 1 wins the war!");
    			System.out.println("Player 1 gets all the following cards from the war:");
    			for(Card c : warCards) {
    				System.out.println(c);
    				p1.add(c);
    			}
    			end = true;
    			break;
    			
    		}else if(C2.getNumber()>C1.getNumber()) {
    			System.out.println("Player 2 wins the war!");
    			System.out.println("Player 2 gets all the following cards from the war:");
    			for(Card c : warCards) {
    				System.out.println(c);
    				p2.add(c);
    			}
    			end = true;
    			break;
    			
    		}else {
    			System.out.println("Draw! The war goes on.");
    		}
    	}
    }
}
