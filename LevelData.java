import java.io.*;
import java.util.*;

public class LevelData{
   private Level[] levels;
   
   public LevelData(String path){
      try{
         FileInputStream file = new FileInputStream(path);
         ObjectInputStream data = new ObjectInputStream(file);
         levels = (Level[]) data.readObject();
         data.close();
         file.close();
      }catch(Exception e){
         System.out.println(e);
         Level error = new Level(-1,-1,-1,-1);
         levels = new Level[1];
         levels[0]= error;
      }
   }
   
   public Level get(){
      return levels[(int) (levels.length * Math.random())];
   }
   
   public Level get(int id){
      return levels[id - 1];
   }
   
   public int length(){
      return levels.length;
   }
   
   public static void main(String[] args){
		
   }
}