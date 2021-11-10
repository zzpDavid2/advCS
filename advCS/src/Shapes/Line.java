package Shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {

	public Line(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	@Override
	public Shape copy() throws CloneNotSupportedException {
		return (Shape) this.clone();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(x, y, width, height);	
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resize(int a, int b, int w, int h) {
		x=a;
		y=b;
		width = w;
		height = h;
	}
}