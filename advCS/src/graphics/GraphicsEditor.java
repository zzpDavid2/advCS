//David Zhou's Graphics Editor
//This needs my custom Shapes package
//Move, pen, and line width are just filler buttons and are not implemented
//Choose the select function on the left and select a shape to:
//change its color, move it to front/ back, delete it

package graphics;

import java.awt.*;
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
import java.util.LinkedList;

import javax.swing.*;

import Shapes.*;
import Shapes.Rectangle;
import Shapes.Shape;

public class GraphicsEditor {
	
	private ArrayList<Shape> shapes; // all the shapes
	
	private final int width = 1000, height = 667;
	
	private JFrame frame;
	
	private JPanel container;

	private JPanel settings;
	
	private JButton save;
	private JLabel lineWidth;
	private JTextField  lineWidthIp;
	private JButton colorChooser;
	private JLabel  textContent;
	private JTextField  textIp;
	private JButton delete;
	private JButton move;
	private JButton front;
	private JButton back;
	
	private JPanel bottom;

	private JPanel tools;
	
	private JButton select;
	private JButton rectangle;
	private JButton line;
	private JButton oval;
	private JButton text;
	private JButton pen;

	private JPanel canvas;
	
	private Constructor currTool;
	
	// parameters of the shape constructor
	private Class[] shapeParameters = {Integer.class, Integer.class, Integer.class, Integer.class, Color.class, String.class}; 
	
	private Color currColor;
	
	private Shape currShape;
	
	private int currRootX, currRootY; // temp variables that record the location where mouse press happened
	
