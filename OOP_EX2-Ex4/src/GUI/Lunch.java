package GUI;

import javax.swing.JFrame;


public class Lunch 
{
	public static void main(String[] args) {
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
		frame.setSize(frame.image.getWidth(),frame.image.getHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}