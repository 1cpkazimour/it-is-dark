import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePanel extends JPanel {
      // Challenge, how could make sure the player starts in the center of the window every time, even if
      // screen size changes on startup?
      private int xCoordinate = 0; // The initial coordinate of the pacman
      private int yCoordinate = 0; // The starting coordinate of the pacman
      
      boolean key_right, key_left, key_down, key_up; // Booleans to track current key press
      
      public GamePanel() {
         this.setFocusable(true); // Events only fire for a component if it has focus, so this call is necessary
         addKeyListener(new GameInput()); // You are adding a KeyListener TO the GamePanel
      }
   
     // Repaint the canvas and move pacman player based on which key press
      public void paintComponent(Graphics g) {
         super.paintComponent(g); // Calls the parent class' method to repaint
         setBackground(Color.white); // Set the background to white
         g.fillRect(xCoordinate, yCoordinate, 10, 10);
      
         // Challenge: How could you modify this logic to load a different image at the time
         // of a key press?
         // Challenge: How to make it so that the pacman doesn't go outside the borders?
         
         // Press DOWN
         if (key_down) { yCoordinate++; }
      
         // Press UP
         if (key_up) { yCoordinate--; }
      
         // Press RIGHT
         if (key_right) { xCoordinate++; }
      
         // Press LEFT
         if (key_left) { xCoordinate--; }
      
         // Add a delay between key presses
         for (int index = 0; index < 50000000; index++) {}
      
         repaint();
      }
      
      // Private class to intantiate a KeyListener object
      // Called from the GamePanel contructor
         private class GameInput implements KeyListener {
         
         //Challenge - How can you modify this and other methods so that
         // that each keypress down truly only moves the pacman a few pixels
         // until the next key press?
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