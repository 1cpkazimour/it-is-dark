import java.io.*;
import java.util.*;

/**
 * Represents the full set of Levels in the game.
 */
public class LevelData {
   
   /**
    * Array containing all Levels in the game.
    */
   private Level[] levels;
   
   /**
    * Creates a new LevelData object and fills it with Levels from
    * a serialized array of Levels at a given path.
    * @param path Path of .ser file on computer.
    */
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
   
   /**
    * Creates a new LevelData object and fills it with Levels from
    * a specified array of Levels.
    * @param levels Array of Levels to load into LevelData.
    */
   public LevelData(Level[] levels) {
      this.levels = levels;
   }
   
   /**
    * Gets all of the levels in this LevelData.
    * @return Array of Levels.
    */
   public Level[] getLevels() {
      return levels;
   }
   
   /**
    * Gets a random Level.
    * @return Random Level.
    */
   public Level get() {
      return levels[(int) (levels.length * Math.random())];
   }
   
   /**
    * Gets a specific Level in the internal Array based upon a given id.
    * @param id Position in the internal Array, starting at 1 (index + 1).\
    * @return Level at the given id.
    */
   public Level get(int id) {
      return levels[id - 1];
   }
   
   /**
    * Gets the number of Levels.
    * @return The number of levels.
    */
   public int length(){
      return levels.length;
   }
   
   /**
    * Serializes Levels and saves them in a file at a given path.
    * @param levels Array of Levels to export.
    * @param path Path to save file on computer.
    */
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
}