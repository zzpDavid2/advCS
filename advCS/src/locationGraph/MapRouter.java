package locationGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Shapes.Shape;

public class MapRouter {

	private static Graph<String, int[], Double> graph; 

	public static void main(String args[]) throws IOException {
		
		MapRouter mapRouter = new MapRouter();
			
		}
	
	//GUI
	
	private final int width = 1000, height = 667;
	
	private final int dotSize = 14; // radius of point
	private final int fontSize = 14;
	private final int selectDis = 16;
	private final int selectStroke = 2;
	private final int edgeStroke = 3;
	private final Color vertexColor = Color.RED;
	private final Color textColor = Color.WHITE;
	private final Color lineColor = Color.BLACK;
	
	private JFrame frame;
	private JPanel container, buttons, canvas;
	private JButton save, load, newSave;
	
	private static File imageFile, saveFile;
	private static BufferedImage image;
	
	private static Vertex<String, int[]> selected = null;
	
	public MapRouter() {
		
		graph = new Graph<String, int[], Double>();
		
		// setup graphics  
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Map Router");
		
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		
		save = new JButton("Save");
		load = new JButton("Load");
		newSave = new JButton("New");
		
		buttons.add(newSave);
		buttons.add(save);
		buttons.add(load);
				
		canvas = new JPanel() {

			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
			    
				//draw the background image
			    g2d.drawImage(image, 0, 0, null);

				 //draw edges
			    g2d.setStroke(new BasicStroke(edgeStroke));
				HashSet<Edge<Double>> edges = graph.getEdges();
				for(Edge<Double> e : edges) {
					//draws the edge
					g2d.setColor(lineColor);
					Vertex<String, int[]> A = e.getA();
					Vertex<String, int[]> B = e.getB();
					g2d.drawLine(A.data[0], A.data[1], B.data[0], B.data[1]);
					
				}
			    
				//draw the vertices
				HashMap<String, Vertex<String, int[]>> vertices = graph.getVertices();
				for(Vertex<String, int[]> v : vertices.values()) {
					//draws the dot
					g2d.setColor(vertexColor);
					g2d.fillOval(v.data[0] - dotSize/2, v.data[1] - dotSize/2, dotSize, dotSize);
					
					//draws the text
					g2d.setColor(textColor);
					
					Font font = new Font("Arial", Font.PLAIN, fontSize);
		            g2d.setFont(font);
		            FontMetrics fm = g2d.getFontMetrics();
					
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					        RenderingHints.VALUE_ANTIALIAS_ON);

					g2d.drawString(v.label, v.data[0] - fm.stringWidth(v.label)/2 , v.data[1] + dotSize/2 + fm.getAscent());
					
//				    //show a black outline when selected
					if(selected == v) {
					    g2d.setStroke(new BasicStroke(selectStroke));
						g2d.setColor(lineColor);
						g2d.drawOval(v.data[0] - dotSize/2, v.data[1] - dotSize/2, dotSize, dotSize);
					}
				}
			}
		};
		
		canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		container.add(buttons);
		container.add(canvas);
		
		frame.add(container);
		
		frame.setVisible(true);
		container.requestFocusInWindow();
				
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		newSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imageFile = getImage();
				if(imageFile != null) {
					try {
						image = ImageIO.read(imageFile);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Failed to load image.");
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "No image selected.");
				}
				saveFile = getSave();
				
				frame.repaint();
			}
			
		});
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Vertex<String, int[]> newSelected = selectVertex(x,y);
				if(selected == null && newSelected == null) {
					//add a new vertex when nothing is selected
					// and clicked on empty space
					String label = JOptionPane.showInputDialog("Please input label for vertex:");
					if(label != null) graph.add(label, new int[] {e.getX(), e.getY()});
				}else if(selected != null && newSelected == null) {
					// de-select when clicked on empty space
					selected = null;
				}else if(selected == null && newSelected != null) {
					// select a vertex when nothing is selected
					// and click on a vertex
					selected = newSelected;
				}else if(selected != null && newSelected != null) {
					// connect the two vertex when a vertex was selected
					double dis = calcDis(selected.data[0],selected.data[1], newSelected.data[0], newSelected.data[1]);
					graph.connect(selected.label, newSelected.label, dis);
					selected = null;
				}
				
				frame.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
		
	}
	
	private Vertex<String, int[]> selectVertex(int x, int y) {
		HashMap<String, Vertex<String, int[]>> vertices = graph.getVertices();
		
		for(Vertex<String, int[]> e : vertices.values()) {
			if(calcDis(x, y, e.data[0], e.data[1]) <= selectDis) {
				System.out.println(e + " selected");
				return e;
			}
		}
		
		return null;
	}
	
	private Double calcDis(int x1, int y1, int x2, int y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dis = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return dis;
	}
	
	private File getImage() {
		JFileChooser imageChooser = new JFileChooser(".");
		imageChooser.setDialogTitle("Set Background Image");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpeg", "jpg", "png", "gif");
		imageChooser.setFileFilter(filter);
		
		int returnValue = imageChooser.showDialog(frame, "Select");
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			imageFile = imageChooser.getSelectedFile();
			System.out.println(imageFile.getAbsolutePath());
			return imageFile;
		}
		return null;
	}
	
	private File getSave() {
		return null;
		
	}
}
