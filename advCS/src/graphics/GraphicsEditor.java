package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	
	private static Class<?> currTool;
	
	public GraphicsEditor() {		
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
		
		canvas = new JPanel();
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
				currTool = Rectangle.class;
			}		
		
		});
		circle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currTool = Circle.class;
			}		
		
		});
		line.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currTool = Line.class;
			}		
		
		});
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				try {
					shapes.add((Shape) currTool.getConstructor(currTool).newInstance(x,y,40,40));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
				drawShapes(canvas.getGraphics());
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
		
		frame.setVisible(true);
	}
	
	public static void drawShapes(Graphics g) {
		for(Shape s : shapes) {
			s.draw(g);
		}
	}
	
	public static void main(String[] args){
		new GraphicsEditor();
	}
}
