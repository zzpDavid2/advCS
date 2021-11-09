package graphics;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import graphics.Shape;

public class GraphicsEditor {
	
	private final int width = 1000, height = 667;
	
	private static JFrame frame;
	
	private static JPanel container;

	private static JPanel settings;
	
	private static JButton save;
	private static JTextArea lineWidth;
	private static JTextArea  lineWidthIp;
	private static JButton colorPicker;
	private static JTextArea  textSize;
	private static JTextArea  textSizeIp;
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
	
	public GraphicsEditor() {
	   	    
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
		
		lineWidth = new JTextArea();
		lineWidth.setText("Line Width:");
		lineWidth.setEditable(false);
		lineWidth.setBackground(new Color(0,0,0,0));
		lineWidth.setPreferredSize(new Dimension(Short.MAX_VALUE,20));
		lineWidth.setMaximumSize(new Dimension(Short.MAX_VALUE, lineWidth.getPreferredSize().height));
		
		lineWidthIp = new JTextArea();
		lineWidthIp.setText("");
		lineWidthIp.setEditable(true);
		
		textSize = new JTextArea();
		textSize.setText("Text Size:");
		textSize.setEditable(false);
		textSize.setBackground(new Color(0,0,0,0));
		textSize.setPreferredSize(new Dimension(Short.MAX_VALUE,20));
		textSize.setMaximumSize(new Dimension(Short.MAX_VALUE, textSize.getPreferredSize().height));
		
		textSizeIp = new JTextArea();
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

		
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		new GraphicsEditor();
	}
}
