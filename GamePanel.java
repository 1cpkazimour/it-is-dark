import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Represents the screen area within the game window.
 */
public class GamePanel extends JPanel {
      
      /**
       * The player object representing the current player.
       */
      private Player player = null;
      
      /**
       * The levels of this game (loaded from file levels.ser).
       */
      private LevelData levels = new LevelData("levels.ser");
      
      /**
       * The current level.
       */
      private Level level = null;
      
      /**
       * Whether or not the game is on the menu screen.
       */
      private boolean isGaming = false;
      
      /**
       * Whether or not each movement key is pressed.
       */
      private boolean key_right, key_left, key_up, key_space, key_w, key_a, key_d;
      
      /**
       * The font used to display the title screen
       */
      private Font font;
            
      /**
       * Makes a new GamePanel.
       */
      public GamePanel() {
         this.setFocusable(true); // Events only fire for a component if it has focus, so this call is necessary
         
         // Load the font
         try {
            font = Font.createFont(Font.TRUETYPE_FONT, GamePanel.class.getResourceAsStream("iosevka-regular.ttf"));
            font = font.deriveFont((float)40); // Point size (cast is required because of overloading)
         } catch (Exception e) {
            System.out.println("Error loading file " + e);
            font = null;
         }
         
         // Handle Inputs, see below
         addKeyListener(new GameInput());
         
         // Update function, runs 60 times per second
         int delay = 1000/60;
         ActionListener update = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               if (isGaming) update();
            }
         };
         Timer updateTimer = new Timer(delay, update);
         updateTimer.setCoalesce(false);
         updateTimer.start();
      }
      
      /**
       * Loads a new random level.
       */
      private void newLevel() {
         level = levels.get();
         level.clearPaint();
         player = new Player(level.getStartX(), level.getStartY());
      }
      
      /**
       * Loads a new level if the player beats the current one.
       */
      private void update() {
         boolean win = player.move(key_up || key_space || key_w, key_right || key_d, key_left || key_a, level);
         if (win) newLevel();
      }
   
      /**
       * Draws all elements of the game onto the screen.
       * @param g Graphics object used to draw game.
       */
      public void paintComponent(Graphics g) {
         super.paintComponent(g); // Calls the parent class' method to repaint
         
         if (isGaming) {
            setBackground(Color.black); // Set the background to black
            
            // Draw the player
            level.paint(g);
            player.draw(g);
            
         } else {
            if (font != null) g.setFont(font);
            setBackground(Color.white);
            
            g.setColor(Color.black);
            g.drawString("it is dark.", 500, 300);
            g.drawString("press space.", 500, 400);
            
            if (key_space) {
               isGaming = true;
               newLevel();
            }
         }
         
         repaint();
      }
      
      /**
       * Private class to instantiate a KeyListener object
       * Called from the GamePanel contructor
       */
      private class GameInput implements KeyListener {
         
         /**
          * Checks if a key is typed (unused)
          * @param e KeyEvent to grab info from.
          */
         public void keyTyped(KeyEvent e) {}
      
         /**
          * Checks if a movement key has been released.
          * @param e KeyEvent to analzye.
          */
         public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == e.VK_UP) key_up = false;
            if (e.getKeyCode() == e.VK_RIGHT) key_right = false;
            if (e.getKeyCode() == e.VK_LEFT) key_left = false;
            if (e.getKeyCode() == e.VK_SPACE) key_space = false;
            if (e.getKeyCode() == e.VK_W) key_w = false; 
            if (e.getKeyCode() == e.VK_A) key_a = false; 
            if (e.getKeyCode() == e.VK_D) key_d = false; 
            
         }
      
         /**
          * Checks if a movement key has been pressed.
          * @param e KeyEvent to analzye.
          */
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == e.VK_UP) key_up = true;
            if (e.getKeyCode() == e.VK_RIGHT) key_right = true;
            if (e.getKeyCode() == e.VK_LEFT) key_left = true;
            if (e.getKeyCode() == e.VK_SPACE) key_space = true; 
            if (e.getKeyCode() == e.VK_W) key_w = true; 
            if (e.getKeyCode() == e.VK_A) key_a = true; 
            if (e.getKeyCode() == e.VK_D) key_d = true;
            
            // Add a next level key
            if (isGaming && e.getKeyCode() == e.VK_G) /* give up */ newLevel();
         }
      }
}
