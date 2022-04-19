import java.awt.*;

public abstract class Shape {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;


    public Shape (int x, int y, int width, int height,Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public abstract void paint(Graphics graphics);

    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected Color getColor() {
        return color;
    }

    public void moveDown() {
        this.y++;
    }

    public void moveLeft() {
        this.x--;
    }

    public void moveUp() {
        this.y--;
    }

    public void setY(int y) {
        this.y = y;
    }
}