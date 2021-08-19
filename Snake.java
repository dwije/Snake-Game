package snakegame;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Snake {
	JFrame frame;
	Board panel;
	
	Snake() {
		frame = new JFrame("Snake Game");
		panel = new Board();
		frame.setBounds(0, 0, 1006, 1000); //Original values: (0, 0, 1006, 835), depends on boardXLength/boardYLength
		//frame.setBackground(Color.orange);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
		frame.add(panel);
		//scoreLabel = new JLabel("Score = ");
		//scoreLabel.setBounds(20, 20, 120, 25);
		//scoreLabel.setFont(scoreLabel.getFont().deriveFont(30f));
		//scoreLabel.setForeground(Color.blue);
		//panel.add(scoreLabel);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Snake snake = new Snake();
	}
}