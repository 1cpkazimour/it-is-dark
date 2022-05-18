import java.lang.Math;

public class Slope extends LevelElement {
   
   public Slope(int xone, int xtwo, int yone, int ytwo) {
      super(xone, xtwo, yone, ytwo);
   }
   
   // Returns downward angle of top face in degrees
   public double getSlope() {
      int a = super.getY2() - super.getY1();
      int b = super.getX2() - super.getX1();
      return Math.atan((double) a / (double) b) * (180 / Math.PI);
   }
   
   // Code for testing
   //public static void main(String[] args) {
   //   Slope test = new Slope(1, 1, 2, 2);
   //   System.out.println(test.getSlope());
   //}
   
   
}