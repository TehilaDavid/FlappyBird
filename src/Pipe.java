import java.awt.*;

public class Pipe {
    private Rectangle bigRectangle;
    private Rectangle smallRectangle;

    public static final int BIG_REC_WIDTH = 60;


    public Pipe(int x, int y, int height, boolean isSmallDown) {
        this.bigRectangle = new Rectangle(x, y, BIG_REC_WIDTH, height, Color.GREEN);
        if (isSmallDown) {
            this.smallRectangle = new Rectangle(x - 5, y + height, BIG_REC_WIDTH + 10, 20, Color.green);
        } else {
            this.smallRectangle = new Rectangle(x - 5, y - 20, BIG_REC_WIDTH + 10, 20, Color.green);
        }
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