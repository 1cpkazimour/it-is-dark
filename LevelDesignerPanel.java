import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LevelDesignerPanel extends JPanel {
   // 0 = First corner of box
   // 1 = Second corner of box
   // 2 = Start pos
   // 3 = End pos
   int mode = 0;

   public LevelDesignerPanel() {
      this.setFocusable(true);
      
      addMouseListener(new GameInput());
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
   
      // Visual indication that you are in edit mode
      setBackground(new Color(127, 32, 127));
      
      g.setColor(Color.black);
      g.drawString("Mode: " + mode, 0, 10);
   
      repaint();
   }
   
   // Private class to intantiate a Mouse object
   // Called from the GamePanel contructor
   private class GameInput implements MouseListener {
      public void mouseClicked(MouseEvent e) {
         System.out.println(e);
      }
      
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
   }

}