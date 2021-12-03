package Shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	int rootX, rootY;
	
	public Line(Integer x, Integer y, Integer w, Integer h, Color c, String s) {
		super(x, y, x, y, c, s);
		rootX=x;
		rootY=y;
	}

	@Override
	public Shape copy() throws CloneNotSupportedException {
		return (Shape) this.clone();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.drawLine(getX(), getY(), width, height);	
	}

	@Override
	public void resize(int x, int y, int h, int w) {
		setX(rootX);
		setY(rootY);
		if(x+h == rootX) {
			width = x;
		}else {
			width = x+h;
		}
		
		if(y+w == rootY) {
			height = y;
		}else {
			height = y+w;
		}
	}

	@Override
	public boolean Select(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}