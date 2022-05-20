import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class LevelDesignerPanel extends JPanel {
   // 0 = First corner of box
   // 1 = Second corner of box
   // 2 = Start pos
   // 3 = End pos
   private int mode = 0;
   
   // Is in mark mode? 
   private boolean markMode = false;
   
   // Location of mouse point, if it exists.
   private int mx = -1;
   private int my = -1;
   
   // Location of start
   private int sx = -1;
   private int sy = -1;
   
   // Location of flag
   private int fx = -1;
   private int fy = -1;
   
   private ArrayList<LevelElement> levelElements;
   
   // Mark System
   private int markX = -1;
   private int markY = -1;
   private boolean reuseX = false;
   private boolean reuseY = false;

   public LevelDesignerPanel() {
      this.setFocusable(true);
      
      addMouseListener(new MouseInput());
      addKeyListener(new KeyboardInput());
      
      levelElements = new ArrayList<LevelElement>();
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
   
      // Visual indication that you are in edit mode
      setBackground(new Color(127, 32, 127));
      
      g.setColor(Color.white);
      g.drawString("Z undo rectangle; F change flag pos; P change player pos; M set mark; X reuse x of mark; Y reuse y of mark", 0, 10);
      if (markMode) { g.setColor(Color.blue); g.drawString("Find Nearest Mark", 0, 20); } else {
         if (mode == 0) g.drawString("Place First Corner", 0, 20);
         if (mode == 1) g.drawString("Place Second Corner", 0, 20);
         if (mode == 2) { g.setColor(Color.green); g.drawString("Set Player Start", 0, 20); }
         if (mode == 3) { g.setColor(Color.red); g.drawString("Set Flag Position", 0, 20); }
      }
      
      if (mode == 1) {
         g.setColor(Color.white);
         g.fillRect(mx, my, 3, 3);
         g.drawString(mx + "," + my, mx, my);
      }
      
      if (sx != -1) {
         g.setColor(Color.green);
         g.fillRect(sx, sy, Player.SIZE, Player.SIZE);
      }
      
      if (fx != -1) {
         g.setColor(Color.red);
         g.fillRect(fx, fy, 10, 10);
      }
      
      g.setColor(Color.white);
      for (LevelElement elm : levelElements) {
         g.drawRect(elm.getX1(), elm.getY1(), elm.getWidth(), elm.getHeight());
      }
      
      if (markX != -1) {
         g.setColor(Color.blue);
         g.fillRect(markX - 3, markY - 3, 6, 6);
      }
      
      g.setColor(Color.red);
      if (reuseX) g.drawLine(markX, 0, markX, 720);
      if (reuseY) g.drawLine(0, markY, 1280, markY);
   
      repaint();
   }
   
   // Returns the distance squared between the points
   private int distance(int x1, int y1, int x2, int y2) {
      return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
   }
   
   private class MouseInput implements MouseListener {
      public void mouseClicked(MouseEvent e) {
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
         
         // x and y are coordinates taking into account the mark
      
         if (markMode) {
            if (levelElements.size() == 0) {
               markMode = false; return;
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
         } else if (mode == 0) {
            mx = x;
            my = y; 
            mode = 1;
         } else if (mode == 1) {
            levelElements.add(new LevelElement(
               mx, my, x, y
            ));
            mode = 0;
         } else if (mode == 2) { // player
            sx = x;
            sy = y;
            mode = 0;
         } else if (mode == 3) { // flag
            fx = x;
            fy = y;
            mode = 0;
         }
      }
      
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
   }

   private class KeyboardInput implements KeyListener {
      public void keyTyped(KeyEvent e) {}
      public void keyReleased(KeyEvent e) {
         int c = e.getKeyCode();
         if (c == e.VK_Z) levelElements.remove(levelElements.size() - 1);
         if (c == e.VK_P) mode = 2;
         if (c == e.VK_F) mode = 3;
         if (c == e.VK_X) reuseX = !reuseX;
         if (c == e.VK_Y) reuseY = !reuseY;
         if (c == e.VK_ESCAPE) {
            if (markMode) markMode = false;
            else mode = 0;
         }
         if (c == e.VK_M) markMode = true;
      }
      public void keyPressed(KeyEvent e) {}
   }
}