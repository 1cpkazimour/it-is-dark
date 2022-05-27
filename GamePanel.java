import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// Game logic here

public class GamePanel extends JPanel {
      private Player player = null;
      private LevelData levels = new LevelData("levels.ser");
      private Level level = null;
      
      // Represents whether the game is in the menus or not
      private boolean isGaming = false;
      
      private boolean key_right, key_left, key_up, key_space, key_w, key_a, key_d; // Booleans to track current key press
      
      public GamePanel() {
         this.setFocusable(true); // Events only fire for a component if it has focus, so this call is necessary
         
         // Handle Inputs, see below
         addKeyListener(new GameInput());
         
         // Update function, runs 60 times per second
         int delay = 1000/60;
         ActionListener update = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               if (isGaming) update();
            }
         };
         new Timer(delay, update).start();
      }
      
      private void newLevel() {
         System.out.println("Got here");
         level = levels.get();
         level.clearPaint();
         player = new Player(level.getStartX(), level.getStartY());
      }
      
      private void update() {
         player.move(key_up || key_space || key_w, key_right || key_d, key_left || key_a, level);
      }
   
      // Repaint the canvas
      public void paintComponent(Graphics g) {
         super.paintComponent(g); // Calls the parent class' method to repaint
         
         if (isGaming) {
            setBackground(Color.black); // Set the background to black
            
            // Draw the player
            level.paint(g);
            player.draw(g);
            
         } else {
            setBackground(Color.white);
            
            g.setColor(Color.black);
            g.drawString("it is dark.", 100, 100);
            g.drawString("press space.", 100, 200);
            
            if (key_space) {
               isGaming = true;
               newLevel();
            }
         }
         
         
         repaint();
      }
      
      // Private class to intantiate a KeyListener object
      // Called from the GamePanel contructor
      private class GameInput implements KeyListener {
         
         public void keyTyped(KeyEvent e) {}
      
         public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == e.VK_UP) key_up = false;
            if (e.getKeyCode() == e.VK_RIGHT) key_right = false;
            if (e.getKeyCode() == e.VK_LEFT) key_left = false;
            if (e.getKeyCode() == e.VK_SPACE) key_space = false;
            if (e.getKeyCode() == e.VK_W) key_w = false; 
            if (e.getKeyCode() == e.VK_A) key_a = false; 
            if (e.getKeyCode() == e.VK_D) key_d = false; 
            
         }
      
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
