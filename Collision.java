public class Collision {
   // Enums for different types of collisions
   public enum Side {
      LEFT,
      RIGHT,
      TOP,
      BOTTOM
   }
   public enum Type {
      NORMAL,
      DEADLY,
      SLIPPERY
   }
   
   // Properties of collision
   private Type collisionType;
   private Side collisionSide;
   
   public Collision(Side side, Type type) {
      collisionSide = side;
      collisionType = type;
   }
   
   // Getters
   public Side getSide() {return collisionSide;}
   public Type getType() {return collisionType;}
   
   // toString for testing
   public String toString() {
      return "Side: " + collisionSide + ", Type: " + collisionType;
   }
}