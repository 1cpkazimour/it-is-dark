// This class represents a player object
public class Player {
   // The x and y positions of the player
   private int x, y;
   // The player's speed
   private int speed = 6;
   
   // Constructor, can specify starting x and y 
   public Player(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   // Move based on keys down
   // This method assumes it is being called 60 times
   // per second
   public void move(boolean key_down, boolean key_up, boolean key_right, boolean key_left) {
      if (key_down) { y += speed; }
      if (key_up) { y -= speed; }
      if (key_right) { x += speed; }
      if (key_left) { x -= speed; }
   }
   
   // getters for position
   public int getX() { return x; }
   public int getY() { return y; }
}