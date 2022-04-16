import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Movement implements KeyListener {

    private Bird bird;


    public Movement(Bird bird) {
        this.bird = bird;
    }


    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            this.bird.moveUp();
            this.bird.jumpPosition();
        }
    }


    public void keyReleased(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//        if (keyCode == KeyEvent.VK_SPACE) {
//            this.bird.moveUp();
//        }
    }

}