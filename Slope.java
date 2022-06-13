import java.lang.Math;
import java.awt.*;

public class Slope extends LevelElement {

   // Direction of slope
   private Direction direction;

   enum Direction {
      LEFT,
      RIGHT
   }
   
   public Slope(int xone, int yone, int xtwo, int ytwo, Direction d) {
      super(xone, yone, xtwo, ytwo);
      direction = d;
   }
   
   // Returns downward slope of the slant of the triangle
   public double getSlope() {
      int a = super.getY2() - super.getY1();
      int b = super.getX2() - super.getX1();
      if (direction == Direction.LEFT) a = -a; // Invert slope for left slopes
      return (double)a / (double)b;
   }
   
   // Checks collisions with player
   public Collision checkCollisions(int x, int y) {
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
      
      // Debug
      // if (GamePanel.globalGraphics != null) GamePanel.globalGraphics.drawLine(x, y, (int)xr, (int)yr);
      
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
         return slopeCollision;
      } else if (slopeCollision == null) {
         return boxCollision;
      } else if (slopeCollision.getDegree() < boxCollision.getDegree()) {
         return slopeCollision;
      } else {
         return boxCollision;
      }
   }
   
   public void drawFaces(Graphics g) {
      super.drawFaces(g);
      if (direction == Direction.LEFT) {
         g.drawLine(super.getX1(), super.getY2(), super.getX2(), super.getY1());
      } else {
         g.drawLine(super.getX1(), super.getY1(), super.getX2(), super.getY2());
      }
   }
   
//    public static void main(String[] args) {
//       Slope test = new Slope(6, 6, 11, 11, Direction.LEFT);
//       System.out.println(test.getSlope());
//       for (int x = 0; x <= 11; x++) {
//          for (int y = 0; y <= 11; y++) {
//             test.checkCollisions(x, y);
//             
//          }
//       }
//    }
}