import java.awt.*;
import java.util.Random;

public class Obstacle {
    private Pipe upper;
    private Pipe lower;

    public static final int STAR_X = 1000;
    public static final int SPACE_SIZE = 150;
    public static final int MIN_PIPE_HEIGHT = 50;

    public Obstacle () {

        Random random = new Random();
        int randomHeight = random.nextInt(Window.WINDOW_HEIGHT - (SPACE_SIZE + MIN_PIPE_HEIGHT*2) ) + MIN_PIPE_HEIGHT;
        this.upper = new Pipe(STAR_X,0,randomHeight,true);
        this.lower = new Pipe(STAR_X, randomHeight + SPACE_SIZE ,Window.WINDOW_HEIGHT - (randomHeight + SPACE_SIZE),false);
    }

    public void paint (Graphics graphics) {
        this.upper.paint(graphics);
        this.lower.paint(graphics);
    }

    public void moveLeft () {
        this.lower.moveLeft();
        this.upper.moveLeft();
    }

    public boolean end () {
        boolean end = false;
        if (this.upper.getSmallRectangle().getX() < 0) {
            end = true;
        }
        return end;
    }

    public Pipe getUpper() {
        return upper;
    }

    public Pipe getLower() {
        return lower;
    }
}
