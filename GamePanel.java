import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// Game logic here

public class GamePanel extends JPanel {
      private Player player = null;
      //private LevelData levels = new LevelData("levels.scp");
      //private Level currentLevel = null;
      
      private boolean key_right, key_left, key_down, key_up; // Booleans to track current key press
      
      public GamePanel() {
         this.setFocusable(true); // Events only fire for a component if it has focus, so this call is necessary
         
         // Make a player
         player = new Player(0, 0);
         
         // Handle Inputs, see below
         addKeyListener(new GameInput());
         
         // Update function, runs 60 times per second
         int delay = 1000/60;
         ActionListener update = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               player.move(key_down, key_up, key_right, key_left);
            }
         };
         new Timer(delay, update).start();
      }
   
     // Repaint the canvas and move pacman player based on which key press
      public void paintComponent(Graphics g) {
         super.paintComponent(g); // Calls the parent class' method to repaint
         setBackground(Color.white); // Set the background to white
         
         // Draw the player
         g.fillRect(player.getX(), player.getY(), 10, 10);
      
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
         }
      
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == e.VK_DOWN) key_down = true;
            if (e.getKeyCode() == e.VK_UP) key_up = true;
            if (e.getKeyCode() == e.VK_RIGHT) key_right = true;
            if (e.getKeyCode() == e.VK_LEFT) key_left = true;
         }
      }
}