import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;


public class MainScene extends JPanel {

    private Bird bird;
    private LinkedList<Obstacle> obstacles;
    private ImageIcon background;
    private int passedCounter;
    private JLabel score; // ניקוד
    private int playerRecord;
    private EndGameWindow endGameWindow;



    public MainScene(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.bird = new Bird(Color.YELLOW);
        this.playerRecord = 0;
        this.passedCounter = 0;
        this.score = new JLabel();
        this.score.setBounds((Window.MAIN_SCENE_WIDTH / 2) - 15, 5, 50, 60);
        Font scoreFont = new Font("Ariel", Font.BOLD, 50);
        this.score.setFont(scoreFont);
        this.score.setText("" + this.passedCounter);
        this.add(this.score);
        this.endGameWindow = new EndGameWindow();
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
        } catch (ConcurrentModificationException e) {
            System.out.println(e.getMessage());
        }





    }

    private void mainGameLoop() {
        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();

            while (true) {
                if (this.bird.isAlive()) {
                    this.obstacles.add(new Obstacle());
                    if (this.obstacles.getFirst().end()) {
                        this.obstacles.removeFirst();
                    }
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

            Movement movement = new Movement(this.bird);
            this.addKeyListener(movement);

            while (true) {
                this.bird.moveDown();
                if (!this.obstacles.isEmpty()) {
                    if (this.obstacles.getFirst().isPassedBird()) {
                        this.passedCounter++;
                        this.score.setText("" + this.passedCounter);
                    }
                    if (this.bird.getLowerBird() > (Window.MAIN_SCENE_HEIGHT - Obstacle.GROUND_HEIGHT) || this.bird.getUpperBird() < Window.Y_MAIN_SCENE ||
                            this.bird.checkCollision(this.obstacles.getFirst())) {
                        this.bird.kill();
                        this.score.setText("Game Over");
                        if (this.playerRecord < this.passedCounter) {
                            this.playerRecord = this.passedCounter;
                        }
                        this.endGameWindow.setEndGamePanel(this.passedCounter, this.playerRecord);
                        this.add(this.endGameWindow);

                        this.endGameWindow.getRestart().addActionListener((event) -> {
                            restart();
                        });
                    }
                }
                if (this.bird.isAlive()) {
//                    try {
                        for (Obstacle obstacle : this.obstacles) {
                            if (obstacle != null) {
                                obstacle.moveLeft();
                            }
//                        }
                        }
//                    } catch (ConcurrentModificationException e) {
//                        System.out.println(e.getMessage());
//                    }
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

    public void restart() {
        this.remove(this.endGameWindow);
        this.passedCounter = 0;
        this.bird.restart();
        this.score.setText("" + this.passedCounter);
        this.obstacles = new LinkedList<>();
    }
}