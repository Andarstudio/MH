import javax.microedition.lcdui.Graphics;

public class PlayerCar {

    private int x;
    private int y;

    private int width = 12;
    private int height = 20;

    private boolean movingLeft;
    private boolean movingRight;

    public PlayerCar(int startX, int startY) {
        x = startX;
        y = startY;
    }

    public void update() {

        if (movingLeft) {
            x -= 3;
        }

        if (movingRight) {
            x += 3;
        }

        if (x < 18) {
            x = 18;
        }

        if (x > 110) {
            x = 110;
        }
    }

    public void moveLeft() {
        movingLeft = true;
        movingRight = false;
    }

    public void moveRight() {
        movingRight = true;
        movingLeft = false;
    }

    public void stop() {
        movingLeft = false;
        movingRight = false;
    }

    public boolean collidesWith(TrafficCar car) {

        return x < car.getX() + car.getWidth()
            && x + width > car.getX()
            && y < car.getY() + car.getHeight()
            && y + height > car.getY();
    }

    public void draw(Graphics g) {

        g.setColor(0x0000FF);
        g.fillRect(x, y, width, height);

        g.setColor(0xFFFFFF);
        g.fillRect(x + 3, y + 3, 6, 5);

        g.setColor(0x000000);
        g.fillRect(x - 2, y + 3, 2, 5);
        g.fillRect(x + width, y + 3, 2, 5);
        g.fillRect(x - 2, y + 13, 2, 5);
        g.fillRect(x + width, y + 13, 2, 5);
    }
}