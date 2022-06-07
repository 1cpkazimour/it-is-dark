public class Collision {
   // Enums for different types of collisions
   // (side of player)
   public enum Side {
      LEFT,
      RIGHT,
      TOP,
      BOTTOM,
      CORNER_LEFT,
      CORNER_RIGHT
   }
   public enum Type {
      NORMAL,
      DEADLY,
      SLIPPERY_LEFT,
      SLIPPERY_RIGHT
   }
   
   // Properties of collision
   private Type collisionType;
   private Side collisionSide;
   private int line; // new X or Y value
   private int degree; // How extreme this collision is.
   // Low degree collisions should be preferred.
   
   public Collision(Side side, Type type, int l, int deg) {
      collisionSide = side;
      collisionType = type;
      line = l;
      degree = deg;
   }
   
   // Getters
   public Side getSide() {return collisionSide;}
   public Type getType() {return collisionType;}
   public int getLine() {return line;}
   public int getDegree() {return degree;}
   
   // Functions to modify x and y coordinates of players
   public int getNewX(int oldX) {
      if (collisionSide == Side.TOP || collisionSide == Side.BOTTOM) return oldX;
      return line;
   }
   
   public int getNewY(int oldY) {
      if (collisionSide == Side.LEFT || collisionSide == Side.RIGHT) return oldY;
      return line;
   }
   
   // toString for testing
   public String toString() {
      return "Side: " + collisionSide + ", Type: " + collisionType;
   }
}