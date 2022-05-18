import java.lang.Math;

public class Slope extends LevelElement {
   
   public Slope(int xone, int xtwo, int yone, int ytwo) {
      super(xone, xtwo, yone, ytwo);
   }
   
   public double getSlope() {
      int a = super.getY2() - super.getY1();
      int b = super.getX2() - super.getX1();
      return Math.atan((double) a / (double) b);
   }
}