import java.awt.*;
import java.util.*;

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
   private Color color = new Color(255, 255, 255);
   
   private boolean didCollide = false;
   
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
   public void move(boolean key_jump, boolean key_right, boolean key_left, Level level) {
      if (key_jump && (y > FLOOR_LEVEL || didCollide)) { vy += 15; } // Checks that player is on floor
      if (key_right) { x += speed; }
      if (key_left) { x -= speed; }
      y -= vy;
      vy--;
      // Set vertical velocity to zero upon vertial collision
      if (y > FLOOR_LEVEL) {
         vy = 0;
      }
      
      didCollide = false;
      
      Collision collision = level.checkCollisions(x, y);
      while (collision != null) {
         if (collision.getType() == Collision.Type.DEADLY) {
            color = new Color(255, 0, 0);
            x = level.getStartX();
            y = level.getStartY();
            vy = 0;
            color = new Color(255, 255, 255);
            break;
         }
         x = collision.getNewX(x);
         y = collision.getNewY(y);
         vy = 0;
         didCollide = true;
         
         collision = level.checkCollisions(x, y);
      }
      
      
   }
   
   // getters for position
   public int getX() { return x; }
   public int getY() { return y; }
}