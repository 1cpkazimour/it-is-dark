/**
 * Represents a collision between a Player object and a LevelElement in the game.
 * Each collision has a side and a type.
 */
public class Collision {
   
   /**
    * Sides of a Player that can collide with a LevelElement
    */
   public enum Side {
      LEFT,
      RIGHT,
      TOP,
      BOTTOM
   }
   
   /**
    * Types of collisions that can take place
    */
   public enum Type {
      NORMAL,
      DEADLY,
      SLIPPERY
   }
   
   /**
    * The type of collison that took place
    */
   private Type collisionType;
   /**
    * The side of the player involved in the collision
    */
   private Side collisionSide;
   /**
    * The new X or Y value of the plane for the player to snap to
    */
   private int line; // new X or Y value
   /**
    * How far the player has to move to correct this collision
    */
   private int degree;
   
   /**
    * Creates a new Collision with a given side, type, line, and degree.
    * @param side The side of the player involved in this collision.
    * @param type The type of Collision that is taking place.
    * @param line The X or Y value of the plane that they player
    *             needs to snap to as a result of this Collision.
    * @param deg The degree of this Collision, or how far the player
    *            needs to move to the plane specified in line.
    */
   public Collision(Side side, Type type, int line, int deg) {
      collisionSide = side;
      collisionType = type;
      this.line = line;
      degree = deg;
   }
   
   // Getters
   
   /**
    * Gets the side of the player that collided.
    * @return Collision.Side collisionSide of this collision.
    */
   public Side getSide() {return collisionSide;}
   /**
    * Gets the type of collision that occured.
    * @return Collision.Type collisionType of this collision.
    */
   public Type getType() {return collisionType;}
   /**
    * Gets the X or Y coordinate of the line that the player needs to snap to.
    * @return int line representing the X or Y coordinate to snap to.
    */
   public int getLine() {return line;}
   /**
    * Gets the degree, or how far the player needs to move to snap to the line.
    * @return int degree of this collision.
    */
   public int getDegree() {return degree;}
   
   // Functions to modify x and y coordinates of players
   
   /**
    * Gets the new correct X position for the player
    * @param oldX The current X position of the player
    * @return int X coordinate
    */
   public int getNewX(int oldX) {
      if (collisionSide == Side.TOP || collisionSide == Side.BOTTOM) return oldX;
      return line;
   }
   
   /**
    * Gets the new correct Y position for the player
    * @param oldY The current Y position of the player
    * @return int Y coordinate
    */
   public int getNewY(int oldY) {
      if (collisionSide == Side.LEFT || collisionSide == Side.RIGHT) return oldY;
      return line;
   }
}