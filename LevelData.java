import java.io.*;
import java.util.*;

// This class represents an object containg level data for the game
public class LevelData {
   // Array of all level data
   private Level[] levels;
   
   // Creates a new LevelData object by loading levels from file at specified path
   public LevelData(String path) {
      try {
         // attempt to load levels from file
         ObjectInputStream data = new ObjectInputStream(LevelData.class.getResourceAsStream(path));
         levels = (Level[]) data.readObject();
         data.close();
      } catch(Exception e) {
         // If file does not exist or is wrong type, add blank level
         System.out.println(e);
         Level error = new Level(-1,-1,-1,-1);
         levels = new Level[1];
         levels[0]= error;
      }
   }
   
   // Returns random Level from array
   public Level get() {
      return levels[(int) (levels.length * Math.random())];
   }
   
   // Returns level of specified number. First level is 1.
   public Level get(int id) {
      return levels[id - 1];
   }
   
   // Returns the number of levels
   public int length(){
      return levels.length;
   }
   
   // Serializes levels and writes them to a file at the specified path
   public static void export(Level[] levels, String path) {
      try {
         FileOutputStream file = new FileOutputStream(path);
         ObjectOutputStream out = new ObjectOutputStream(file);
         out.writeObject(levels);
         out.flush();
         out.close();
         } catch(Exception e) {
            System.out.println(e);
         }
   }
   
   public static void main(String[] args) {
      LevelData test = new LevelData("levels.ser");
      System.out.println(test.get(1));
      
   }
}