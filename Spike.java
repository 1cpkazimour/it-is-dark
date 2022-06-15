import java.awt.*;

/**
 * Represents a type of LevelElement called a spike that kills the player upon a collision.
 */
public class Spike extends LevelElement {
   /**
    * Creates a new Spike with given coordinates.
    * @param xa The first x coordinate
    * @param ya The first y coordinate
    * @param xb The second x coordinate
    * @param yb The second y coordinate
    */
   public Spike(int xa, int xb, int ya, int yb)  {
      super(xa, xb, ya, yb);
   }
   
   /**
    * Checks whether a given set of coordinates collides with this Spike.
    * @param x The X coordinate to check.
    * @param y The Y coordinate to check.
    * @return A Collision object containing collision data if applicable, otherwise null.
    */
   public Collision checkCollisions(int x, int y) {
      Collision collision = super.checkCollisions(x, y);
      if (collision == null){
         return null;
      }
      return new Collision(collision.getSide(), Collision.Type.DEADLY, collision.getLine(), collision.getDegree());
   }
   
   /**
    * Draws the faces of the Spike that are set as illuminated.
    * @param g The graphics object used to draw on the screen.
    */
   public void drawFaces(Graphics g) {
      super.setColor(new Color(255, 0, 0));
      super.drawFaces(g);
   }
   
}
