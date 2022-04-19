import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    private boolean start;
    private Movement movement;
    private int counterUpDownTime = 0;
    private JLabel pressToStart;
    private File song;


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

        this.mainGameLoop();
        this.obstacles = new LinkedList<>();

        this.start = false;
        this.pressToStart = new JLabel("Press 'Space' To Start");
        this.pressToStart.setBounds(Window.MAIN_SCENE_WIDTH / 4, Bird.Y_HEAD, 2 * Window.MAIN_SCENE_WIDTH / 3, Window.MAIN_SCENE_HEIGHT / 10);
        this.pressToStart.setFont(scoreFont);
        this.pressToStart.setForeground(Color.WHITE);



    }


        protected void paintComponent (Graphics g){
            super.paintComponent(g);
            this.background = new ImageIcon("background.png");
            this.background.paintIcon(this, g, 0, -50);
            this.bird.paint(g);

            try {
                for (Obstacle obstacle : this.obstacles) {
                    obstacle.paint(g);
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }

            if (this.endGameWindow != null) {
                this.endGameWindow.boundarySettings();
            }

        }

        private void mainGameLoop () {


            new Thread(() -> {
                this.setFocusable(true);
                this.requestFocus();



                this.movement = new Movement(this.bird);
                this.addKeyListener(movement);

                while (true) {
                    this.start = movement.isStart();

                    if (this.start) {
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

                                if (this.endGameWindow == null) {
                                    this.endGameWindow = new EndGameWindow(this.passedCounter, this.playerRecord);
                                    this.add(this.endGameWindow);
                                    this.endGameWindow.boundarySettings();
                                } else {
                                    this.endGameWindow.getRestart().addActionListener((event) -> {
                                        restart();
                                    });
                                }
                            }
                        }
                        try {
                            for (Obstacle obstacle : this.obstacles) {
                                if (obstacle != null) {
                                    obstacle.moveLeft();
                                }
                            }
                        } catch (ConcurrentModificationException e) {
                            e.printStackTrace();
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

            new Thread(() -> {
                this.setFocusable(true);
                this.requestFocus();


                while (true) {
                    if (this.bird.isAlive() && this.start) {
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

                boolean isPressExist = false;

                while (true) {
                    if (!this.start) {
                        if (counterUpDownTime % 2 == 0) {
                            this.bird.moveDownSlow();
                        } else {
                            this.bird.moveUpSlow();
                        }
                        if (!isPressExist) {
                            this.add(this.pressToStart);
                            isPressExist = true;
                        }

                    } else if (isPressExist) {
                        this.remove(this.pressToStart);
                        isPressExist = false;
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


                while (true) {
                    if (!this.start) {
                        this.counterUpDownTime = this.counterUpDownTime + 1;
                    }

                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();

        }


        public void restart () {
            if (this.endGameWindow != null) {
                this.remove(this.endGameWindow);
                this.endGameWindow.boundarySettings();
            }
            this.endGameWindow = null;
            this.passedCounter = 0;
            this.bird.restart();
            this.score.setText("" + this.passedCounter);
            this.obstacles = new LinkedList<>();
            this.movement.setStart(false);
            this.pressToStart.setBounds(Window.MAIN_SCENE_WIDTH / 4, Bird.Y_HEAD, 2 * Window.MAIN_SCENE_WIDTH / 3, Window.MAIN_SCENE_HEIGHT / 10);
        }
    }