public class Spike extends LevelElement {

   public Spike(int xone, int xtwo, int yone, int ytwo)  {
      super(xone, xtwo, yone, ytwo);
   }
   
   public Collision checkCollisions(int x, int y) {
      Collision collision = super.checkCollisions(x, y);
      if (collision == null){
         return null;
      }
      return new Collision(collision.getSide(), Collision.Type.DEADLY, collision.getLine(), collision.getDegree());
   }
   
}