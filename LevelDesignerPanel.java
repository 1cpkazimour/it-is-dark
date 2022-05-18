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
   
   private ArrayList<LevelElement> levelElements;

   public LevelDesignerPanel() {
      this.setFocusable(true);
      
      addMouseListener(new MouseInput());
      
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
      
      for (LevelElement elm : levelElements) {
         g.fillRect(elm.getX1(), elm.getY1(), elm.getWidth(), elm.getHeight());
      }
   
      repaint();
   }
   
   private class MouseInput implements MouseListener {
      public void mouseClicked(MouseEvent e) {
         System.out.println("Got here");
         if (mode == 0) {
            mx = e.getX();
            my = e.getY();
            mode = 1;
         } else if (mode == 1) {
            levelElements.add(new LevelElement(
               mx, my, e.getX(), e.getY()
            ));
            mode = 0;
         }
      }
      
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
   }

}