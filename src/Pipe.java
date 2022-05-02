import java.awt.*;

public class Pipe {
    private Rectangle bigRectangle;
    private Rectangle smallRectangle;

    public static final int BIG_REC_WIDTH = 60;
    public static final int SMALL_REC_HEIGHT = 20;


    public Pipe(int x, int y, int height, boolean isSmallDown) {
        this.bigRectangle = new Rectangle(x, y, BIG_REC_WIDTH, height, Color.GREEN);
        int ySmallRectangle;
        if (isSmallDown) {
            ySmallRectangle = y + height;
        } else {
            ySmallRectangle = y - SMALL_REC_HEIGHT;
        }
        this.smallRectangle = new Rectangle(x - (SMALL_REC_HEIGHT / 4), ySmallRectangle, BIG_REC_WIDTH + (SMALL_REC_HEIGHT / 2), SMALL_REC_HEIGHT, Color.green);
    }

    public void paint(Graphics graphics) {
        this.bigRectangle.paint(graphics);
        this.smallRectangle.paint(graphics);
    }

    public void moveLeft() {
        this.smallRectangle.moveLeft();
        this.bigRectangle.moveLeft();
    }

    public Rectangle getSmallRectangle() {
        return smallRectangle;
    }

    public Rectangle getBigRectangle() {
        return bigRectangle;
    }
}