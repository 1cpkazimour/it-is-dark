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
       // Check left collision (Right side of player)
      if (x + Player.SIZE + 1 >= x1 && x <= x1 && y <= y2 && y + Player.SIZE >= y1) {
         return new Collision(Collision.Side.RIGHT, Collision.Type.NORMAL, x1);
      }
      // Check right collision (Left side of player)
      if (x - 1 <= x2 && x + Player.SIZE >= x2 && y <= y2 && y + Player.SIZE >= y1) {
         return new Collision(Collision.Side.LEFT, Collision.Type.NORMAL, x2);
      }
      // Check bottom collision (Top of player)
      if (y - 1 <= y2 && y + Player.SIZE >= y2 && x <= x2 && x + Player.SIZE >= y1) {
         return new Collision(Collision.Side.TOP, Collision.Type.NORMAL, y2);
      }
      // Check top collision (Bottom of player)
      if (y + Player.SIZE + 1 >= y1 && y <= y1 && x <= x2 && x + Player.SIZE >= x1) {
         return new Collision(Collision.Side.BOTTOM, Collision.Type.NORMAL, y1);
      }
      return null;
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
//       System.out.println(test.checkCollisions(53, 10));
//       
//   }
}