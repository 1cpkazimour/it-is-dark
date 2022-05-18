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
      g.drawString("Mode: " + mode + "; Z undo rectangle; F change flag pos; P change player pos", 0, 10);
      
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
      
      for (LevelElement elm : levelElements) {
         g.setColor(Color.white);
         g.fillRect(elm.getX1(), elm.getY1(), elm.getWidth(), elm.getHeight());
      }
   
      repaint();
   }
   
   private class MouseInput implements MouseListener {
      public void mouseClicked(MouseEvent e) {
         if (mode == 0) {
            mx = e.getX();
            my = e.getY();
            mode = 1;
         } else if (mode == 1) {
            levelElements.add(new LevelElement(
               mx, my, e.getX(), e.getY()
            ));
            mode = 0;
         } else if (mode == 2) {
            sx = e.getX();
            sy = e.getY();
            mode = 0;
         } else if (mode == 3) {
            fx = e.getX();
            fy = e.getY();
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
      }
      public void keyPressed(KeyEvent e) {}
   }
}