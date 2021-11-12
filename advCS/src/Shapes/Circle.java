package Shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {

	public Circle(Integer x, Integer y, Integer w, Integer h, Color c) {
		super(x, y, w, h, c);
	}

	@Override
	public Shape copy() throws CloneNotSupportedException {
		return (Shape) this.clone();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(getX(), getY(), width, height);	
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resize(int a, int b, int w, int h) {
		setX(a);
		setY(b);
		width = w;
		height = h;
	}
}