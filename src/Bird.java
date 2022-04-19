import java.awt.*;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Bird {
    private Oval body;
    private Oval wing;
    private Oval eye;
    private Oval pupil;
    private Oval upperLip;
    private Oval lowerLip;
    private boolean alive;

    public static final int BODY_WIDTH = 50;
    public static final int BODY_HEIGHT = BODY_WIDTH - (BODY_WIDTH / 10);
    public static final int X_HEAD = 100;
    public static final int Y_HEAD = (((Window.MAIN_SCENE_HEIGHT - Obstacle.GROUND_HEIGHT) / 2) - BODY_HEIGHT);


    public Bird(Color color) {
        this.body = new Oval(X_HEAD, Y_HEAD, BODY_WIDTH, BODY_HEIGHT, color);
        this.wing = new Oval(X_HEAD - (BODY_WIDTH / 4), Y_HEAD + (BODY_HEIGHT / 3), BODY_WIDTH - (BODY_WIDTH / 5), (BODY_HEIGHT / 2), Color.GRAY);
        this.eye = new Oval(X_HEAD + (BODY_WIDTH / 2), Y_HEAD, BODY_WIDTH / 2, BODY_WIDTH / 2, Color.WHITE);
        this.pupil = new Oval((X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4)), Y_HEAD + (BODY_WIDTH / 8), BODY_WIDTH / 5, BODY_HEIGHT / 3, Color.BLACK);
        this.upperLip = new Oval(X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4), Y_HEAD + (2 * BODY_HEIGHT / 5), BODY_WIDTH / 2, BODY_HEIGHT / 4, Color.PINK);
        this.lowerLip = new Oval(X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4), Y_HEAD + (3 * BODY_HEIGHT / 5), BODY_WIDTH / 2, BODY_HEIGHT / 4, Color.PINK);


        this.alive = true;
    }

    public void paint(Graphics graphics) {
        if (this.alive) {
            this.body.paint(graphics);
            this.lowerLip.paint(graphics);
            this.upperLip.paint(graphics);
            this.eye.paint(graphics);
            this.pupil.paint(graphics);
            this.wing.paint(graphics);


//            Graphics2D g2 = (Graphics2D) graphics;
//            int x = 50;
//            int y = 75;
//            int width = 200;
//            int height = 100;
//            Shape r1 = new Ellipse2D.Float (x, y, width, height);
//            for (int angle = 0; angle <= 180; angle += 20) {
//                g2.rotate(Math.toRadians(angle), x + width / 2, y + height / 2);
//                g2.setPaint(Color.YELLOW);
//                g2.fill(r1);
//                g2.setStroke(new BasicStroke(4));
//                g2.setPaint(Color.BLACK);
//                g2.draw(r1);
//            }

        }
    }

    public void moveUp() {
        for (int i = 0; i < 40; i++) {
            this.body.moveUp();
            this.wing.moveUp();
            this.upperLip.moveUp();
            this.lowerLip.moveUp();
            this.eye.moveUp();
            this.pupil.moveUp();
        }
    }

    public void moveUpSlow() {
        this.body.moveUp();
        this.wing.moveUp();
        this.upperLip.moveUp();
        this.lowerLip.moveUp();
        this.eye.moveUp();
        this.pupil.moveUp();
    }

    public void moveDownSlow() {
        this.body.moveDown();
        this.wing.moveDown();
        this.upperLip.moveDown();
        this.lowerLip.moveDown();
        this.eye.moveDown();
        this.pupil.moveDown();

    }

    public void moveDown() {
        this.body.moveDown();
        this.wing.moveDown();
        this.upperLip.moveDown();
        this.lowerLip.moveDown();
        this.eye.moveDown();
        this.pupil.moveDown();
    }

    public void kill() {
        this.alive = false;
    }

    public int getLowerBird() {
        return (this.body.getY() + this.body.getHeight());
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
        this.alive = true;

        this.body = new Oval(X_HEAD, Y_HEAD, BODY_WIDTH, BODY_HEIGHT, Color.YELLOW);
        this.wing = new Oval(X_HEAD - (BODY_WIDTH / 4), Y_HEAD + (BODY_HEIGHT / 3), BODY_WIDTH - (BODY_WIDTH / 5), (BODY_HEIGHT / 2), Color.GRAY);
        this.eye = new Oval(X_HEAD + (BODY_WIDTH / 2), Y_HEAD, BODY_WIDTH / 2, BODY_WIDTH / 2, Color.WHITE);
        this.pupil = new Oval((X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4)), Y_HEAD + (BODY_WIDTH / 8), BODY_WIDTH / 5, BODY_HEIGHT / 3, Color.BLACK);
        this.upperLip = new Oval(X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4), Y_HEAD + (2 * BODY_HEIGHT / 5), BODY_WIDTH / 2, BODY_HEIGHT / 4, Color.PINK);
        this.lowerLip = new Oval(X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4), Y_HEAD + (3 * BODY_HEIGHT / 5), BODY_WIDTH / 2, BODY_HEIGHT / 4, Color.PINK);
    }

    public void jumpPosition() {
        ///

    }
}