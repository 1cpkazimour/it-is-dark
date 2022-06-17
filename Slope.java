import java.lang.Math;
import java.awt.*;

/**
 * A type of LevelElement that implements a sloped surface
 */
public class Slope extends LevelElement {
   /**
    * Internal UID for serialization.
    */
    private static final long serialVersionUID = -4083507241221796397L;

   /**
    * Direction of the slope
    */
   private Direction direction;
   
   /**
    * Whether or not the slant face has been illumnated (collided with)
    */
   private boolean lit_slant;

   /**
    * Color of the slope (white)
    */
   private Color color = new Color(255, 255, 255);

   /**
    * Represents the two possible directions of a slope
    * LEFT (sloping down and to the left)
    * RIGHT (sloping down and to the right_
    */
   enum Direction {
      LEFT,
      RIGHT
   }
   
   /**
    * Creates a new LevelElement with given coordinates.
    * @param xone The first x coordinate
    * @param yone The first y coordinate
    * @param xtwo The second x coordinate
    * @param ytwo The second y coordinate
    * @param d The direction the slope is facing
    */
   public Slope(int xone, int yone, int xtwo, int ytwo, Direction d) {
      super(xone, yone, xtwo, ytwo);
      direction = d;
      lit_slant = false;
   }

   /**
    * Returns the direction of the slope
    * @return the direction of the slope
    */
   public Direction getDirection() {
      return direction;
   }
   
   
   /**
    * Returns the slope of the slope (y=<b>m</b>x+b form)
    * @return the slope of the slope
    */
   public double getSlope() {
      int a = super.getY2() - super.getY1();
      int b = super.getX2() - super.getX1();
      if (direction == Direction.LEFT) a = -a; // Invert slope for left slopes
      return (double)a / (double)b;
   }
   
   /**
    * Checks whether a given set of coordinates collides with this LevelElement.
    * @param x The X coordinate to check.
    * @param y The Y coordinate to check.
    * @return A Collision object containing collision data if applicable, otherwise null.
    */
   public Collision checkCollisions(int x, int y) {
      // Store these temporary values, in case we need them if the boxCollision doesn't apply
      boolean lit_bottom = super.lit_bottom;
      boolean lit_left = super.lit_left;
      boolean lit_right = super.lit_right;

      Collision boxCollision = super.checkCollisions(x, y);
      // Checks if the player collides with the sides or top of the slope
      if (boxCollision == null) 
         return null;
         
      y += Player.SIZE;
      if (direction == Direction.LEFT) x += Player.SIZE;
      
      // Slope of the line of the box
      double slope = getSlope();
      // Slope of the line extending from the point on the player to the collision point on the slope
      // This is defined to be perpendicular to the slope line, so we work backwards from it
      double invSlope = -1 / slope;
            
      // These are just the coordinates of a point on the end of the triangle. It doesn't matter which one
      double xa = super.getX1();
      double ya = super.getY1();
      if (direction == Direction.LEFT) xa = super.getX2();
      
      // This is the point on the slope that the player should be snapped to
      // I did some algebra to figure out this formula
      double xr = (slope * xa - invSlope * x + y - ya) / (slope - invSlope);
      double yr = slope * (xr - xa) + ya;
      
      Collision slopeCollision = null;
      // If we are colliding, then yr will be less than y
      if (yr < (double)y) {
         if (direction == Direction.RIGHT) {
            slopeCollision = new SlopeCollision(Collision.Side.CORNER_RIGHT, Collision.Type.SLIPPERY_RIGHT,
               (int)xr, (int)yr - Player.SIZE, (int)Math.sqrt(Math.pow(xr - x, 2) + Math.pow(yr - y, 2)));
         } else {
            slopeCollision = new SlopeCollision(Collision.Side.CORNER_LEFT, Collision.Type.SLIPPERY_LEFT,
               (int)xr - Player.SIZE, (int)yr - Player.SIZE, (int)Math.sqrt(Math.pow(xr - x, 2) + Math.pow(yr - y, 2)));         
         }
      }
      
      // Make collisions with edges of slopes less janky
      if (super.getX1() > x || super.getX2() < x) {
         return boxCollision;
      }
      
      if ((boxCollision.getSide() == Collision.Side.BOTTOM) ||
          (boxCollision.getSide() == Collision.Side.LEFT && direction == Direction.RIGHT) ||
          (boxCollision.getSide() == Collision.Side.RIGHT && direction == Direction.LEFT)) {
         // Restore pre-boxCollision values
         super.lit_right = lit_right;
         super.lit_left = lit_left;
         super.lit_bottom = lit_bottom;
         // We've collided with a slope if slopeCollision isn't null
         lit_slant |= slopeCollision != null;
         return slopeCollision;
      } else if (slopeCollision == null) {
         // Restore pre-boxCollision values
         super.lit_right = lit_right;
         super.lit_left = lit_left;
         super.lit_bottom = lit_bottom;
         return null;
      } else if (slopeCollision.getDegree() < boxCollision.getDegree()) {
         // Restore pre-boxCollision values
         super.lit_right = lit_right;
         super.lit_left = lit_left;
         super.lit_bottom = lit_bottom;
         // We've collided with a slope (slopeCollision isn't null here)
         lit_slant = true;
         return slopeCollision;
      } else {
         return boxCollision;
      }
   }
   
   /**
    * Draws the faces of the Slope that are set as illuminated.
    * @param g The graphics object used to draw on the screen.
    */
   public void drawFaces(Graphics g) {
      g.setColor(color);
      if (super.lit_bottom) {
         g.fillRect(super.getX1(), super.getY2() - 2, super.getX2() - super.getX1(), 2);
      }
      if (super.lit_left && direction == Direction.RIGHT) {
         g.fillRect(super.getX1(), super.getY1(), 2, super.getY2() - super.getY1());
      }
      if (super.lit_right && direction == Direction.LEFT) {
         g.fillRect(super.getX2(), super.getY1(), 2, super.getY2() - super.getY1());
      }
      
      if (lit_slant) {
         if (direction == Direction.LEFT) {
            g.drawLine(super.getX1(), super.getY2() - 2, super.getX2(), super.getY1());
            g.drawLine(super.getX1(), super.getY2() - 1, super.getX2(), super.getY1() + 1);
         } else {
            g.drawLine(super.getX1(), super.getY1() - 2, super.getX2(), super.getY2());
            g.drawLine(super.getX1(), super.getY1() - 1, super.getX2(), super.getY2() + 1);
         }
      }
   }
}