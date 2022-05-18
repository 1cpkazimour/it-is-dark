import java.awt.*;

public class ScreenSize {
    private int height;
    private int width;

    public ScreenSize() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = (int) size.getWidth(); // screen width
        this.height = (int) size.getHeight(); // screen height
    }

    public int getWidth() {
        if (width <= 1280 && height <= 720) {
            return 1280;
        } else if (width <= 1920 && height <= 1080) {
            return 1920;
        }
        return 2560;
    }

    public int getHeight() {
        if (width <= 1280 && height <= 720) {
            return (int) 720;
        } else if (width <= 1920 && height <= 1080) {
            return (int) 1080;
        }
        return (int) 1440;
    }
}