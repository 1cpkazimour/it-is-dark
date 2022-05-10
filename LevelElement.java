import java.io.Serializable;

public class LevelElement implements Serializable{
   private int x1;
   private int y1;
   private int x2;
   private int y2;
   
   public LevelElement(int xone, int yone, int xtwo, int ytwo){
      x1 = xone;
      y1 = yone;
      x2 = xtwo;
      y2 = ytwo;
   }
   
   public String toString(){
      return x1 + ", " + y1 + ", " + x2 + ", " + y2;
   }
}