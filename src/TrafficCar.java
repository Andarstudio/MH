import javax.microedition.lcdui.Graphics;

public class TrafficCar {

    private int x;
    private int y;
    private int lane;

    private int width = 12;
    private int height = 20;

    private boolean hitPlayer;

    public TrafficCar(int startX, int startY, int startLane) {
        x = startX;
        y = startY;
        lane = startLane;
    }

    public void update(int speed) {
        y += speed;
    }

    public void reset() {

        int[] lanes = {30, 55, 80, 100};

        x = lanes[(lane + 1) % lanes.length];
        y = -30;

        lane++;

        if (lane >= lanes.length) {
            lane = 0;
        }

        hitPlayer = false;
    }

    public void draw(Graphics g) {

        g.setColor(0xCC0000);
        g.fillRect(x, y, width, height);

        g.setColor(0xFFFFFF);
        g.fillRect(x + 3, y + 3, 6, 5);

        g.setColor(0x000000);
        g.fillRect(x - 2, y + 3, 2, 5);
        g.fillRect(x + width, y + 3, 2, 5);
        g.fillRect(x - 2, y + 13, 2, 5);
        g.fillRect(x + width, y + 13, 2, 5);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasHitPlayer() {
        return hitPlayer;
    }

    public void setHitPlayer(boolean value) {
        hitPlayer = value;
    }
}