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
import javax.swing.*;
import Shapes.*;
import Shapes.Rectangle;
import Shapes.Shape;

public class GraphicsEditor {
	
	private static ArrayList<Shape> shapes;
	
	private final int width = 1000, height = 667;
	
	private static JFrame frame;
	
	private static JPanel container;

	private static JPanel settings;
	
	private static JButton save;
	private static JLabel lineWidth;
	private static JTextField  lineWidthIp;
	private static JButton colorPicker;
	private static JLabel  textSize;
	private static JTextField  textSizeIp;
	private static JButton delete;
	private static JButton move;
	private static JButton forward;
	private static JButton back;
	
	private static JPanel bottom;

	private static JPanel tools;
	
	private static JButton rectangle;
	private static JButton line;
	private static JButton circle;
	private static JButton text;
	private static JButton pen;

	private static JPanel canvas;
	
	private static Constructor currTool;
	
	private static Class[] shapeParameters = {Integer.class, Integer.class, Integer.class, Integer.class, Color.class};
	
	private static Color currColor;
	
	private static Shape currShape;
	
	private static int currRootX, currRootY;
	
	public GraphicsEditor() throws NoSuchMethodException, SecurityException {
		currTool = Rectangle.class.getConstructor(shapeParameters);
		currColor = Color.BLACK;
		
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
		
		colorPicker = new JButton("Color Picker");
		
		lineWidth = new JLabel();
		lineWidth.setText("Line Width:");
		
		lineWidthIp = new JTextField();
		lineWidthIp.setText("");
		lineWidthIp.setEditable(true);
		
		textSize = new JLabel();
		textSize.setText("Text Size:");
		
		textSizeIp = new JTextField();
		textSizeIp.setText("");
		textSizeIp.setEditable(true);
		
		delete = new JButton("Delete");
		move = new JButton("Move");
		forward = new JButton("Forward");
		back = new JButton("Back");
		
		bottom = new JPanel();
		
		tools = new JPanel();
		
		rectangle = new JButton("Rectangle");
		line = new JButton("Line");
		circle = new JButton("Circle");
		text = new JButton("Text");
		pen = new JButton("Pen");
		
		canvas = new JPanel() {
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
		settings.add(colorPicker);
		settings.add(lineWidth);
		settings.add(lineWidthIp);
		settings.add(textSize);
		settings.add(textSizeIp);
		settings.add(delete);
		settings.add(move);
		settings.add(forward);
		settings.add(back);
		settings.add(Box.createHorizontalGlue());
		
		container.add(settings);
		
		tools.setLayout(new BoxLayout(tools, BoxLayout.Y_AXIS));
		
		tools.add(rectangle);
		tools.add(line);
		tools.add(circle);
		tools.add(text);
		tools.add(pen);
		
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.add(tools);
		bottom.add(canvas);
		
		container.add(bottom);
		
		frame.setMinimumSize(new Dimension(width,height));
		
		frame.add(container);
		
		//create listeners
		rectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					currTool = Rectangle.class.getConstructor(shapeParameters);
				} catch (NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
				System.out.println(currTool);
			}	

		
		});
		circle.addActionListener(new ActionListener() {
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
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				currRootX = e.getX();
				currRootY = e.getY();
				try {
					currShape = (Shape) currTool.newInstance(currRootX,currRootY,0,0,currColor);
					shapes.add(currShape);
					System.out.println("draw");
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e1) {
					e1.printStackTrace();
				}
				frame.getContentPane().repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		canvas.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				int x1 = currRootX;
				int y1 = currRootY;
				int x2 = e.getX();
				int y2 = e.getY();
				
				
				if(x2>=x1) {
					if(y2>=y1) {
						currShape.resize(x1, y1, x2-x1, y2-y1);
						System.out.println("4");
					}else {
						currShape.resize(x1, y2, x2-x1, y1-y2);
						System.out.println("1");
					}
				}else {
					if(y2>=y1) {
						currShape.resize(x2, y1, x1-x2, y2-y1);
						System.out.println("3");
					}else {
						currShape.resize(x2, y2, x1-x2, y1-y2);
						System.out.println("2");
					}
				}
				
				System.out.println(x2+ " " + y2);
				frame.getContentPane().repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException{
		new GraphicsEditor();
	}
}
