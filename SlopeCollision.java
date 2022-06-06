public class SlopeCollision extends Collision {

   private int newX;
   private int newY;
   
   public SlopeCollision(Collision.Side side, Collision.Type type, int newX, int newY, int deg) {
      super(side, type, 400, deg);
      System.out.println(newX + ", " + newY);
      this.newX = newX;
      this.newY = newY;
   }


   public int getNewX(int oldX) {
      return newX;
   }
   
   public int getNewY(int oldY) {
      return newY;
   }
}