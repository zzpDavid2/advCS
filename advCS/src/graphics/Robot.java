package graphics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Robot {
	
	private final int width = 600, height = 600;
	
	private static JFrame frame;
	
	private static int x,y;
	
	public Robot() {
		frame = new JFrame();
		
		frame.setSize(width, height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(Color.RED);
				g.fillRect(x,y,50,50);
			}
		};
		
		canvas.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				char ip = e.getKeyChar();
				if(ip == 'w') {
					y-=10;
				}else if(ip == 's') {
					y+=10;
				}else if(ip == 'a') {
					x-=10;
				}else if(ip == 'd') {
					x+=10;
				}
				frame.getContentPane().repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {}		
		
		});
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				frame.getContentPane().repaint();
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
		
		frame.add(canvas);
		
		frame.setVisible(true);
		
		canvas.requestFocusInWindow();
	}
	
	public static void main(String[] args) {
		new Robot();
	}
}
