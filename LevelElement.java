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
   
   public String toString() {
      return x1 + ", " + y1 + ", " + x2 + ", " + y2;
   }
   
   // Getters
   public int getX1() { return x1; }
   public int getY1() { return y1; }
   public int getX2() { return x2; }
   public int getY2() { return y2; }
   
   // Compute properties of the objects
   public int getHeight() { return y2 - y1; }
   public int getWidth() { return x2 - x1; }
}