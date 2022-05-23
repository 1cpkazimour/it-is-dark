import java.lang.Math;

public class Slope extends LevelElement {

   // Direction of slope
   private Direction direction;

   enum Direction {
      LEFT,
      RIGHT
   }
   
   public Slope(int xone, int xtwo, int yone, int ytwo, Direction d) {
      super(xone, xtwo, yone, ytwo);
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
   if ((super.getWidth() * y) + (super.getHeight() * x) - (super.getWidth() * super.getHeight()) < 0){
               System.out.println("collided");
            }
      Collision box = super.checkCollisions(x, y);
      // Checks if the player collides with the sides or top of the slope
      if (box != null) {
         double xa = super.getX1();
         double xb = super.getX2();
         double ya = super.getY1();
         double yb = super.getY2();
         double c = (yb - ya) / (xb - xa);
         double refx = ((c * xa) + ya + (x/c) - y) / ((-1 / c) - c);
         double refy = c * (refx - xa) + y;
         double d = Math.sqrt(Math.pow((refx - x), 2) + Math.pow((refy - y), 2));
         if (y < refy) {
            System.out.println("Bottom left collision");
         }
         if (box.getSide() == Collision.Side.RIGHT && direction == Direction.LEFT) {

         }
         if (box.getSide() == Collision.Side.LEFT && direction == Direction.RIGHT) {
         }
         if (box.getSide() == Collision.Side.BOTTOM) {
            
         }
         
      }
      return box;
   }
   
   public static void main(String[] args) {
      Slope test = new Slope(5, 5, 10, 10, Direction.LEFT);
      System.out.println(test.getSlope());
      test.checkCollisions(6, 8);
   }
   
   
}