import java.awt.*;
import javax.swing.*;

/**
 * Entry point of program (contains main method).
 */

public class Main extends JFrame {
   /** 
    * Go into edit mode?
    */
   public static final boolean editMode = false;

   /**
    * constructs a Main object.
    */
   public Main() {
      // Setup self as window
      this.setTitle("it is dark.");
      this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Good window hygene
      // this.setSize(new Dimension(s.getWidth(), s.getHeight()));
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

   /**
    * Main method of the game.
    */
   public static void main(String[] args) {
      new Main();
   }

}
