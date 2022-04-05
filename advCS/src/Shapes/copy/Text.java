package Shapes.copy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Text extends Shape {
	
	public Text(Integer x, Integer y, Integer w, Integer h, Color c, String s) {
		super(x, y, w, h, c, s);
	}

	@Override
	public Shape copy() throws CloneNotSupportedException {
		return (Shape) this.clone();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.drawString(content, x, y);
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
		if(x>=this.getX() && x <= this.getX()+ width && y>=this.getY() && y<= this.getY() + height) {
			this.isSelected = true;
//			System.out.print(isSelected);
		}
		return false;
	}
}
