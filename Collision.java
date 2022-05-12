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
   private int line;
   
   public Collision(Side side, Type type, int l) {
      collisionSide = side;
      collisionType = type;
      line = l;
   }
   
   // Getters
   public Side getSide() {return collisionSide;}
   public Type getType() {return collisionType;}
   public int getLine() {return line;}
   
   // toString for testing
   public String toString() {
      return collisionSide + ", " + collisionType;
   }
}