import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main extends JFrame {
   // Go into edit mode?
   public static final boolean editMode = true;

   public Main(ScreenSize s) {
      // Setup self as window
      this.setTitle("it is dark.");
      this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Good window hygene
      //this.setSize(new Dimension(s.getWidth(), s.getHeight()));
      this.setSize(new Dimension(1280, 720));
      this.setResizable(false);
      this.setLocationRelativeTo(null);
      this.getContentPane().setLayout(new BorderLayout());
      if (editMode) {
         this.getContentPane().add(new LevelDesignerPanel());
      } else {
         this.getContentPane().add(new GamePanel()); // GamePanel holds game logic      
      }
      this.setVisible(true);
   }

   public static void main(String[] args) {
      ScreenSize s = new ScreenSize();
      new Main(s);
   }

}
