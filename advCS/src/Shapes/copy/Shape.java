package Shapes.copy;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Color c;
	protected String content;
	protected boolean isSelected;
	
	
	public Shape(Integer x,Integer y, Integer w, Integer h, Color c, String s) {
		this.setX(x); this.setY(y);
		width = w; height = h;
		this.c = c;
		this.content = s;
	}
	
	public void move(int x1, int y1, int x2, int y2) {
		setX(x2-x1); setY(y2-y1);
	}
	
	public abstract Shape copy() throws CloneNotSupportedException;
	public abstract void draw(Graphics g);
	public abstract void resize(int x1, int y1, int x2, int y2);
	public abstract boolean Select(int x, int y);
	
	public void deselect() {
		isSelected = false;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

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
	
	public void setColor(Color color) {
		this.c = color;
	}
}


