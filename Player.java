import java.awt.*;

// This class represents a player object
public class Player {
   // The x and y positions of the player
   private int x, y;
   // The player's speed
   private int speed = 6;
   // The player's vertical (y) velocity
   private int vy = 0;
      
   // Size of the player
   public static final int SIZE = 40;
   
   // Level of the floor
   private static final int FLOOR_LEVEL = 500 - SIZE;
   // Color of the player (black)
   private static final Color color = new Color(255, 255, 255);
   
   // Constructor, can specify starting x and y 
   public Player(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   // Draw function, to draw self
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillRect(x, y, SIZE, SIZE);
   }
   
   // Move based on keys down
   // This method assumes it is being called 60 times per second
   // TODO: add check for LevelElements
   public void move(boolean key_jump, boolean key_right, boolean key_left) {
      if (key_jump && y > FLOOR_LEVEL) { vy += 10; } // Checks that player is on bottom
      if (key_right) { x += speed; }
      if (key_left) { x -= speed; }
      y -= vy;
      vy--;
      if (y > FLOOR_LEVEL) {
         vy = 0;
      }
      
   }
   
   // getters for position
   public int getX() { return x; }
   public int getY() { return y; }
}