import javax.swing.*;

public class Window extends JFrame {

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 500;

    public static final int X_MAIN_SCENE = 0;
    public static final int Y_MAIN_SCENE = 0;
    public static final int MAIN_SCENE_WIDTH = 1000;
    public static final int MAIN_SCENE_HEIGHT = 500;


    private ImageIcon background;
    private JLabel label;


    public static void main(String[] args) {
        Window window = new Window();
    }

    public Window () {
        this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        MainScene mainScene = new MainScene(X_MAIN_SCENE,Y_MAIN_SCENE,MAIN_SCENE_WIDTH,MAIN_SCENE_HEIGHT);
        this.add(mainScene);



    }


}
