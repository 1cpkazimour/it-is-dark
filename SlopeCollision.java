/**
 * The type of Collision returned by Slopes
 */
public class SlopeCollision extends Collision {
   /**
    * represents the new x value for the player
    */
   private int newX;
   /**
    * represents the new y value for the player
    */
   private int newY;
      
   /**
    * Creates a new Collision with a given side, type, line, and degree.
    * @param side The side of the player involved in this collision.
    * @param type The type of Collision that is taking place.
    * @param newX The new X value of the player
    * @param newY The new Y value of the player
    * @param deg The degree of this Collision, or how far the player
    *            needs to move to the plane specified in line.
    */
   public SlopeCollision(Collision.Side side, Collision.Type type, int newX, int newY, int deg) {
      super(side, type, 0, deg);
      this.newX = newX;
      this.newY = newY;
   }

   /**
    * Gets the new correct X position for the player
    * @param oldX The current X position of the player
    * @return integer X coordinate
    */
   public int getNewX(int oldX) {
      return newX;
   }
   
   /**
    * Gets the new correct Y position for the player
    * @param oldY The current Y position of the player
    * @return integer Y coordinate
    */
   public int getNewY(int oldY) {
      return newY;
   }
}