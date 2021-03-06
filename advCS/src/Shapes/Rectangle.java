package Shapes;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape {

	public Rectangle(Integer x, Integer y, Integer w, Integer h, Color c, String s) {
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
		g.fillRect(getX(), getY(), width, height);
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
		if(x>=this.getX() && x <= this.getX()+ width && y>=this.getY() && y<= this.getY() + height) {
			this.isSelected = true;
//			System.out.print(isSelected);
		}
		return false;
	}
}