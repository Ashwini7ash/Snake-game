import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		//JFrame is the window in which our game
		JFrame obj = new JFrame();
		Gameplay gp = new Gameplay();
		
		obj.setBounds(10,10,905,700);
		obj.setBackground(Color.DARK_GRAY);
		
		//so that user donot change the dimensions
		obj.setResizable(false);
		
		//to actually see the frame that we created
		obj.setVisible(true);
		
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add object of gameplay to the object of mainframe
		obj.add(gp);
	}

}
