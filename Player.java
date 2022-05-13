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
   public void move(boolean key_jump, boolean key_right, boolean key_left, Level level) {
      boolean col_left = false;
      boolean col_right = false;
      boolean col_top = false;
      boolean col_bottom = false;
      ArrayList<Collision> collisions = level.checkAllCollisions(x, y);
      // Read all collision data and snap player to surface
      for (Collision c : collisions) {
         if (c != null){
             if (c.getSide() == Collision.Side.TOP) {col_top = true; y = c.getLine();}
             if (c.getSide() == Collision.Side.BOTTOM) {col_bottom = true; y = c.getLine() - Player.SIZE;}
             if (c.getSide() == Collision.Side.LEFT) {col_left = true; x = c.getLine();}
             if (c.getSide() == Collision.Side.RIGHT) {col_right = true; x = c.getLine() - Player.SIZE;}
               
         }
         System.out.println(c);
      }
      if (key_jump && (y > FLOOR_LEVEL || col_bottom) && !col_top) { vy += 10; } // Checks that player is on floor
      if (key_right && !col_right) { x += speed; }
      if (key_left && !col_left) { x -= speed; }
      y -= vy;
      vy--;
      // Set vertical velocity to zero upon vertial collision
      if (y > FLOOR_LEVEL || col_bottom || col_top) {
         vy = 0;
      }
      
   }
   
   // getters for position
   public int getX() { return x; }
   public int getY() { return y; }
}