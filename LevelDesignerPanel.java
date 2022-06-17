import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

/**
 * This class is used to design levels, and is used instead of GamePanel
 * when Main.editMode is set to true
 */

public class LevelDesignerPanel extends JPanel {
   /**
    * What does clicking place? (overridden by markMode)
    * 0 = First corner of box
    * 1 = Second corner of box
    * 2 = Start pos
    * 3 = End pos
    */
   private int mode = 0;
   
   /**
    * Type of object to place
    * 0 = LevelElement
    * 1 = Spike
    * 2 = SlopeLeft
    * 3 = SlopeRight
    */
   private int objectType = 0;
   
   /**
    * Will clicking change the position of the mark, instead of following mark?
    */
   private boolean markMode = false;
   
   /**
    * X position of the first corner of the box (or -1)
    */
   private int mx = -1;
   /**
    * Y position of the first corner of the box (or -1)
    */
   private int my = -1;
   
   /**
    * X position of the player start position
    */
   private int sx = -1;
   /**
    * Y position of the player start position
    */
   private int sy = -1;
   
   /**
    * X position of the flag (end of the level)
    */
   private int fx = -1;
   /**
    * Y position of the flag (end of the level)
    */
   private int fy = -1;
   
   /**
    * The elements of the level currently being edited
    */
   private ArrayList<LevelElement> levelElements;
   /**
    * The list of Levels -- Turns into a LevelData when the data is exported
    */
   private ArrayList<Level> levels;
   /**
    * The index (into levels) of the level we're editing
    */
   private int levelIndex = 0;
   
   /**
    * The X position of the mark
    */
   private int markX = -1;
   /**
    * The Y position of the mark
    */
   private int markY = -1;
   /**
    * Is X locking on?
    */
   private boolean reuseX = false;
   /**
    * Is Y locking on?
    */
   private boolean reuseY = false;

   /**
    * Construct a LevelDesignerPanel
    */
   public LevelDesignerPanel() {
      this.setFocusable(true);
      
      addMouseListener(new MouseInput());
      addKeyListener(new KeyboardInput());
      
      // These are empty until they are loaded
      levelElements = new ArrayList<LevelElement>();
      levels = new ArrayList<Level>();
   }
   
   /**
    * Display the level editor to the screen
    */
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
   
      // Visual indication that you are in edit mode
      setBackground(new Color(127, 32, 127));
      
      g.setColor(Color.white);
      // Awful tutorial text
      g.drawString("Z undo rectangle; F change flag pos; P change player pos; M set mark; X reuse x of mark; Y reuse y of mark; L to load from file; S to save to file; Arrow keys to switch between levels; T to change type of placed object", 0, 10);
      g.drawString("Level " + levelIndex, 0, 30);
      if (markMode) {
         g.setColor(Color.yellow);
         g.drawString("Find Nearest Mark", 0, 20);
      } else {
         if (mode == 0) g.drawString("Place First Corner", 0, 20);
         if (mode == 1) g.drawString("Place Second Corner", 0, 20);
         if (mode == 2) {
            g.setColor(Color.green);
            g.drawString("Set Player Start", 0, 20);
         }
         if (mode == 3) {
            g.setColor(Color.red);
            g.drawString("Set Flag Position", 0, 20);
         }
      }
      g.setColor(Color.white);
      if (objectType == 0) g.drawString("Placing LevelElement", 140, 20);
      if (objectType == 1) g.drawString("Placing Spike", 140, 20);
      if (objectType == 2) g.drawString("Placing Slope (left)", 140, 20);
      if (objectType == 3) g.drawString("Placing Slope (right)", 140, 20);
      
      // Draw the first corner of the rectangle, if you're placing a second corner
      if (mode == 1) {
         g.setColor(Color.white);
         g.fillRect(mx, my, 3, 3);
         g.drawString(mx + "," + my, mx, my);
      }
      
      // If a start position exists, display it
      if (sx != -1) {
         g.setColor(Color.green);
         g.fillRect(sx, sy, Player.SIZE, Player.SIZE);
      }
      
      // If a flag position exists, display it
      if (fx != -1) {
         g.setColor(Color.red);
         g.fillRect(fx - 5, fy - 5, 10, 10);
      }

      // Draw the level in a wireframe like manner
      for (LevelElement elm : levelElements) {
         // Draw Spikes in red
         if (elm instanceof Spike) {
            g.setColor(Color.red);
         } else {
            g.setColor(Color.white);
         }

         g.drawRect(elm.getX1(), elm.getY1(), elm.getWidth(), elm.getHeight());

         // Draw slopes with a slope
         if (elm instanceof Slope) {
            Slope e = (Slope)elm;
            if (e.getDirection() == Slope.Direction.LEFT) {
               g.drawLine(e.getX1(), e.getY2(), e.getX2(), e.getY1());
            } else {
               g.drawLine(e.getX1(), e.getY1(), e.getX2(), e.getY2());
            }
         }
      }

      // If the mark exists, show it
      if (markX != -1) {
         g.setColor(Color.yellow);
         g.fillRect(markX - 3, markY - 3, 6, 6);
      }
      
      // Draw a visual indicator that mark mode is active
      g.setColor(Color.red);
      if (reuseX) g.drawLine(markX, 0, markX, 720);
      if (reuseY) g.drawLine(0, markY, 1280, markY);
   
