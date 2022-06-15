import java.util.*;
import java.io.Serializable;
import java.awt.*;

/**
 * Represents a single level in the game containing many LevelElements that make up the level.
 */
public class Level implements Serializable {

   /**
    * Internal UID for serialization.
    */
   private static final long serialVersionUID = 6900062191966977137L;

   // Starting X and Y coordinates for player
   
   /**
    * The X coordinate that the player starts the level at.
    */
   private int startX;
   
   /**
    * The Y coordinate that the player starts the level at.
    */
   private int startY;
   
   // X and Y coordinates of player objective
   
   /**
    * The X coordinate that the player needs to reach to beat the level.
    */
   private int endX;
   
   /**
    * The Y coordinate that the player needs to reach to beat the level.
    */
   private int endY;
   
   /**
    * Contains all of the LevelElements that make up the level.
    */
   private ArrayList<LevelElement> elements;
   
   /**
    * Creates a new empty Level with a given start and end point.
    * @param sX Starting X position of player.
    * @param sY Starting Y position of player.
    * @param eX X position of level objective.
    * @param eY Y position of level objective.
    */
   public Level(int sX, int sY, int eX, int eY) {
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
      elements = new ArrayList<LevelElement>();
   }
   
   /**
    * Creates a new Level with a given start and end point
    * and fills it with LevelElements from a given array.
    * @param sX Starting X position of player.
    * @param sY Starting Y position of player.
    * @param eX X position of level objective.
    * @param eY Y position of level objective.
    * @param levelElements Array of LevelElements to fill level with.
    */
   public Level(int sX, int sY, int eX, int eY, LevelElement[] levelElements) {
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
      elements = new ArrayList<LevelElement>();
      for(LevelElement element : levelElements){
         elements.add(element);
      }
   }
   
   /**
    * Creates a new Level with a given start and end point
    * and fills it with LevelElements from a given ArrayList.
    * @param sX Starting X position of player.
    * @param sY Starting Y position of player.
    * @param eX X position of level objective.
    * @param eY Y position of level objective.
    * @param levelElements ArrayList of LevelElements to fill level with.
    */
   public Level(int sX, int sY, int eX, int eY, ArrayList<LevelElement> levelElements) {
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
      elements = levelElements;
   }
   
   /**
    * Adds a LevelElement to the Level.
    * @param element LevelElement to add to this Level.
    */
   public void addElement(LevelElement element) {
      elements.add(element);
   }
   
   /**
    * Exports all of the LevelElements in this level to an ArrayList.
    * @return ArrayList containing all LevelElements.
    */
   public ArrayList<LevelElement> getElements() {
      return elements;
   }
   
   /**
    * Gets a LevelElement at a given index.
    * @param index Index to get LevelElement from.
    * @return LevelElement at given index.
    */
   public LevelElement getElement(int index) {
      return elements.get(index);
   }
   
   /**
    * Gets the length of a level (number of LevelElements).
    * @return integer length of level.
    */
   public int getLength() {
      return elements.size();
   }
   
   /**
    * Checks whether a give set of coordinates collides with the LevelElements in this Level.
    * @param x The X coordinate to check.
    * @param y The Y coordinate to check.
    * @return A Collision object containing collision data of the best collision
    * (collision that snaps the player the shortest distance) if applicable, otherwise null.
    */
   public Collision checkCollisions(int x, int y) {
      Collision bestCollision = null;
      int bestDegree = Integer.MAX_VALUE;
      
      for(LevelElement element : elements) {
         Collision c = element.checkCollisions(x, y);
         if(c != null && c.getDegree() < bestDegree) {
            bestCollision = c;
            bestDegree = c.getDegree();
         }
      }
      
      return bestCollision;
   }
   
   /**
    * Draws a line for each of the faces of illuminated LevelElements.
    * @param g java Graphics object to use to draw faces.
    */
   public void paint(Graphics g) {
      for (LevelElement e : elements) {
         e.drawFaces(g);
      }
      
      g.setColor(Color.green);
      g.fillRect(endX - 5, endY - 5, 10, 10);
   }
   
   /**
    * Removes illumination for all elements in this Level.
    */
   public void clearPaint() {
      for (LevelElement e: elements) {
         e.clearPaint();
      }
   }
   
   // Getters for start and end positions
   
   /**
    * Gets the X coordinate of the starting point of the Level.
    * @return integer value of X coordinate.
    */
   public int getStartX() { return startX; }
   
   /**
    * Gets the Y coordinate of the starting point of the Level.
    * @return integer value of Y coordinate.
    */
   public int getStartY() { return startY; }
   
   /**
    * Gets the X coordinate of the ending point (objective) of the Level.
    * @return integer value of X coordinate.
    */
   public int getEndX() { return endX; }
   
   /**
    * Gets the Y coordinate of the ending point (objective) of the Level.
    * @return integer value of Y coordinate.
    */
   public int getEndY() { return endY; }
}