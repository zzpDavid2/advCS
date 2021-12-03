package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.*;

public class Circle extends Shape {

	public Circle(Integer x, Integer y, Integer w, Integer h, Color c, String s) {
		super(x, y, w, h, c, s);
	}

	@Override
	public Shape copy() throws CloneNotSupportedException {
		return (Shape) this.clone();
	}

	@Override
	public void draw(Graphics g) {
		//draws the shape
		g.setColor(c);
		g.fillOval(getX(), getY(), width, height);
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(3));
	    
	    //show a black outline when selected
		if(isSelected) {
		    g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.BLACK);
			g2.drawRect(getX(), getY(), width, height);
		}
	}


	@Override
	public void resize(int a, int b, int w, int h) {
		setX(a);
		setY(b);
		width = w;
		height = h;
	}

	@Override
	public boolean Select(int x, int y) {
		//determines whether this shape is clicked
		int a1 = width/2;
		int a2 = height/2;
		int X = x-getX()-a1;
		int Y = y-getY()-a2;
		double r = Math.pow(X, 2)/Math.pow(a1, 2) + Math.pow(Y, 2)/Math.pow(a2, 2);
//		System.out.println(X + " " + Y);
//		System.out.println(a1 + " " + a2);
//		System.out.println(r);
		
		if(r<=1) {
			this.isSelected = true;
//			System.out.print(isSelected);
		}
		return false;
	}
}