import java.awt.*;
import java.util.*;

/**
 * Represents a player along with all of its properties
 */
public class Player {
   
   /**
    * The X position of the player.
    */
   private int x;
   
   /**
    * The Y position of the player.
    */
   private int y;
   
   /**
    * The speed of the player (horizontal movement per frame).
    */
   private int speed = 6;
   
   /**
    * The velocity of the player in the y direction (vertical).
    */
   private int vy = 0;
      
   /**
    * The size of the player (length of each side).
    */
   public static final int SIZE = 40;

   /**
    * The color of the player.
    */
   private Color color = new Color(255, 255, 255);
   
   /**
    * Whether or not the player is currently colliding with something.
    */
   private boolean didCollide = false;
   
   /**
    * Creates a new Player at a given position
    * @param x X position of new Player.
    * @param y Y position of new player.
    */
   public Player(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   /**
    * Draws the player at it's current position.
    * @param g java Graphics object to draw with.
    */
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillRect(x, y, SIZE, SIZE); 
   }
   
   // Move based on keys down
   // This method assumes it is being called 60 times per second
   // Returns true if the level has been won
   /**
    * Moves the player based upon the currently pressed keys.
    * This method assumes that it is being called 60 times per second.
    * Returns true if the Player has reached the objective of the level.
    * @param key_jump boolean representing the state of the jump key.
    * @param key_left boolean representing the state of the right key.
    * @param key_right boolean representing the state of the left key.
    * @param level Level that player is moving in.
    * @return boolean value representing whether or not the Player has reached the objective.
    */
   public boolean move(boolean key_jump, boolean key_right, boolean key_left, Level level) {
      if (key_jump && didCollide) { vy += 15; } // Checks that player is on floor
      if (key_right) { x += speed; }
      if (key_left) { x -= speed; }
      y -= vy;
      vy--;
      
      didCollide = false;
      
      Collision collision = level.checkCollisions(x, y);
      int maxCollisions = 100;
      while (collision != null && maxCollisions >= 0) {
         if (collision.getType() == Collision.Type.DEADLY) {
            die(level);
            break;
         }
         x = collision.getNewX(x);
         y = collision.getNewY(y);
         vy = 0;
         didCollide = true;
         
         collision = level.checkCollisions(x, y);
	 maxCollisions--;
      }
      
      if (y > 720) die(level);
      
      // Is on level end
      return Math.abs(level.getEndX() - x) < SIZE && Math.abs(level.getEndY() - y) < SIZE;
   }
   
   /**
    * Returns player to starting position of level and resets vertical velocity.
    * @param level The level that the player died in.
    */
   private void die(Level level) {
      x = level.getStartX();
      y = level.getStartY();
      vy = 0;
   }
   
   // getters for position
   
   /**
    * Gets the current X position of this Player
    * @return integer representing current X position of the Player.
    */
   public int getX() { return x; }
  
   /**
    * Gets the current Y position of this Player
    * @return integer representing current Y position of the Player.
    */
   public int getY() { return y; }
}
