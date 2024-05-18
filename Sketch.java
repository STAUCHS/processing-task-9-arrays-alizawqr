import processing.core.PApplet;

public class Sketch extends PApplet {

  // Related arrays for the (x, y) coordinates of the snowflakes
  float[] snowX = new float [42];
  float[] snowY = new float [42];
  boolean [] ballHideStatus = new boolean[42];
  int snowDiameter = 10;

  // Circle coordinates
  float circleX = 200;
  float circleY = 200;

  // Declare number of player lives 
  int playerLives = 3;

  public void settings() {
    size(400, 400);
  }

  public void setup() {
    // Background - black
    background(0);

    // Generate random X and y values for snowflakes 
    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(height);
      ballHideStatus [i] = false;
    }
  }

  public void draw() {
    // Change background to white when all lives are lost
    if (playerLives == 0) {
      background(255, 255, 255);
    }
    else {
      background(0);

      // Draw snow
      snow();

      // Draw circle 
      circle();

      // Draw player lives
      threeLives();
    }
  }
  
  /**
   * The method snow draws the snow, allows the player to change the speed of snowfall, checks if the player has collided with a snowflake, and allows the player to remove snowflakes by clicking on them
   * @author: A. Waqar
   */
  public void snow() {
    for (int i = 0; i < snowX.length; i++) {
      if (!ballHideStatus[i]){
        
        // Draw snow and animate falling down
        fill (255, 255, 255);
        circle(snowX[i], snowY[i], snowDiameter);
        snowY[i] += 2;
        
        // Reset snowflakes 
        if (snowY[i] > height) {
          snowY[i] = 0;
        }

        // Change speed of snowflakes 
        if (keyPressed){
          if (keyCode == UP) {
            snowY[i] -= 1;
          }
          else if (keyCode == DOWN) {
            snowY[i] += 1;
          }
        }

        // Collision detection with snowflake and player
        if (dist(snowX[i], snowY[i], circleX, circleY) < snowDiameter / 10 * 25 / 2){
          snowY[i] = 0;
          playerLives--;
        }

        // Collision detection with disappearing snowflake
        if (mousePressed) {
          if (dist(snowX[i], snowY[i], mouseX, mouseY) < snowDiameter / 2) {
            ballHideStatus [i] = true;
          }
        }
      }
    }
  }

  /**
   * The program circle creates circle/player and makes it controlled through WASD keys
   * @author A. Waqar
   */
  public void circle() {
    // draw blue circle and control by player through WASD keys 
    if (keyPressed) {
      if (key == 'w') {
        circleY -= 2;
      }
      else if (key == 's') {
        circleY += 2;
      }
      else if (key == 'a'){
        circleX -= 2;
      }
      else if (key == 'd'){
       circleX += 2;
      }
    }  
    fill (121, 200, 252);
    ellipse(circleX, circleY, 25, 25);
  }

  /**
   * threeLives is the method which draws the three player lives in the corner and a life is lost everytime the player collides with a snowflake
   * @author A. Waqar
   */
  public void threeLives() {
    fill(150, 50, 50);
    for (int i = playerLives; i > 0; i--) {
      square(width - (i * 50), 50, 20);
    }
  }
}