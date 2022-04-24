import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EndGameWindow extends JPanel {

    private JLabel currentScore;
    private JLabel record;
    private JButton restartButton;

    public static final int END_GAME_PANEL_WIDTH = 400;
    public static final int END_GAME_PANEL_HEIGHT = 200;

    public EndGameWindow (int currentScore, int record){
        drawOnPanel();
        boundarySettings();
        this.currentScore.setText("Your score :  " + currentScore);
        this.record.setText("Your record : " + record);
    }

    public void boundarySettings () {
        this.setBounds((Window.MAIN_SCENE_WIDTH / 2) - (END_GAME_PANEL_WIDTH / 2), (Window.MAIN_SCENE_HEIGHT / 2) - ((3 * END_GAME_PANEL_HEIGHT) / 4), END_GAME_PANEL_WIDTH, END_GAME_PANEL_HEIGHT);
        this.currentScore.setBounds(END_GAME_PANEL_WIDTH / 8, END_GAME_PANEL_HEIGHT / 10, END_GAME_PANEL_WIDTH, END_GAME_PANEL_HEIGHT / 5);
        this.record.setBounds(END_GAME_PANEL_WIDTH / 8, 7 * END_GAME_PANEL_HEIGHT / 20, END_GAME_PANEL_WIDTH, END_GAME_PANEL_HEIGHT / 5);
        this.restartButton.setBounds(END_GAME_PANEL_WIDTH / 4, 13 * END_GAME_PANEL_HEIGHT / 20, END_GAME_PANEL_WIDTH / 2, END_GAME_PANEL_HEIGHT / 5);
    }

    private void drawOnPanel() {
        this.setBackground(Color.PINK);
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(blackLine);

        Font font = new Font("DialogInput", Font.BOLD, 35);

        this.currentScore = new JLabel();
        this.currentScore.setFont(font);
        this.currentScore.setText("Your score : ");
        this.add(this.currentScore);

        this.record = new JLabel();
        this.record.setFont(font);
        this.record.setText("Your record : ");
        this.add(this.record);

        Font buttonFont = new Font("DialogInput", Font.BOLD, 30);
        this.restartButton = new JButton();
        this.restartButton.setFont(buttonFont);
        this.restartButton.setText("RESTART");
        this.restartButton.setBackground(Color.WHITE);
        this.add(restartButton);
    }

//    public void setEndGamePanel (int currentScore, int record){
//        this.setBounds((Window.MAIN_SCENE_WIDTH/2) - (END_GAME_PANEL_WIDTH / 2), (Window.MAIN_SCENE_HEIGHT/2) - ((3*END_GAME_PANEL_HEIGHT)/4) , END_GAME_PANEL_WIDTH , END_GAME_PANEL_HEIGHT);
//        this.currentScore.setBounds(END_GAME_PANEL_WIDTH / 8,END_GAME_PANEL_HEIGHT/10, END_GAME_PANEL_WIDTH, END_GAME_PANEL_HEIGHT/5);
//        this.record.setBounds(END_GAME_PANEL_WIDTH / 8, 7*END_GAME_PANEL_HEIGHT / 20 , END_GAME_PANEL_WIDTH, END_GAME_PANEL_HEIGHT / 5);
//        this.restartButton.setBounds(END_GAME_PANEL_WIDTH / 4, 13*END_GAME_PANEL_HEIGHT / 20, END_GAME_PANEL_WIDTH / 2, END_GAME_PANEL_HEIGHT / 5);
//        this.currentScore.setText("Your score : " + currentScore);
//        this.record.setText("Your record : " + record);
//    }

    public JButton getRestartButton() {
        return restartButton;
    }
}