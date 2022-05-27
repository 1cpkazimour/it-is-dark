import java.util.*;
import java.io.Serializable;
import java.awt.*;

// This class represents a single level object used to hold the level data for the game
public class Level implements Serializable {
   private static final long serialVersionUID = 6900062191966977137L;

   // Staring X and Y coordinates for player
   private int startX;
   private int startY;
   
   // X and Y coordinates of player objective
   private int endX;
   private int endY;
   
   // ArrayList containing all the elements that make up the level
   private ArrayList<LevelElement> elements;
   
   // Creates new empty level with given start and objective points
   public Level(int sX, int sY, int eX, int eY) {
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
      elements = new ArrayList<LevelElement>();
   }
   
   // Creates new level and fills with LevelElements from a given array
   public Level(int sX, int sY, int eX, int eY, LevelElement[] e) {
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
      elements = new ArrayList<LevelElement>();
      for(LevelElement element : e){
         elements.add(element);
      }
   }
   
   // Creates new level and fills with LevelElements from a given arraylist
   public Level(int sX, int sY, int eX, int eY, ArrayList<LevelElement> e) {
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
      elements = e;
   }
   
   // Adds element to the level
   public void addElement(LevelElement e) {
      elements.add(e);
   }
   
   // Returns ArrayList containing all elements in level
   public ArrayList<LevelElement> getElements() {
      return elements;
   }
   
   // Returns LevelElement at a given index.
   public LevelElement getElement(int index) {
      return elements.get(index);
   }
   
   // Returns the number of elements in level
   public int getLength() {
      return elements.size();
   }
   
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
   
   // Draws lines on the faces of LevelElements that the player has collided with
   public void paint(Graphics g) {
      for (LevelElement e : elements) {
         e.drawFaces(g);
      }
      
      g.setColor(Color.green);
      g.fillRect(endX - 5, endY - 5, 10, 10);
   }
   
   // Prints the information about the level and each LevelElement. Used for testing purposes
   public String toString() {
      String output = "Start: (" + startX  + ", " + startY + ") / " + "End: (" + endX  + ", " + endY + ")\n" ;
      for(LevelElement e : elements){
         output += e.toString() + "\n";
      }
      return output;
   }
   
   public void clearPaint() {
      for (LevelElement e: elements) {
         e.clearPaint();
      }
   }
   
   // Getters for start and end positions
   public int getStartX() { return startX; }
   public int getStartY() { return startY; }
   public int getEndX() { return endX; }
   public int getEndY() { return endY; }
}