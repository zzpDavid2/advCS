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
import javax.swing.ImageIcon;
import java.awt.RenderingHints;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Shapes.Shape;
import util.Tuple;

public class MapRouter {

	private static LocationGraph graph; 

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
	private JButton save, load, newSave, setBackground;
	private JToggleButton connect;
	
	private HashSet<Edge<Double>> highlightEdges = new HashSet<Edge<Double>>();
	
	private File imageFile, saveFile;
	private ImageIcon imageIcon;
	
	private Vertex<String, int[]> selected = null;
	
	private Vertex<String, int[]> start, end;
	
	private boolean ranDijk = true;
	
	public MapRouter() {
		
		graph = new LocationGraph();
		
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
		setBackground = new JButton("Set Background");
		
		connect = new JToggleButton("Connect");
		
		buttons.add(newSave);
		buttons.add(save);
		buttons.add(load);
		buttons.add(setBackground);
		
		buttons.add(connect);
		
		canvas = new JPanel() {

			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
			    
				//draw the background image
				if(imageIcon != null) {
				    imageIcon.paintIcon(canvas, g2d, 0, 0);
				}

				graph.drawEdges(g2d, new BasicStroke(edgeStroke), lineColor);
				
				for(Edge<Double> e : highlightEdges) {
					graph.highlightEdge(g2d, e, new BasicStroke(edgeStroke), Color.RED);
				}
				
				graph.drawVertices(g2d, dotSize, vertexColor, fontSize, textColor);
				
				if(selected != null) {
					graph.outlineVertex(g2d, selected, new BasicStroke(selectStroke), dotSize, lineColor);
				}
				
				if(start != null && end != null && ranDijk == false) {
					if(ranDijk == false) {
						ArrayList<String> path = graph.dijkstrasAlgorithm(start.label, end.label);
						highlightEdges.clear();
						HashMap<String, Vertex<String, int[]>> verticies = graph.getVertices();
						for(int i=0; i<path.size()-1; i++) {
							Vertex<String, int[]> curr = verticies.get(path.get(i));
							Vertex<String, int[]> next = verticies.get(path.get(i+1));
							Edge<Double> e = graph.findEdge(curr,next);
							highlightEdges.add(e);
							graph.highlightEdge(g2d, e, new BasicStroke(selectStroke), Color.RED);
						}

						ranDijk = true;
					}
					
					for(Edge<Double> e : highlightEdges) {
						graph.highlightEdge(g2d, e, new BasicStroke(selectStroke), Color.RED);
					}
				}
			}
		};
		
		
		canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		JScrollPane scroll = new JScrollPane (canvas, 
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		container.add(buttons);
		container.add(scroll);
		
		frame.add(container);
		
		frame.setVisible(true);
		container.requestFocusInWindow();
				
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				saveFile = getSave();
				
				try {
			         FileOutputStream fileOut =
			         new FileOutputStream(saveFile);
			         ObjectOutputStream out = new ObjectOutputStream(fileOut);
					 Tuple<LocationGraph, ImageIcon> t = new Tuple<LocationGraph, ImageIcon>(graph, imageIcon);
			         out.writeObject(t);
					 out.close();
			         fileOut.close();
			         System.out.printf("Serialized data is saved in " + saveFile.getAbsolutePath());
			      } catch (IOException i) {
			         i.printStackTrace();
			      }
			}
			
		});
		
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				saveFile = getLoad();
				
				try {
					FileInputStream fileIn = new FileInputStream(saveFile);
					ObjectInputStream in = new ObjectInputStream(fileIn);
//					graph = (LocationGraph) in.readObject();
					Tuple<LocationGraph, ImageIcon> t = (Tuple<LocationGraph, ImageIcon>) in.readObject();
					graph = t.a;
					imageIcon = t.b;
					in.close();
					fileIn.close();
					} catch (IOException i) {
					i.printStackTrace();
				} catch (ClassNotFoundException c) {
					System.out.println("Employee class not found");
					c.printStackTrace();
				}
				
				frame.repaint();
			}
			
		});
		
		newSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				graph = new LocationGraph();
				
				imageIcon = null;

				frame.repaint();
			}
			
		});
		
		setBackground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				imageFile = getImage();
				if(imageFile != null) {
					try {
						BufferedImage image = ImageIO.read(imageFile);
						imageIcon = new ImageIcon(image);
						canvas.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Failed to load image.");
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "No image selected.");
				}
				
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
				}else if((selected != null && newSelected == null) || selected == newSelected) {
					// de-select when clicked on empty space
					selected = null;
				}else if(selected == null && newSelected != null) {
					// select a vertex when nothing is selected
					// and click on a vertex
					selected = newSelected;
				}else if(selected != null && newSelected != null) {
					if(connect.isSelected()) {
						// connect mode
						start = selected;
						end = newSelected;
						ranDijk = false;
						System.out.println(start + " " + end);
						System.out.println(selected + " " + newSelected);
						System.out.println(ranDijk);
					}else {
						// connect the two vertex when a vertex was selected
						double dis = calcDis(selected.data[0],selected.data[1], newSelected.data[0], newSelected.data[1]);
						graph.connect(selected.label, newSelected.label, dis);
						selected = null;
					}
	
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
	    JFileChooser fileChooser= new JFileChooser(".");
		fileChooser.setDialogTitle("Set Save Location");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Files", "txt", "ser");
		fileChooser.setFileFilter(filter);
		
		int returnValue = fileChooser.showDialog(frame, "Save");
	    if (returnValue == JFileChooser.APPROVE_OPTION) {
	        saveFile = fileChooser.getSelectedFile();
			System.out.println(saveFile.getAbsolutePath());
	        return saveFile;
	    }
		return null;
	}
	
	private File getLoad() {
	    JFileChooser fileChooser= new JFileChooser(".");
		fileChooser.setDialogTitle("Load Save File");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Files", "txt", "ser");
		fileChooser.setFileFilter(filter);
		
		int returnValue = fileChooser.showDialog(frame, "Load");
	    if (returnValue == JFileChooser.APPROVE_OPTION) {
	        saveFile = fileChooser.getSelectedFile();
			System.out.println(saveFile.getAbsolutePath());
	        return saveFile;
	    }
		return null;
	}
	
	
}
