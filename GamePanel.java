import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// Game logic here

public class GamePanel extends JPanel {
      private Player player = null;
      //private LevelData levels = new LevelData("levels.scp");
      //private Level currentLevel = null;
      
      private boolean key_right, key_left, key_down, key_up, key_space, key_w, key_a, key_d; // Booleans to track current key press
      
      public GamePanel() {
         this.setFocusable(true); // Events only fire for a component if it has focus, so this call is necessary
         
         // Make a player
         player = new Player(0, 200);
         
         // Handle Inputs, see below
         addKeyListener(new GameInput());
         
         // Update function, runs 60 times per second
         int delay = 1000/60;
         ActionListener update = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               Level level = new Level(-1, -1, -1, -1, new LevelElement[]{new LevelElement(100,440,200,450)});
               player.move(key_up || key_space || key_w, key_right || key_d, key_left || key_a, level);
            }
         };
         new Timer(delay, update).start();
      }
   
      // Repaint the canvas
      public void paintComponent(Graphics g) {
         super.paintComponent(g); // Calls the parent class' method to repaint
         setBackground(Color.black); // Set the background to black
         
         // Draw the player
         player.draw(g);
                 
         repaint();
      }
      
      // Private class to intantiate a KeyListener object
      // Called from the GamePanel contructor
      private class GameInput implements KeyListener {
         
         public void keyTyped(KeyEvent e) {}
      
         public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == e.VK_DOWN) key_down = false;
            if (e.getKeyCode() == e.VK_UP) key_up = false;
            if (e.getKeyCode() == e.VK_RIGHT) key_right = false;
            if (e.getKeyCode() == e.VK_LEFT) key_left = false;
            if (e.getKeyCode() == e.VK_SPACE) key_space = false;
            if (e.getKeyCode() == e.VK_W) key_w = false; 
            if (e.getKeyCode() == e.VK_A) key_a = false; 
            if (e.getKeyCode() == e.VK_D) key_d = false; 
            
         }
      
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == e.VK_DOWN) key_down = true;
            if (e.getKeyCode() == e.VK_UP) key_up = true;
            if (e.getKeyCode() == e.VK_RIGHT) key_right = true;
            if (e.getKeyCode() == e.VK_LEFT) key_left = true;
            if (e.getKeyCode() == e.VK_SPACE) key_space = true; 
            if (e.getKeyCode() == e.VK_W) key_w = true; 
            if (e.getKeyCode() == e.VK_A) key_a = true; 
            if (e.getKeyCode() == e.VK_D) key_d = true; 
         }
      }
}