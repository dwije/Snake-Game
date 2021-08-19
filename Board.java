package snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, ActionListener {
	
	private final int boardXLength = 1000;		// MUST BE A MULTIPLE OF 25
	private final int boardYLength = 800;		// MUST BE A MULTIPLE OF 25
	private int[] x = new int[1280];
	private int[] y = new int[1280];
	private int xFood;
	private int yFood;
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	private Timer timer;
	private int delay = 75;
	private boolean gameStarted = false;
	private int snakeLength;
	private int score = 0;
	private Font font = new Font("Dialog", Font.PLAIN, 40);
	int foodType;
	int invincibilityBuffX;
	int invincibilityBuffY;
	int invincibilityBuffDuration = 0;
	boolean invincibilityFoodActive = false;
	int invincibilityFoodDuration = 0;
	
	JFrame gameEndFrame = new JFrame("Game Over");
	JPanel gameEndPanel = new JPanel();
	JLabel gameOverLabel = new JLabel("Game Over");
	JLabel scoreLabel = new JLabel("Score:");
	JLabel scoreLabelNum = new JLabel(Integer.toString(this.score));
	JButton playAgainButton = new JButton("Play Again");
	JButton exitButton = new JButton("Exit");
	
	
	public Board() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		spawnFood();
		timer = new Timer(delay, this);
		timer.start();
	}
	
	int getScore() {
		return score;
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(0, 0, boardXLength, 165);
		if(invincibilityBuffDuration > 0) {
			g.setColor(Color.white);
			g.fillOval(500, 60, 25, 25);
			g.setColor(Color.black);
			g.setFont(font.deriveFont(20f));
			double durationInSeconds = invincibilityBuffDuration*0.075;
			g.drawString(Integer.toString(invincibilityBuffDuration), 540, 60);
		}
		g.setColor(Color.black);
		g.fillRect(0, 165, boardXLength, boardYLength);
		if (gameStarted == false) {
			x[0] = 50;   //Starting Position
			x[1] = 25;
			x[2] = 0;
			y[0] = 165;
			y[1] = 165;
			y[2] = 165;
			right = true;
			snakeLength = 3;
		}
		for(int i=0;i < snakeLength;i++) {
			if (i == 0) {
				g.setColor(Color.blue);
				g.fillOval(x[0], y[0], 25, 25);
			} else {
				if(invincibilityBuffDuration > 0) {
					g.setColor(Color.white);
				} else {
					g.setColor(Color.cyan);
				}
				g.fillOval(x[i], y[i], 25, 25);
			}
		}
		if(foodType == 1) {
			g.setColor(Color.green);
			g.fillOval(xFood, yFood, 25, 25);
		} else if(foodType == 2) {
			g.setColor(Color.yellow);
			g.fillOval(xFood, yFood, 25, 25);
		} else {
			g.setColor(Color.red);
			g.fillOval(xFood, yFood, 25, 25);
		}
		if(invincibilityFoodActive) {
			g.setColor(Color.white);
			g.fillOval(invincibilityBuffX, invincibilityBuffY, 25, 25);
		}
		g.setColor(Color.blue);
		g.setFont(font);
		g.drawString("Score = ", 20, 80);
		g.drawString(Integer.toString(score), 175, 80);
	}
	
	private void spawnFood() {
		xFood = (int)(Math.random()*40)*25;
		yFood = 165+(int)(Math.random()*32)*25;
		int foodTypeRand = (int)(Math.random()*100)+1;		//generates random number between 1-100
		if(foodTypeRand <= 90) {
			foodType = 1;
		} else if(foodTypeRand <= 99) {
			foodType = 2;
		} else {
			foodType = 3;
		}
		int invincibilityBuffRand = (int)(Math.random()*100)+1;		//generates random number between 1-100
		if(invincibilityBuffRand <= 30 && invincibilityBuffDuration == 0 && !invincibilityFoodActive) {
			do {
				invincibilityBuffX = (int)(Math.random()*40)*25;
				invincibilityBuffY = 165+(int)(Math.random()*32)*25;
				invincibilityFoodActive = true;
				invincibilityFoodDuration = 133;	//approximately 10 seconds
			} while (invincibilityBuffX == xFood && invincibilityBuffY == yFood);	//ensures food and buff don't spawn in same spot
		}
	}
	
	private boolean foodEaten() {
		if (x[0] == xFood && y[0] == yFood) {
			return true;
		} else return false;
	}
	
	private boolean invincibilityBuffEaten() {
		if (x[0] == invincibilityBuffX && y[0] == invincibilityBuffY) {
			return true;
		} else return false;
	}
	
	private void checkCollision() {
		for (int i=1;i < snakeLength;i++) {
			if (x[0] == x[i] && y[0] == y[i]) {
				gameOver();
			}
		}
	}
	
	private void gameOver() {
		timer.stop();
		
		scoreLabelNum.setText(Integer.toString(score));
		playAgainButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		gameEndFrame.setBounds(0, 0, 400, 250);
		gameEndFrame.setResizable(false);
		gameEndFrame.setLocationRelativeTo(null);
		gameEndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameEndPanel.setLayout(null);
		gameEndFrame.add(gameEndPanel);
		
		gameOverLabel.setForeground(Color.red);
		gameOverLabel.setFont(gameOverLabel.getFont().deriveFont(20f));
		gameOverLabel.setBounds(140, 20, 200, 25);
		scoreLabel.setForeground(Color.black);
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(18f));
		scoreLabel.setBounds(160, 60, 200, 25);
		scoreLabelNum.setForeground(Color.black);
		scoreLabelNum.setFont(scoreLabelNum.getFont().deriveFont(18f));
		scoreLabelNum.setBounds(220, 60, 200, 25);  //x value = [x value of scoreLabel]+60
		playAgainButton.setBounds(90, 120, 100, 25);
		exitButton.setBounds(210, 120, 100, 25);
		
		gameEndPanel.add(gameOverLabel);
		gameEndPanel.add(scoreLabel);
		gameEndPanel.add(scoreLabelNum);
		gameEndPanel.add(playAgainButton);
		gameEndPanel.add(exitButton);
		
		gameEndFrame.setVisible(true);		
	}
	
	private void restartGame() {
		gameStarted = false;
		score = 0;
		for(int i=3; i < 1280; i++) {
			x[i] = 0;
			y[i] = 0;
		}
		repaint();
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//timer.start();
		if(arg0.getSource() == timer) {
			if (foodEaten()) {
				if(foodType == 1) {
					snakeLength++;
					score++;
				} else if(foodType == 2) {
					snakeLength += 2;
					score += 2;
				} else {
					snakeLength += 3;
					score += 3;
				}
				spawnFood();
			}
			if(invincibilityFoodDuration > 0) {
				invincibilityFoodDuration--;
				if(invincibilityFoodDuration == 0) {
					invincibilityFoodActive = false;
				}
			}
			if (invincibilityBuffDuration > 0) {
				invincibilityBuffDuration--;
			}
			if (invincibilityBuffEaten() && invincibilityFoodActive) {
				invincibilityFoodActive = false;
				invincibilityFoodDuration = 0;
				invincibilityBuffDuration = 267;	//approximately 20 seconds
			}
			for (int i=snakeLength-1;i > 0;i--) {
				x[i] = x[i-1];
				y[i] = y[i-1];
			}
			if(right) {
				x[0] += 25;
				if (x[0] > boardXLength-25) {
					x[0] = 0;
				}
			}
			if(left) {
				x[0] -= 25;
				if (x[0] < 0) {
					x[0] = boardXLength-25;
				}
			}
			if(up) {
				y[0] -= 25;
				if (y[0] < 165) {
					y[0] = 165+boardYLength-25;
				}
			}
			if(down) {
				y[0] += 25;
				if (y[0] > 165+boardYLength-25) {
					y[0] = 165;
				}
			}
			if(invincibilityBuffDuration == 0) {
				checkCollision();
			}
			repaint();
		}
		else if(arg0.getSource() == playAgainButton) {
			gameEndFrame.dispose();
			restartGame();
		}
		else if(arg0.getSource() == exitButton) {
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			gameStarted = true;
			if (!left) {
				right = true;
				left = false;
				up = false;
				down = false;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			gameStarted = true;
			if (!right) {
				right = false;
				left = true;
				up = false;
				down = false;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			gameStarted = true;
			if (!down) {
				right = false;
				left = false;
				up = true;
				down = false;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			gameStarted = true;
			if (!up) {
				right = false;
				left = false;
				up = false;
				down = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}