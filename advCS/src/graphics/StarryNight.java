package graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// we will extend JPanel (a built-in Java class). A panel will 
// have the graphics capabilities we want.
public class StarryNight {
	
	// constants that are predefined and won't change 
	// don't use 'magic numbers' in your code!!!

	
	// the width/height of our window - note I set this 
	// final bc I didn't allow the window size to change
	private final int width = 909, height = 720;
	
	// our images, will be assigned later.
	
	private Image img;
	
	private static JFrame frame;
 
	// this is where we do the graphics initializations
	public StarryNight() throws InterruptedException {
		
		// the frame holds the panel. A frame is simply a container,
		// it does nothing but hold panels and other graphics tools
		frame = new JFrame();
		
		// set the window size - notice, no magic numbers!
		frame.setSize(width, height);
		
		// this ends the program when the close button is pressed
		// probably always a good idea to use this
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add our customized panel to the container
		frame.add(new JPanel() {
			// defines how to paint our panel - this is called 
			// note that I never call this directly.
			// If I want to update my original graphics display, I call repaint()
			public void paint(Graphics g) {
			
				// painted at location 0, 0 and scaled to size width x height
				g.drawImage(img, 0, 0, width, height, this);	
			}
		});
		
		
		// this line centers the window upon startup
		frame.setLocationRelativeTo(null);
		
		// decide whether the user can resize the window - 
		// sometimes this is good, sometimes bad.
		// if you choose to set this true, make sure to be 
		// careful with your height/width variables!!
		frame.setResizable(false);
		
		// we need to tell the computer to make your frame and 
		// its contents visible (I don't know why this is automatically
		// set to false...)
		frame.setVisible(true);
		
		// decide whether you will need focus in your program.
		// focus is the ability for the program to pay attention 
		// to just one component - for example, if you have multiple 
		// text input boxes, we need to know which box to focus on
		// at all times
		frame.setFocusable(true);
		
		// load our image and save it for future use
		img = Toolkit.getDefaultToolkit().getImage("Starry Night.png");
		
		
		// get our functionality going (if we have any)
		run(0,0,0);
	}

	// This is what we want the code to do as the panel is open.
	public static void run(int r, int g, int b) throws InterruptedException {
		frame.getContentPane().setBackground(new Color(r,g,b));

		// note - I don't have anything besides graphics setup in
		// this code, so my program won't actually 'do' anything.
		// If I wanted to 'do something', this is where I would do that
	}
	
	// very simple main method - create our graphics object
	public static void main(String[] args) throws InterruptedException {
		new StarryNight(); 
		while(true) {
			for(int i=0; i<256; i++) {
				for(int j=0; j< 256; j++) {
					for(int k=0; k<256; k++) {
						StarryNight.run(i,j,k);
					}
				}
			}
			for(int i=255; i>=0; i--) {
				for(int j=255; j>=0; j--) {
					for(int k=255; k>=0; k--) {
						StarryNight.run(i,j,k);
					}
				}
			}
		}
	}
}


