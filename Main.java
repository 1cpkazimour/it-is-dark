import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main extends JFrame {
   public Main() {
      // Setup self as window
      this.setTitle("it is dark.");
      this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Good window hygene
      this.setSize(new Dimension(600, 600)); // Challenge: How could you make the window take up the size of the screen?
      this.setLocationRelativeTo(null);
      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(new GamePanel()); // GamePanel holds game logic
      this.setVisible(true);
   }

   public static void main(String[] args) {
      new Main();
   }
   
}