	public GraphicsEditor() throws NoSuchMethodException, SecurityException {
		//set default tool and color
		currTool = Rectangle.class.getConstructor(shapeParameters);
		currColor = Color.RED;
		
		// setup graphics
		shapes = new ArrayList<Shape>();
	   	    
		frame = new JFrame();
		
		frame.setSize(width, height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		
		frame.setTitle("Graphics Editor");
		
		container = new JPanel();
		
		settings = new JPanel();
		settings.setPreferredSize(new Dimension(width,20));
		save = new JButton("Save");
		
		colorChooser = new JButton("Color Chooser");
		
		lineWidth = new JLabel();
		lineWidth.setText("Line Width:");
		
		lineWidthIp = new JTextField();
		lineWidthIp.setText("");
		lineWidthIp.setEditable(true);
		
		textContent = new JLabel();
		textContent.setText("Text Content:");
		
		textIp = new JTextField();
		textIp.setText("");
		textIp.setEditable(true);
		
		delete = new JButton("Delete");
		move = new JButton("Move");
		front = new JButton("Front");
		back = new JButton("Back");
		
		bottom = new JPanel();
		
		tools = new JPanel();
		
		select = new JButton("Select");
		rectangle = new JButton("Rectangle");
		line = new JButton("Line");
		oval = new JButton("Oval");
		text = new JButton("Text");
		pen = new JButton("Pen");
		
		canvas = new JPanel() {
			//draw all the shapes on canvas
			public void paint(Graphics g) {
				super.paint(g);
				for(Shape s : shapes) {
					s.draw(g);
				}
			}
		};
		
		canvas.setBackground(Color.WHITE);
		canvas.setOpaque(true);
		
		canvas.setPreferredSize(new Dimension(1920,1080));
		
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		settings.setLayout(new BoxLayout(settings, BoxLayout.X_AXIS));
		
		settings.add(save);
		settings.add(colorChooser);
		settings.add(lineWidth);
		settings.add(lineWidthIp);
		settings.add(textContent);
		settings.add(textIp);
		settings.add(delete);
		settings.add(move);
		settings.add(front);
		settings.add(back);
		settings.add(Box.createHorizontalGlue());
		
		container.add(settings);
		
		tools.setLayout(new BoxLayout(tools, BoxLayout.Y_AXIS));
		
		tools.add(select);
		tools.add(rectangle);
		tools.add(line);
		tools.add(oval);
		tools.add(text);
		tools.add(pen);
		
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.add(tools);
		bottom.add(canvas);
		
		container.add(bottom);
		
		frame.setMinimumSize(new Dimension(width,height));
		
		frame.add(container);
		
		//create listeners
		colorChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//creates a color chooser and user chooses a color
				currColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
				
				//updates selected shape to new color
				for(int i=0; i< shapes.size(); i++) {
					if(shapes.get(i).isSelected()) {
						shapes.get(i).setColor(currColor);
					}
				}
				frame.getContentPane().repaint();
			}		
		
		});
		
		front.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//move selected shape to front
				for(int i=0; i< shapes.size(); i++) {
					if(shapes.get(i).isSelected()) {
						shapes.add(shapes.get(i));
						shapes.remove(i);
					}
				}
				frame.getContentPane().repaint();
			}		
		
		});
		
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// moves selected shape to back
				for(int i=0; i< shapes.size(); i++) {
					if(shapes.get(i).isSelected()) {
						Shape temp = shapes.get(i);
						shapes.remove(i);
						shapes.add(0, temp);
					}
				}
				frame.getContentPane().repaint();
			}		
		
		});
		
		// null in currTool indicates its select mode
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currTool = null;
//				System.out.println(currTool);
			}	

		});
		rectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					currTool = Rectangle.class.getConstructor(shapeParameters);
				} catch (NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
//				System.out.println(currTool);
			}	

		
		});
		oval.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					currTool = Circle.class.getConstructor(shapeParameters);
				} catch (NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
			}		
		
		});
		line.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					currTool = Line.class.getConstructor(shapeParameters);
				} catch (NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
			}		
		
		});
		
		text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					currTool = Text.class.getConstructor(shapeParameters);
				} catch (NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
			}		
		
		});
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				//selecting clicked item
				if(currTool == null) { 
					for(Shape s : shapes) {
						s.deselect();
					}
//					System.out.println(shapes);
					int x = e.getX();
					int y = e.getY();
					for(Shape s : shapes) {
						s.Select(x, y);
					}
//					System.out.println(shapes);
					frame.getContentPane().repaint();
					return;
				}
				
				//creating a new shape
				currRootX = e.getX();
				currRootY = e.getY();
				
				try {
					currShape = (Shape) currTool.newInstance(currRootX,currRootY,0,0,currColor, textIp.getText());
//					System.out.println(textIp.getText());
					shapes.add(currShape);
//					System.out.println("draw");
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e1) {
					e1.printStackTrace();
				}
				
				frame.getContentPane().repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
		
		canvas.addMouseMotionListener(new MouseMotionListener(){
			
			@Override
			public void mouseDragged(MouseEvent e) {
				//determines the size of a shape
				if(currTool == null) {
					return;
				}
				
				int x1 = currRootX;
				int y1 = currRootY;
				int x2 = e.getX();
				int y2 = e.getY();
				
				//allows dragging in all directions
				if(x2>=x1) {
					if(y2>=y1) {
						currShape.resize(x1, y1, x2-x1, y2-y1);
//						System.out.println("4");
					}else {
						currShape.resize(x1, y2, x2-x1, y1-y2);
//						System.out.println("1");
					}
				}else {
					if(y2>=y1) {
						currShape.resize(x2, y1, x1-x2, y2-y1);
//						System.out.println("3");
					}else {
						currShape.resize(x2, y2, x1-x2, y1-y2);
//						System.out.println("2");
					}
				}
				
//				System.out.println(x2+ " " + y2);
				frame.getContentPane().repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		container.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				int ip = e.getKeyCode();
				if(ip == KeyEvent.VK_DELETE || ip == KeyEvent.VK_BACK_SPACE) {
					deleteSelected();
					System.out.println("delete");
				}
				frame.getContentPane().repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {}		
		
		});
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelected();
			}		
		
		});
		
		frame.setVisible(true);
		container.requestFocusInWindow();
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException{
		new GraphicsEditor();
	}
	
	//delete selected shapes
	private void deleteSelected() {
		for(int i=0; i< shapes.size(); i++) {
			if(shapes.get(i).isSelected()) {
				shapes.remove(i);
			}
		}
		frame.getContentPane().repaint();
	}
}
