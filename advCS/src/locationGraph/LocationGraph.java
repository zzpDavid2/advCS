package locationGraph;

import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.HashSet;

public class LocationGraph extends Graph<String, int[], Double>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6468289037106462840L;
	
	public LocationGraph() {
		vertices = new HashMap<String, Vertex<String, int[]>>();
		edges = new HashSet<Edge<Double>>();
	}

	public void drawEdges(Graphics2D g2d, Stroke s, Color lineColor) {
		 //draw edges
	    g2d.setStroke(s);
		HashSet<Edge<Double>> edges = this.edges;
		for(Edge<Double> e : edges) {
			//draws the edge
			g2d.setColor(lineColor);
			Vertex<String, int[]> A = e.getA();
			Vertex<String, int[]> B = e.getB();
			g2d.drawLine(A.data[0], A.data[1], B.data[0], B.data[1]);
			
		}
	}
	
	public void drawVertices(Graphics2D g2d, int dotSize, Color vertexColor, int fontSize, Color textColor) {
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
		}
	}
	
	public void outlineVertex(Graphics2D g2d, Vertex<String, int[]> v, Stroke s, int dotSize, Color lineColor) {
	    g2d.setStroke(s);
		g2d.setColor(lineColor);
		g2d.drawOval(v.data[0] - dotSize/2, v.data[1] - dotSize/2, dotSize, dotSize);
	}
	
	public void highlightEdge(Graphics2D g2d, Edge<Double> e,  Stroke s, Color lineColor) {
		// draw edges
	    g2d.setStroke(s);
		HashSet<Edge<Double>> edges = this.edges;
		// draws the edge
		g2d.setColor(lineColor);
		Vertex<String, int[]> A = e.getA();
		Vertex<String, int[]> B = e.getB();
		g2d.drawLine(A.data[0], A.data[1], B.data[0], B.data[1]);
		
	}
}
