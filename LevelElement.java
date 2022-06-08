import java.io.Serializable;
import java.awt.*;

/**
 * Represents a single rectangular element (box) in a Level.
 */
public class LevelElement implements Serializable {
   /**
    * Internal UID for serialization.
    */
   private static final long serialVersionUID = 9060099550825241414L;

   /**
    * X coordinate of first corner
    */
   private int x1;
   
   /**
    * Y coordinate of first corner
    */
   private int y1;

   /**
    * X coordinate of second corner
    */
   private int x2;
   
   /**
    * X coordinate of second corner
    */
   private int y2;

   /**
    * Whether or not the top face has been illumnated (collided with)
    */
   private boolean lit_top;
   
   /**
    * Whether or not the bottom face has been illumnated (collided with)
    */
   private boolean lit_bottom;
   
   /**
    * Whether or not the left face has been illumnated (collided with)
    */
   private boolean lit_left;
   
   /**
    * Whether or not the right face has been illumnated (collided with)
    */
   private boolean lit_right;
   
   /**
    * The color of the faces when illumniated
    */
   private Color color = new Color(255, 255, 255);
   
   /**
    * Creates a new LevelElement with given coordinates.
    * @param xa The first x coordinate
    * @param ya The first y coordinate
    * @param xb The second x coordinate
    * @param yb The second y coordinate
    */
   public LevelElement(int xa, int ya, int xb, int yb) {
      x1 = Math.min(xa, xb);
      y1 = Math.min(ya, yb);
      x2 = Math.max(xa, xb);
      y2 = Math.max(ya, yb);
   }

   // For testing purposes
   public String toString() {
      return x1 + ", " + y1 + ", " + x2 + ", " + y2;
   }

   public void drawFaces(Graphics g) {
      if (lit_top) {
         g.setColor(color);
         g.fillRect(x1, y1, x2 - x1, 2);
      }
      if (lit_bottom) {
         g.setColor(color);
         g.fillRect(x1, y2 - 2, x2 - x1, 2);
      }
      if (lit_left) {
         g.setColor(color);
         g.fillRect(x1, y1, 2, y2 - y1);
      }
      if (lit_right) {
         g.setColor(color);
         g.fillRect(x2, y1, 2, y2 - y1);
      }
   }

   public Collision checkCollisions(int x, int y) {
      int size = Player.SIZE;

      // Gap between the left edge of the player and the right edge of the wall
      int left_gap = x - x2;
      // Gap between the right edge of the player and the left edge of the wall
      int right_gap = x1 - (x + size);
      // Gap between the top edge of the player and the bottom edge of the wall
      int top_gap = y - y2;
      // Gap between the bottom edge of the player and the top edge of the wall
      int bottom_gap = y1 - (y + size);
      // gap is positive if you aren't colliding, negative if you are

      // This boolean represents if the player is within the horizontal realm of the
      // box. If it is in the vertical realm as well, it is colliding
      boolean horiz_realm = top_gap < 0 && bottom_gap < 0;

      // This boolean represents if the player is within the vertical realm of the
      // box. If it is in the horizontal realm as well, it is colliding
      boolean vert_realm = left_gap < 0 && right_gap < 0;

      if (!vert_realm || !horiz_realm)
         return null; // Not colliding
      // at this point, we know all gaps are less than zero
      // so the greatest gap is the one that is closest to zero, and therefore
      // the one we want to collide with

      // Left gap is the smallest
      if (left_gap > right_gap && left_gap > top_gap && left_gap > bottom_gap) {
         lit_right = true;
         return new Collision(Collision.Side.LEFT, Collision.Type.NORMAL, x2, -left_gap);
      }

      // We don't need to check the left gap anymore
      if (right_gap > top_gap && right_gap > bottom_gap) {
         lit_left = true;
         return new Collision(Collision.Side.RIGHT, Collision.Type.NORMAL, x1 - size, -right_gap);
      }

      // We don't need to check for the right gap anymore
      if (top_gap > bottom_gap) {
         lit_bottom = true;
         return new Collision(Collision.Side.TOP, Collision.Type.NORMAL, y2, -top_gap);
      }

      // We don't need to check for anything, we know it's bottom
      lit_top = true;
      return new Collision(Collision.Side.BOTTOM, Collision.Type.NORMAL, y1 - size, -bottom_gap);
   }

   // Change face color
   public void setColor(Color color) {
      this.color = color;
   }

   // Getters
   public int getX1() {
      return x1;
   }

   public int getY1() {
      return y1;
   }

   public int getX2() {
      return x2;
   }

   public int getY2() {
      return y2;
   }
   
   // Remove all paint from the object
   public void clearPaint() {
      lit_top = lit_bottom = lit_left = lit_right = false;
   }

   // Compute properties of the objects
   public int getHeight() {
      return y2 - y1;
   }

   public int getWidth() {
      return x2 - x1;
   }

   // Main method for testing
//    public static void main(String[] args) {
//       LevelElement test = new LevelElement(0, 0, 60, 60);
//       Collision c = test.checkCollisions(53, 10);
//       System.out.println(c);
//       System.out.println(c.getNewX(53));
//       System.out.println(c.getNewY(10));  
//    }
}
