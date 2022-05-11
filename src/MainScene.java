import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;


public class MainScene extends JPanel {

    private Bird bird;
    private LinkedList<Obstacle> obstacles;
    private ImageIcon background;

    private int passedCounter;
    private int playerRecord;
    private JLabel score;
    private EndGameWindow endGameWindow;

    private boolean start;
    private Movement movement;
    private int counterUpDownTime;
    private JLabel pressToStart;

    private boolean isObstaclesListRunning;

    private Music addPointSound;
    private Music collisionSound;
    private boolean birdCollided;
    private Music music;
    private boolean songActivated;

    public static final int BACKGROUND_X = 0;
    public static final int BACKGROUND_Y = -50;

    public static final int  BIRD_VIBRATION_TIME_LOOP = 20;
    public static  final int MOVE_TIME_LOOP = 5;

    public MainScene(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.start = false;
        this.bird = new Bird();
        this.obstacles = new LinkedList<>();
        this.isObstaclesListRunning = false;

        Font font = new Font("Ariel", Font.BOLD, 50);

        this.counterUpDownTime = 0;
        this.pressToStart = new JLabel("Press 'Space' To Start");
        pressToStartSetBounds();
        this.pressToStart.setFont(font);
        this.pressToStart.setForeground(Color.WHITE);

        this.playerRecord = 0;
        this.passedCounter = 0;
        this.score = new JLabel();
        this.score.setBounds((Window.MAIN_SCENE_WIDTH/2)-15,5, 50,60);
        this.score.setFont(font);
        this.score.setText(""+this.passedCounter);
        this.add(this.score);

        this.music = new Music("sounds/song.wav");
        this.addPointSound = new Music("sounds/point.wav");
        this.collisionSound = new Music("sounds/collision sound.wav");

        this.movement = new Movement(this.bird);
        this.mainGameLoop();
    }

    private void pressToStartSetBounds(){
        this.pressToStart.setBounds(Window.MAIN_SCENE_WIDTH / 4, Bird.Y_HEAD, 2 * Window.MAIN_SCENE_WIDTH / 3, Window.MAIN_SCENE_HEIGHT / 10);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.background = new ImageIcon("background.png");
        this.background.paintIcon(this, g, BACKGROUND_X, BACKGROUND_Y);
        try {

            if (!this.isObstaclesListRunning){
                this.isObstaclesListRunning = true;
                for (Obstacle obstacle : this.obstacles) {
                    if (obstacle != null){
                        obstacle.paint(g);
                    }
                }
                this.isObstaclesListRunning = false;
            }
        }catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        this.bird.paint(g);

        if (this.endGameWindow != null) {
            this.endGameWindow.boundarySettings();
        }
    }

    private void mainGameLoop() {
        birdVibrationLoop();
        obstacleListLoop();
        moveLoop();
    }

    private void obstacleListLoop (){
        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();

            while (true) {
                Obstacle obstacle = new Obstacle();
                if (this.start && this.bird.isAlive() && !this.isObstaclesListRunning) {
                    this.obstacles.add(obstacle);
                    repaint();
                    try {
                        Thread.sleep(MOVE_TIME_LOOP * 300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void moveLoop(){
        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(this.movement);
            while (true) {
                this.start = this.movement.isStart();
                if (this.start){
                    this.bird.moveDown();
                    obstaclesMoveLeft();
                    tests();
                    repaint();
                }
                try {
                    Thread.sleep(MOVE_TIME_LOOP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void tests(){
        if (!this.obstacles.isEmpty()) {
            if (this.obstacles.getFirst().end() && !this.isObstaclesListRunning) {
                this.obstacles.removeFirst();
            }
            if (this.obstacles.getFirst().isPassedBird() && this.bird.isAlive()) {
                this.passedCounter++;
                this.addPointSound.play();
                this.score.setText("" + this.passedCounter);
            }
            if (this.bird.isTouchGround() || this.bird.getUpperBird() <= Window.Y_MAIN_SCENE ||
                    this.bird.checkCollision(this.obstacles.getFirst())) {
                if (this.songActivated){
                    try {
                        this.music.stop();
                        this.songActivated = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!birdCollided) {
                    this.collisionSound.play();
                    birdCollided = true;
                }
                this.bird.kill();
                this.score.setText("Game Over");
                if (this.playerRecord < this.passedCounter){
                    this.playerRecord = this.passedCounter;
                }
                if (this.endGameWindow == null){
                    this.endGameWindow = new EndGameWindow(this.passedCounter, this.playerRecord);
                    this.endGameWindow.boundarySettings();
                    this.add(this.endGameWindow);
                }else if (this.endGameWindow.isRestart()) {
                    restart();
                }
            }
        }
    }

    private void restart (){
        this.start = false;
        if (this.endGameWindow != null) {
            this.remove(this.endGameWindow);
            this.endGameWindow.boundarySettings();
            this.endGameWindow = null;
        }
        this.bird.restart();
        this.passedCounter = 0;
        this.score.setText(""+this.passedCounter);
        while (!this.obstacles.isEmpty()){
            if (!this.isObstaclesListRunning){
                this.obstacles.clear();
            }
        }
        this.movement.setStart(false);
        pressToStartSetBounds();
        this.birdCollided = false;

    }

    private void obstaclesMoveLeft(){
        if (this.bird.isAlive() && this.start) {
            try {
                if (!this.isObstaclesListRunning){
                    this.isObstaclesListRunning = true;
                    for (Obstacle obstacle : this.obstacles) {
                        if (obstacle != null){
                            obstacle.moveLeft();
                        }
                    }
                    this.isObstaclesListRunning = false;
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
    }

    private void birdVibrationLoop(){
        new Thread(() -> {
            this.setFocusable(true);
            this.requestFocus();

            boolean isPressVisible = false;

            while (true) {
                if (!this.start) {
                    if (!this.songActivated){
                        this.music.play();
                        this.songActivated = true;
                    }
                    if (counterUpDownTime % 2 == 0) {
                        this.bird.moveDown();
                    } else {
                        this.bird.moveUp();
                    }
                    if (!isPressVisible) {
                        this.add(this.pressToStart);
                        isPressVisible = true;
                    }
                } else if (isPressVisible) {
                    this.remove(this.pressToStart);
                    isPressVisible = false;
                }
                repaint();
                try {
                    Thread.sleep(BIRD_VIBRATION_TIME_LOOP);
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
                    this.counterUpDownTime ++;
                }
                try {
                    Thread.sleep(BIRD_VIBRATION_TIME_LOOP * 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}