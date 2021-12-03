package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Shapes.Circle;
import Shapes.Line;
import Shapes.Shape;
import graphics.GraphicsEditor;

public class DrawMaze {

	private ArrayList<Shape> shapes;
	
	private final int width = 650, height = 650;
	
	private JFrame frame;
	
	private JPanel canvas;
	
	private int tileWidth;
	private int tileHeight;
	
	boolean[][] maze;

	public DrawMaze(boolean[][] m) {
		
		maze = m;
		
		tileWidth = width/maze[0].length;
		tileHeight = height/maze.length;
	    
		System.out.println(tileWidth + " " + tileHeight);
		
		frame = new JFrame();
		
		frame.setSize(width, height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		
		frame.setTitle("Maze");
		
//		frame.setResizable(false);
		
		canvas = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(Color.BLACK);
				super.paint(g);
				int currX=0;
				int currY=0;
				for(int i=0; i< maze.length; i++) {
					currX =0;
					for(int j=0; j<maze[0].length; j++) {
						if(maze[i][j]) {
							//space
							g.drawRect(currX, currY, tileWidth, tileHeight);
						}else {
							//wall
							g.fillRect(currX, currY, tileWidth, tileHeight);
						}
						currX += tileWidth;
						
					}
					currY += tileHeight;
				}
			}
		};
		
		canvas.setPreferredSize(new Dimension(650,650));
		
		frame.add(canvas);
		
		frame.setVisible(true);
	}	
	
	public void update(boolean[][] m) {
		maze = m;
		canvas.repaint();
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException{
		// tests this class
		boolean[][] maze = new boolean[10][10];
		
		new DrawMaze(maze);
	}

}
