import java.awt.*;
import java.util.ArrayList;

public class Rectangle extends Shape {

    public Rectangle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    public void paint(Graphics graphics) {
        graphics.setColor(this.getColor());
        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.setColor(Color.BLACK);
        graphics.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

    }
}