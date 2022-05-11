import java.util.*;
import java.io.Serializable;

// This class represents a single level object used to hold the level data for the game
public class Level implements Serializable {
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
   
   public Collision[] checkAllCollisions(int x, int y) {
      Collision[] collisions = new Collision[4];
      int counter = 0;
      for(LevelElement element : elements) {
         if(element.checkCollisions(x, y) != null){
            collisions[counter] = element.checkCollisions(x, y);
            counter++;
         }
      }
      return collisions;
   }
   
   // Prints the information about the level and each LevelElement. Used for testing purposes
   public String toString() {
      String output = "Start: (" + startX  + ", " + startY + ") / " + "End: (" + endX  + ", " + endY + ")\n" ;
      for(LevelElement e : elements){
         output += e.toString() + "\n";
      }
      return output;
   }
   
   // Getters for start and end positions
   public int getStartX() { return startX; }
   public int getStartY() { return startY; }
   public int getEndX() { return endX; }
   public int getEndY() { return endY; }
}