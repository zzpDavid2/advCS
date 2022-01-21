package labeledGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class KevinBaconGame {
	
	private static Graph<String, String> g;
	private static HashMap<String, String> actors;
	private static HashMap<String, String> movies;
	
	public static void main(String args[]) throws IOException {
		
		g = new Graph<String, String>();
		
		Scanner sc = new Scanner(java.nio.file.Paths.get("Movie:Actors", "actors.txt"));
		
		actors = new HashMap<String, String>();
		while(sc.hasNext()) {
			String next = sc.nextLine();
			int sep = next.indexOf('~');
			String id = next.substring(0,sep);
			String name = next.substring(sep+1);
			
			actors.put(id, name);
			g.add(name);
		}
		
		sc.close();
		
		System.out.println(actors);
		
		sc = new Scanner(java.nio.file.Paths.get("Movie:Actors", "movies.txt"));
		
		movies = new HashMap<String, String>();
		while(sc.hasNext()) {
			String next = sc.nextLine();
			int sep = next.indexOf('~');
			String id = next.substring(0,sep);
			String name = next.substring(sep+1);
			
			movies.put(id, name);
		}
		
		sc.close();
		
		System.out.println(movies);
		
		sc.close();
		
		sc = new Scanner(java.nio.file.Paths.get("Movie:Actors", "movie-actors.txt"));
		
		//both of these stores ID
		String currMovie = "";
		ArrayList<String> currGroup = new ArrayList<String>();
		
		while(sc.hasNext()) {
			String next = sc.nextLine();
			int sep = next.indexOf('~');
			String movieID = next.substring(0,sep);
			String actorID = next.substring(sep+1);
			
			if(movieID != currMovie) {
				//connects the actors of previous group
				for(int i=0; i<currGroup.size(); i++) {
					for(int j=i; j<currGroup.size(); j++) {
						String a = actors.get(currGroup.get(i));
						String b = actors.get(currGroup.get(j));
						String m = movies.get(currMovie);
						
						System.out.println(a + " " + b + " " + m);
						
						g.connect(a, b, m);
					}
				}
				
				//create new movie group
				currGroup = new ArrayList<String>();
				movieID = currMovie;
			}
			
			currGroup.add(actorID);
		}
		
		System.out.println(g);
		
		sc.close();
		
	}
}
