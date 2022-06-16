import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * It is dark is a simple platformer game with basic graphics and unique
 * gameplay.
 * In each level, the player starts with a black screen, and has to slowly
 * reveal the level
 * by moving around to reveal the surfaces in the level.
 * 
 * @author Cameron Kazimour, Julian Shah, Rohan Kapur
 */

public class Main extends JFrame {
   // Go into edit mode?
   public static final boolean editMode = false;

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

   public static void main(String[] args) {
      new Main();
   }

}
