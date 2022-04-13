import java.awt.*;

public class Oval extends Shape {

    public Oval(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }


    public void paint(Graphics graphics) {
        graphics.setColor(this.getColor());
        graphics.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.setColor(Color.black);
        graphics.drawOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());

    }


}