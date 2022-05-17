import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LevelDesignerPanel extends JPanel {
   public LevelDesignerPanel() {
      this.setFocusable(true);
      
      addMouseListener(new GameInput());
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
   
      setBackground(Color.blue);
   
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