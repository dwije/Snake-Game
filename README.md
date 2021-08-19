# snake-game
The popular Snake game coded in Java

This game was made using basic Java Swing components like JFrames, JPanels, JButtons, etc.
The driving class of the game is the Board class, which is where the animation and game loop calculations occur.
The Board class extends the JPanel class and implements ActionListener and KeyListener, using the Timer class to draw every frame.
The Snake class sets up the JFrame, which is the window where the game takes place.
It calls the Board class to establish the game panel and the scoreboard panel at the top.
The repaint method in the Board class is called in every frame to draw and update the game board as the snake moves.

Once the game is run, the game window opens with the snake located in the top left corner, awaiting user input.
The user can use the arrow keys to control the snake's direction. Key inputs are managed in the Board class using KeyListener.
Food for the snake is spawned at a random location everytime the current food piece is eaten.

There are multiple types of food:
  Green food - worth 1 point, most common, increases snake length by 1
  Yellow food - worth 2 points, rare, increases snake length by 2
  Red food - worth 3 points, very rare, increases snake length by 3

There is also an invincibility powerup, which essentially removes the collision check to prevent the snake from dying.
The invincibility "buff" occurs as a white food piece which randomly spawns on the board and remains for about 10 seconds.
If it is eaten within 10 seconds, the snake turns white and gains invincibility.
If it is not eaten within 10 seconds, it simply disappears.
The buff will not spawn if it is already on the board, or if the snake is currently invincible.
When invincible, the snake will not die if it collides with itself.
This effect lasts for about 20 seconds. A white bubble occurs on the scoreboard to indicate the buff, along with a timer.
Once the timer runs out, the buff is removed, and the snake goes back to normal.

The scoareboard at the top keeps track of the users score, starting from 0.
Everytime a piece of food is eaten, the score increases depending on the type of food.

The game ends when the snake collides with itself. The snake can pass through the walls without dying; it simply appears from the opposite wall.
Once the game ends, gameplay is paused, and a small window pops up containing the users final score.
The pop-up also contains two buttons, "Play again" and "Exit".
