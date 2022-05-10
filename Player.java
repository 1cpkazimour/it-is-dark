import java.awt.*;

// This class represents a player object
public class Player {
   // The x and y positions of the player
   private int x, y;
   // The player's speed
   private int speed = 6;
   // The player's vertical velocity
   private int vert = 0;
   
   // Constructor, can specify starting x and y 
   public Player(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   // Draw function, to draw self
   public void draw(Graphics g) {
      g.fillRect(x, y, 10, 10);
   }
   
   // Move based on keys down
   // This method assumes it is being called 60 times per second
   // TODO: add check for LevelElements
   public void move(boolean key_down, boolean key_up, boolean key_right, boolean key_left, boolean key_space) {
      if (key_down) { /*y += speed;*/ }
      if ((key_up || key_space) && y > 550) { vert += 10; } // Checks that player is on bottom
      if (key_right) { x += speed; }
      if (key_left) { x -= speed; }
      y -= vert;
      vert--;
      if (y > 550) {
         vert = 0;
      }
      
   }
   
   // getters for position
   public int getX() { return x; }
   public int getY() { return y; }
}