import java.io.*;
import java.util.*;

// This class represents an object containg level data for the game
public class LevelData{
   // Array of all level data
   private Level[] levels;
   
   // Creates a new LevelData object by loading levels from file at specified path
   public LevelData(String path){
      try{
         // attempt to load levels from file
         FileInputStream file = new FileInputStream(path);
         ObjectInputStream data = new ObjectInputStream(file);
         levels = (Level[]) data.readObject();
         data.close();
         file.close();
      }catch(Exception e){
         // If file does not exist or is wrong type, add blank level
         System.out.println(e);
         Level error = new Level(-1,-1,-1,-1);
         levels = new Level[1];
         levels[0]= error;
      }
   }
   
   // Returns random Level from array
   public Level get(){
      return levels[(int) (levels.length * Math.random())];
   }
   
   //Returns level of specified number. First level is 1.
   public Level get(int id){
      return levels[id - 1];
   }
   
   //Returns the number of levels
   public int length(){
      return levels.length;
   }
   
   public static void main(String[] args){
		
   }
}