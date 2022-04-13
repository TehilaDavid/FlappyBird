import java.awt.*;
import java.awt.Rectangle;

public class Bird {
    private Oval body;
    private Oval wing;
    private Oval eye;
    private Oval pupil;
    private Oval upperLip;
    private Oval lowerLip;
    private boolean alive;

    public static final int X_HEAD = 100;
    public static final int Y_HEAD = 0;
    public static final int BODY_WIDTH = 50;
    public static final int BODY_HEIGHT = BODY_WIDTH-(BODY_WIDTH/10);


    public Bird (Color color) {
        this.body = new Oval(X_HEAD, Y_HEAD, BODY_WIDTH, BODY_HEIGHT,color);
        this.wing = new Oval(X_HEAD - (BODY_WIDTH/4), Y_HEAD + (BODY_HEIGHT / 3), BODY_WIDTH - (BODY_WIDTH/5), (BODY_HEIGHT / 2),Color.GRAY);
        this.eye = new Oval(X_HEAD + (BODY_WIDTH / 2), Y_HEAD, BODY_WIDTH / 2, BODY_WIDTH / 2,Color.WHITE);
        this.pupil = new Oval((X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4)), Y_HEAD+(BODY_WIDTH / 8) , BODY_WIDTH / 5, BODY_HEIGHT / 3, Color.BLACK);
        this.upperLip = new Oval(X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4), Y_HEAD+(2*BODY_HEIGHT/5), BODY_WIDTH / 2, BODY_HEIGHT / 4, Color.PINK);
        this.lowerLip = new Oval(X_HEAD + (BODY_WIDTH / 2) + (BODY_WIDTH / 4), Y_HEAD+(3*BODY_HEIGHT/5), BODY_WIDTH / 2, BODY_HEIGHT / 4, Color.PINK);

        this.alive = true;
    }

    public void paint (Graphics graphics){
        if (this.alive){
            this.body.paint(graphics);
            this.lowerLip.paint(graphics);
            this.upperLip.paint(graphics);
            this.eye.paint(graphics);
            this.pupil.paint(graphics);
            this.wing.paint(graphics);
        }
    }

    public void moveUp() {
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                this.body.moveUp();
                this.wing.moveUp();
                this.upperLip.moveUp();
                this.lowerLip.moveUp();
                this.eye.moveUp();
                this.pupil.moveUp();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
}