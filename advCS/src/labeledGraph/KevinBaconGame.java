package labeledGraph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import Shapes.Rectangle;
import Shapes.Shape;

import util.*;

public class KevinBaconGame {
	
	private static Graph<String, String> g;
	private static HashMap<String, String> actors;
	private static HashMap<String, String> movies;
	
	public static int connectionCnt = 0;
	
	private static String[] actorList;
	
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
		
		actorList = new String[actors.size()];
		
		int i=0;
		
		for(String s : actors.values()) {
			actorList[i] = s;
			i++;
		}
		
//		System.out.println(actors);
		
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
		
//		System.out.println(movies);
		
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
			
//			System.out.println(movieID + " " + currMovie);
			
			if(!movieID.equals(currMovie)) {
				//connects the actors of previous group
				connectGroup(currGroup, currMovie);
				
				//create new movie group
				currGroup = new ArrayList<String>();
				currMovie = movieID;
			}
			
			currGroup.add(actorID);
//			System.out.println(currGroup);
		}
		
		connectGroup(currGroup, currMovie);
		
//		System.out.println(g);
		
		sc.close();
		
		System.out.println(connectionCnt);
		
//		g.bfs("Robert Downey Jr.", "Kevin Bacon");
		
		KevinBaconGame kbg = new KevinBaconGame();
	}
	
	private static void connectGroup(ArrayList<String> currGroup, String currMovie) {
		for(int i=0; i<currGroup.size(); i++) {
			for(int j=i+1; j<currGroup.size(); j++) {
				String a = actors.get(currGroup.get(i));
				String b = actors.get(currGroup.get(j));
				String m = movies.get(currMovie);
				
//				System.out.println(m);
//				System.out.println(a + " " + b + " " + m);
				
				g.connect(a, b, m);
				connectionCnt ++;
			}
		}
	}
	
	private final int width = 1000, height = 667;
	
	private JFrame frame;
	
	private JPanel container;
	
	private JPanel theActor;
	private JLabel  actorLabel;
	private JButton play;
	private AutoCompleteComboBox  actorInput;
	
	private JPanel inMovies;
	private JLabel  moviesLabel;
	private JTextArea  moviesList;
	private JScrollPane scroll;
	
	private JLabel  distanceFromKB;
	
	private JLabel  farestPerson;
	
	private JPanel bfs;
	private JLabel connectTo;
	private AutoCompleteComboBox connectInput;
	private JButton connect;
	
	private JTextArea bfsOutput;
	
	public KevinBaconGame() {
		//The person
		//All movies he was in
		//Distence from Kevin Bacon
		//farest person he is connected to
		//bfs
		
		// setup graphics  
		frame = new JFrame();
		
		frame.setSize(width, height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		
		frame.setTitle("Kevin Bacon Game");
		
		container = new JPanel();
		
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		theActor = new JPanel();
		
//		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		
		actorLabel = new JLabel();
		actorLabel.setText("Actor: ");
		
//		actorInput = new AutoComboBox();
//		actorInput.setKeyWord(actorList);
		
		actorInput = new AutoCompleteComboBox(actorList);
		actorInput.setEditable(true);
		actorInput.addActionListener(actorInput);
		
		play = new JButton("Play");
		
		theActor.add(actorLabel);
		theActor.add(actorInput);
		theActor.add(play);
		
		moviesLabel = new JLabel();
		moviesLabel.setText("This actor is in these movies:");
		
		inMovies = new JPanel();
		
		moviesList = new JTextArea();
		moviesList.setText("");
		moviesList.setEditable(false);
		moviesList.setPreferredSize(new Dimension(500,300));
		
		JScrollPane scroll = new JScrollPane (moviesList, 
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setMaximumSize(new Dimension(500,300));
		scroll.setPreferredSize(new Dimension(500,300));
		
//		inMovies.add(moviesLabel);
		inMovies.add(scroll);
		
		distanceFromKB = new JLabel();
		distanceFromKB.setText("Distance from Kevin Bacon:");
		
		farestPerson = new JLabel();
		farestPerson.setText("The actor farest from this actor is:");
		
		//bfs function
		
		bfs = new JPanel();
		
		connectTo = new JLabel();
		connectTo.setText("Connect this actor with:");
		
//		connectInput = new AutoComboBox();
//		connectInput.setKeyWord(actorList);
		
		connectInput = new AutoCompleteComboBox(actorList);
//		connectInput.setEditable(true);
//		connectInput.addActionListener(actorInput);
		
		connect = new JButton("Connect");
		
		bfs.add(connectTo);
		bfs.add(connectInput);
		bfs.add(connect);
		
		bfsOutput = new JTextArea();
		bfsOutput.setEditable(false);
		bfsOutput.setPreferredSize(new Dimension(500,300));
		
		container.add(theActor);
		
		container.add(distanceFromKB);
		container.add(farestPerson);
		
		container.add(moviesLabel);
		container.add(scroll);
		container.add(inMovies);
		
		container.add(bfs);
		container.add(bfsOutput);
		
		frame.add(container);
		
		frame.setVisible(true);
		container.requestFocusInWindow();
		
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moviesList.setText("Working...");
				String actorName = actorInput.getSelectedItem().toString();
				distanceFromKB.setText("Distance from Kevin Bacon: " + String.valueOf((g.bfs(actorName, "Kevin Bacon").size()-1)/2));
				
				String s_movieList = "";
				ArrayList<String> l_movieList = g.getEdgeDataList(actorName);
				for(String s : l_movieList) {
					s_movieList += s + "\n";
				}
				
				System.out.println(s_movieList);
				
				moviesList.setText(s_movieList);
				frame.getContentPane().repaint();
			}		
		
		});
		
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bfsOutput.setText("Working...");
				String actorName = actorInput.getSelectedItem().toString();
				
				ArrayList<String> path = g.bfs(actorName, connectInput.getSelectedItem().toString());
				
				String op = "";
				
				op += path.get(path.size()-1);
				String prev;
				String curr = path.get(path.size()-1);
				
				for(int i=path.size()-2; i>=0; i--) {
					prev = curr;
					curr = path.get(i);
					
					String edgeData = g.getEdgeData(prev,curr);
					
					op +=" -(" + edgeData + ")-> " + curr;
				}
				
				bfsOutput.setText(op);
			}		
		
		});
	}
}
