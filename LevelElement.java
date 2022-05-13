import java.io.Serializable;

// Represents a single element in a level. Maybe should be made an interface?
public class LevelElement implements Serializable {

   // X and Y coordinates of one corner
   private int x1;
   private int y1;
   
   // X and Y coordinates of another corner
   private int x2;
   private int y2;
   
   public LevelElement(int xone, int yone, int xtwo, int ytwo) {
      x1 = xone;
      y1 = yone;
      x2 = xtwo;
      y2 = ytwo;
   }
   
   // For testing purposes
   public String toString() {
      return x1 + ", " + y1 + ", " + x2 + ", " + y2;
   }
   
   public Collision checkCollisions(int x, int y){
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
      
      if (!vert_realm || !horiz_realm) return null; // Not colliding
      // at this point, we know all gaps are less than zero
      // so the greatest gap is the one that is closest to zero, and therefore
      // the one we want to collide with
      
      // Left gap is the smallest
      if (left_gap > right_gap && left_gap > top_gap && left_gap > bottom_gap) {
         System.out.println("L" + left_gap);
         return new Collision(Collision.Side.LEFT, Collision.Type.NORMAL, x2, -left_gap);
      }
      
      // We don't need to check the left gap anymore
      if (right_gap > top_gap && right_gap > bottom_gap) {
         System.out.println("R" + right_gap);
         return new Collision(Collision.Side.RIGHT, Collision.Type.NORMAL, x1 - size, -right_gap);
      }
      
      // We don't need to check for the right gap anymore
      if (top_gap > bottom_gap) {
         return new Collision(Collision.Side.TOP, Collision.Type.NORMAL, y2, -top_gap);
      }
      
      // We don't need to check for anything, we know it's bottom
      return new Collision(Collision.Side.BOTTOM, Collision.Type.NORMAL, y1 - size, -bottom_gap);
   }
   
   // Getters
   public int getX1() { return x1; }
   public int getY1() { return y1; }
   public int getX2() { return x2; }
   public int getY2() { return y2; }
   
   // Compute properties of the objects
   public int getHeight() { return y2 - y1; }
   public int getWidth() { return x2 - x1; }
   
   // Main method for testing
//    public static void main(String[] args) {
//       LevelElement test = new LevelElement(0, 0, 60, 60);
//       Collision c = test.checkCollisions(53, 10);
//       System.out.println(c);
//       System.out.println(c.getNewX(53));
//       System.out.println(c.getNewY(10));  
//    }
}