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
   
   // Returns downward angle of top face in degrees
   public double getSlope() {
      int a = super.getY2() - super.getY1();
      int b = super.getX2() - super.getX1();
      return Math.atan((double) a / (double) b) * (180 / Math.PI);
   }
   
   // Checks collisions with player
   public Collision checkCollisions(int x, int y) {
      Collision box = super.checkCollisions(x, y);
      // Checks if the player collides with the sides or top of the slope
      if (box == null) return null;
   
      //System.out.println("x: " + x + " y: " + y + " d: " + d + " refx: " + refx + " refy: " + refy);
      if ((box.getSide() == Collision.Side.LEFT || box.getSide() == Collision.Side.BOTTOM) && direction == Direction.RIGHT) {
         y += Player.SIZE;
         double xa = super.getX1();
         double xb = super.getX2();
         double ya = super.getY1();
         double yb = super.getY2();
         double c = (yb - ya) / (xb - xa);
         double refx = -((c * xa) + ya + (x/c) - y) / (-(1 / c) - c);
         double refy = c * (refx - xa) + y;
         double d = Math.sqrt(Math.pow((refx - x), 2) + Math.pow((refy - y), 2));
         if (y > refy) {
            return new SlopeCollision(Collision.Side.BOTTOM, Collision.Type.NORMAL, (int) refx, (int) refy - Player.SIZE, 0);         
         } else {
            return null;
         }
      } else if ((box.getSide() == Collision.Side.RIGHT || box.getSide() == Collision.Side.BOTTOM) && direction == Direction.LEFT) {
         x += Player.SIZE;
         y += Player.SIZE;
         double xa = super.getX1();
         double xb = super.getX2();
         double ya = super.getY2();
         double yb = super.getY1();
         double c = (yb - ya) / (xb - xa);
         double refx = -((c * xa) + ya + (x/c) - y) / (-(1 / c) - c);
         double refy = c * (refx - xa) + y;
         double d = Math.sqrt(Math.pow(refx - x, 2) + Math.pow(refy - y, 2));
         if (y > refy) {
            return new SlopeCollision(Collision.Side.BOTTOM, Collision.Type.NORMAL, (int) refx - Player.SIZE, (int) refy - Player.SIZE, 0);
         } else {
            return null;
         }
      } else {
         return box;
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