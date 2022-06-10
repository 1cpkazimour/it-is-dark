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
   
   /**
    * Draws the faces of the LevelElement that are set as illuminated.
    * @param g The graphics object used to draw on the screen.
    */
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

   /**
    * Checks whether a give set of coordinates collides with this LevelElement.
    * @param x The X coordinate to check.
    * @param y The Y coordinate to check.
    * @return A Collision object containing collision data if applicable, otherwise null.
    */
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

      // We don't need to check for anything, we know it's bottom.
      lit_top = true;
      return new Collision(Collision.Side.BOTTOM, Collision.Type.NORMAL, y1 - size, -bottom_gap);
   }

   /**
    * Changes the color of the LevelElement when it is illuminated.
    * @param color The color to use when drawing the faces of LevelElements.
    */
   public void setColor(Color color) {
      this.color = color;
   }

   // Getters
   
   /**
    * Gets the X value of the first coordinate pair.
    * @return integer value of X coordinate.
    */
   public int getX1() {
      return x1;
   }
   
   /**
    * Gets the Y value of the first coordinate pair.
    * @return integer value of Y coordinate.
    */
   public int getY1() {
      return y1;
   }

   /**
    * Gets the X value of the second coordinate pair.
    * @return integer value of X coordinate.
    */
   public int getX2() {
      return x2;
   }

   /**
    * Gets the Y value of the second coordinate pair.
    * @return integer value of Y coordinate.
    */
   public int getY2() {
      return y2;
   }
   
   /**
    * Removes illumination from all faces of this LevelElement.
    */
   public void clearPaint() {
      lit_top = lit_bottom = lit_left = lit_right = false;
   }

   /**
    * Gets the height of the LevelElement.
    * @return integer value representing height.
    */
   public int getHeight() {
      return y2 - y1;
   }

   /**
    * Gets the width of the LevelElement.
    * @return integer value representing width.
    */
   public int getWidth() {
      return x2 - x1;
   }
}
