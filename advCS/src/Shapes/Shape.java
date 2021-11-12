package Shapes;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {

	private int x;
	private int y;
	protected int width;
	protected int height;
	protected Color c;
	protected String type;
	
	public Shape(Integer x,Integer y, Integer w, Integer h, Color c) {
		this.setX(x); this.setY(y);
		width = w; height = h;
		this.c = c;
	}
	
	public void move(int x1, int y1, int x2, int y2) {
		setX(x2-x1); setY(y2-y1);
	}
	
	public abstract Shape copy() throws CloneNotSupportedException;
	public abstract void draw(Graphics g);
	public abstract boolean isOn(int x, int y);
	public abstract void resize(int x1, int y1, int x2, int y2);

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
}
