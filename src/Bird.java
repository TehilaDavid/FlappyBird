import java.awt.*;
import java.awt.Rectangle;
import java.util.Random;

public class Bird {
    private Oval body;
    private Oval wing;
    private Oval eye;
    private Oval pupil;
    private Oval upperLip;
    private Oval lowerLip;

    private boolean alive;
    private Random random;
    public static final Color[] colors = new Color[]{Color.WHITE, Color.RED, Color.YELLOW, Color.ORANGE, Color.LIGHT_GRAY};

    public static final int BODY_WIDTH = 50;
    public static final int BODY_HEIGHT = BODY_WIDTH - (BODY_WIDTH / 10);
    public static final int X_HEAD = 100;
    public static final int Y_HEAD = (((Window.MAIN_SCENE_HEIGHT - Obstacle.GROUND_HEIGHT) / 2) - BODY_HEIGHT);


    public Bird() {
        this.random = new Random();
        this.body = new Oval(X_HEAD, Y_HEAD, BODY_WIDTH, BODY_HEIGHT, this.colors[random.nextInt(this.colors.length)]);

        this.wing = new Oval(X_HEAD - (BODY_WIDTH / 4), Y_HEAD + (BODY_HEIGHT / 3), BODY_WIDTH - (BODY_WIDTH / 5), (BODY_HEIGHT / 2), Color.GRAY);
        this.eye = new Oval(X_HEAD + (BODY_WIDTH / 2), Y_HEAD, BODY_WIDTH / 2, BODY_WIDTH / 2, Color.WHITE);
        this.pupil = new Oval((X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4)), Y_HEAD + (BODY_HEIGHT / 8), BODY_WIDTH / 5, BODY_HEIGHT / 3, Color.BLACK);
        this.upperLip = new Oval(X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4), Y_HEAD + (2 * BODY_HEIGHT / 5), BODY_WIDTH / 2, BODY_HEIGHT / 4, Color.PINK);
        this.lowerLip = new Oval(X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4), Y_HEAD + (3 * BODY_HEIGHT / 5), BODY_WIDTH / 2, BODY_HEIGHT / 4, Color.PINK);

        this.alive = true;
    }

    public void paint(Graphics graphics) {
        this.body.paint(graphics);
        this.lowerLip.paint(graphics);
        this.upperLip.paint(graphics);
        this.eye.paint(graphics);
        this.pupil.paint(graphics);
        this.wing.paint(graphics);
    }

    public void moveUp() {
        this.body.moveUp();
        setYBird(this.body.getY());
    }

    public void moveDown() {
        if (!isTouchGround()) {
            this.body.moveDown();
            setYBird(this.body.getY());
        }
    }

    public boolean isTouchGround() {
        return (getLowerBird() >= (Window.MAIN_SCENE_HEIGHT - Obstacle.GROUND_HEIGHT));
    }

    public void kill() {
        this.alive = false;
    }

    public int getLowerBird() {
        return (this.body.getY() + BODY_HEIGHT);
    }

    public int getUpperBird() {
        return (this.body.getY());
    }

    public boolean checkCollision(Obstacle obstacle) {
        boolean collision = false;
        Rectangle upperPipeRectangle = new Rectangle(
                obstacle.getUpper().getSmallRectangle().getX(),
                obstacle.getUpper().getBigRectangle().getY(),
                obstacle.getUpper().getSmallRectangle().getWidth(),
                obstacle.getUpper().getBigRectangle().getHeight() + obstacle.getUpper().getSmallRectangle().getHeight()
        );

        Rectangle lowerPipeRectangle = new Rectangle(
                obstacle.getLower().getSmallRectangle().getX(),
                obstacle.getLower().getSmallRectangle().getY(),
                obstacle.getLower().getSmallRectangle().getWidth(),
                obstacle.getLower().getBigRectangle().getHeight() + obstacle.getUpper().getSmallRectangle().getHeight()
        );

        Rectangle birdBody = new Rectangle(
                this.body.getX(),
                this.body.getY(),
                this.body.getWidth() + (this.lowerLip.getWidth() / 3),
                this.body.getHeight()
        );
        if (upperPipeRectangle.intersects(birdBody) || lowerPipeRectangle.intersects(birdBody)) {
            collision = true;
        }
        return collision;
    }

    public boolean isAlive() {
        return alive;
    }

    public void restart() {
        this.body.setY(Y_HEAD);
        this.body.setColor(this.colors[this.random.nextInt(this.colors.length)]);
        setYBird(Y_HEAD);
        this.alive = true;
    }

    private void setYBird(int y) {
        this.wing.setY(y + (BODY_HEIGHT / 3));
        this.eye.setY(y);
        this.pupil.setY(y + (BODY_HEIGHT / 8));
        this.upperLip.setY(y + (2 * BODY_HEIGHT / 5));
        this.lowerLip.setY(y + (3 * BODY_HEIGHT / 5));
    }

    public void wingMoveUp() {
        this.wing.moveUp();
    }

    public void wingMoveDown() {
        this.wing.moveDown();
    }


}