      repaint();
   }
   
   /**
    * Returns the distance squared between the points.
    * Used for mark calculations
    * @param x1 X coordinate of first point
    * @param y1 Y coordinate of first point
    * @param x2 X coordinate of second point
    * @param y2 Y coordinate of second point
    * @return the distance squared between the two points
    */
   private int distance(int x1, int y1, int x2, int y2) {
      return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
   }
   
   /**
    * Save the level in the levels arrayList
    */
   private void saveLevel() {
      Level l = new Level(sx, sy, fx, fy, levelElements);
      if (levelIndex >= levels.size()) {
         levels.add(l);
      } else {
         levels.set(levelIndex, l);
      }
   }
   /**
    * Load the level from the current index
    */
   public void loadLevel() {
      if (levelIndex >= levels.size()) {
         levelElements = new ArrayList<LevelElement>();
         sx = sy = fx = fy = -1;
      } else {
         Level l = levels.get(levelIndex);
         levelElements = l.getElements();
         sx = l.getStartX();
         sy = l.getStartY();
         fx = l.getEndX();
         fy = l.getEndY();
      }
      markX = markY = -1;
   }
   
   /**
    * Input handler for mouse clicks
    */
   private class MouseInput implements MouseListener {
      /**
       * Callback for when the mouse is clicked
       */
      public void mouseClicked(MouseEvent e) {                 
         // set x and y to coordinates taking into account the mark
         int x;
         if (reuseX) {
            x = markX;
         } else {
            x = e.getX();
         }
         
         int y;
         if (reuseY) {
            y = markY;
         } else {
            y = e.getY();
         }
      
         // This click is placing a mark
         if (markMode) {
            // Can't place a mark if there aren't any levelElements for it to snap to
            if (levelElements.size() == 0) {
               markMode = false;
               return;
            }
         
            // Find nearest mark
            markX = levelElements.get(0).getX1();
            markY = levelElements.get(0).getY1();
            // Mouse positions
            int mx = e.getX();
            int my = e.getY();
            for (LevelElement elm: levelElements) {
               int x1 = elm.getX1();
               int y1 = elm.getY1();
               int x2 = elm.getX2();
               int y2 = elm.getY2();
               
               // If point 1 is closer to the mouse than the current
               // mark is, replace it
               if (distance(x1, y1, mx, my) < distance(markX, markY, mx, my)) {
                  markX = x1; markY = y1;
               }
               
               // If point 2 is closer to the mouse than the current
               // mark is, replace it
               if (distance(x2, y2, mx, my) < distance(markX, markY, mx, my)) {
                  markX = x2; markY = y2;
               }
               
               // Combinations of points 1 and 2 for the other two corners
               
               if (distance(x1, y2, mx, my) < distance(markX, markY, mx, my)) {
                  markX = x1; markY = y2;
               }

               if (distance(x2, y1, mx, my) < distance(markX, markY, mx, my)) {
                  markX = x2; markY = y1;
               }
            }
            
            markMode = false;
         } else if (mode == 0) { // Setting position of first corner
            mx = x;
            my = y; 
            mode = 1; // Now place second corner
         } else if (mode == 1) { // Placing second corner and creating levelElement based on the objectType
            if (objectType == 1) { // Place a Spike
               levelElements.add(new Spike(mx, my, x, y ));
            } else if (objectType == 2) { // Place a left facing Slope
               levelElements.add(new Slope(mx, my, x, y, Slope.Direction.LEFT));
            } else if (objectType == 3) { // Place a right facing Slope
               levelElements.add(new Slope(mx, my, x, y, Slope.Direction.RIGHT));
            } else { // Just a regular old LevelElement
               levelElements.add(new LevelElement(mx, my, x, y));
            }
            mode = 0;
         } else if (mode == 2) { // Setting position of the player
            sx = x;
            sy = y;
            mode = 0;
         } else if (mode == 3) { // Setting position of the flag
            fx = x;
            fy = y;
            mode = 0;
         }
      }
      
      // These don't do anything, but we have to define them
      // to implement the interface
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
   }

   /**
    * Input handler for keyboard presses
    */
   private class KeyboardInput implements KeyListener {
      /**
       * Callback for when a key is released
       * (all key actions are triggered on key release)
       */
      public void keyReleased(KeyEvent e) {
         int c = e.getKeyCode();
         if (c == e.VK_Z) levelElements.remove(levelElements.size() - 1); // undo key (i.e. Ctrl-Z)
         if (c == e.VK_P) mode = 2; // Player place mode
         if (c == e.VK_F) mode = 3; // Flag place mode
         if (c == e.VK_X) reuseX = !reuseX; // toggle X-locking
         if (c == e.VK_Y) reuseY = !reuseY; // toggle Y-locking
         if (c == e.VK_T) objectType = (objectType + 1) % 4; // switch the Type of object being placed
         if (c == e.VK_Q) levelElements.add(new LevelElement(0, 590, 1281, 720)); // Convenience feature to place a floor
         if (c == e.VK_ESCAPE) { // General purpose reset/cancel button
            if (markMode) markMode = false;
            else mode = 0;
         }
         if (c == e.VK_M) markMode = !markMode; // toggle Mark mode
         if (c == e.VK_L) { // Load the levels
            LevelData l = new LevelData("levels.ser");
            levels = new ArrayList<Level>(Arrays.asList(l.getLevels()));
            levelIndex = 0;
            loadLevel();
         }
         if (c == e.VK_S) { // Save the levels
            // Save the current levels
            saveLevel();
            // Remove all levels with no LevelElements
            // The forbidden lambda :O
            levels.removeIf(level -> level.getElements().size() == 0);
            LevelData.export(levels.toArray(new Level[levels.size()]), "levels.ser");
         }
         // Switch between levels
         if (c == e.VK_RIGHT) {
            saveLevel();
            levelIndex++;
            loadLevel();
         }
         if (c == e.VK_LEFT && levelIndex > 0) {
            saveLevel();
            levelIndex--;
            loadLevel();
         }
      }

      // These don't do anything, but we have to define them
      // to implement the interface  
      public void keyTyped(KeyEvent e) {}
      public void keyPressed(KeyEvent e) {}
   }
}