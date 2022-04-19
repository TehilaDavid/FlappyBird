import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Movement implements KeyListener {

    private Bird bird;
    private boolean start;



    public Movement(Bird bird) {
        this.bird = bird;
        this.start = false;
    }


    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            this.bird.moveUp();
            this.start = true;
        }
    }


    public void keyReleased(KeyEvent e) {
    }


    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

}