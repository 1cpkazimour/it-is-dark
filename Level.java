import java.util.*;
import java.io.Serializable;

public class Level implements Serializable{
   private int startX;
   private int startY;
   private int endX;
   private int endY;
   private ArrayList<LevelElement> elements;
   
   public Level(int sX, int sY, int eX, int eY){
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
      elements = new ArrayList<LevelElement>();
   }
   
   public Level(int sX, int sY, int eX, int eY, LevelElement[] e){
      startX = sX;
      startY = sY;
      endX = eX;
      endY = eY;
      elements = new ArrayList<LevelElement>();
      for(LevelElement element : e){
         elements.add(element);
      }
   }
   
   public void addElement(LevelElement e){
      elements.add(e);
   }
   
   public ArrayList<LevelElement> getElements(){
      return elements;
   }
   
   public LevelElement getElement(int index){
      return elements.get(index);
   }
   
   public int getLength(){
      return elements.size();
   }
   
   public String toString(){
      String output = "Start: (" + startX  + ", " + startY + ") / " + "End: (" + endX  + ", " + endY + ")\n" ;
      for(LevelElement e : elements){
         output += e.toString() + "\n";
      }
      return output;
   }
}