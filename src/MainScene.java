import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;


public class MainScene extends JPanel {

    private Bird bird;
    private LinkedList<Obstacle> obstacles;
    private ImageIcon background;


    public MainScene(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.bird = new Bird(Color.YELLOW);
        this.mainGameLoop();
        this.obstacles = new LinkedList<>();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.background = new ImageIcon("background.png");
        this.background.paintIcon(this, g, 0, -50);

        this.bird.paint(g);
        try {
            for (Obstacle obstacle : this.obstacles) {
                obstacle.paint(g);
            }
        }catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    private void mainGameLoop() {

        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();

            while (true) {
                this.obstacles.add(new Obstacle());
                if (this.obstacles.getFirst().end()) {
                    this.obstacles.removeFirst();
                }
                repaint();

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();

            while (true) {
                try {
                    for (Obstacle obstacle : this.obstacles) {
                        obstacle.moveLeft();
                    }
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }

                repaint();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();
            Movement movement = new Movement(this.bird);
            this.addKeyListener(movement);

            while (true) {
                this.bird.moveDown();

                if (!this.obstacles.isEmpty()) {
                    if (this.bird.getLowerBird() > Window.WINDOW_HEIGHT || this.bird.getUpperBird() < Window.Y_MAIN_SCENE ||
                            this.bird.checkCollision(this.obstacles.getFirst())) {
                        this.bird.kill();
                    }
                }

                repaint();

                try {
                    Thread.sleep(9);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
