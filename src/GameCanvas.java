import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public class GameCanvas extends Canvas implements Runnable {

    private Thread thread;
    private boolean running;
    private boolean paused;

    private PlayerCar player;
    private TrafficCar[] traffic;
    private GameManager manager;

    public GameCanvas() {
        setFullScreenMode(true);

        player = new PlayerCar(getWidth() / 2 - 8, getHeight() - 30);

        traffic = new TrafficCar[3];

        traffic[0] = new TrafficCar(35, -40, 0);
        traffic[1] = new TrafficCar(80, -100, 1);
        traffic[2] = new TrafficCar(55, -170, 2);

        manager = new GameManager();

        setCommandListener(null);
    }

    public void startGame() {
        if (thread == null) {
            running = true;
            paused = false;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void pauseGame() {
        paused = true;
    }

    public void stopGame() {
        running = false;

        if (thread != null) {
            try {
                thread.join(500);
            } catch (Exception e) {
            }

            thread = null;
        }
    }

    public void run() {

        while (running) {

            long start = System.currentTimeMillis();

            if (!paused) {
                update();
                repaint();
            }

            long elapsed = System.currentTimeMillis() - start;
            long delay = 50 - elapsed;

            if (delay < 5) {
                delay = 5;
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
    }

    private void update() {

        if (manager.isGameOver() || manager.isFinished()) {
            return;
        }

        player.update();

        for (int i = 0; i < traffic.length; i++) {

            traffic[i].update(manager.getSpeed());

            if (traffic[i].getY() > getHeight() + 30) {
                traffic[i].reset();
                manager.addDistance(10);
            }

            if (player.collidesWith(traffic[i])) {

                if (!traffic[i].hasHitPlayer()) {

                    traffic[i].setHitPlayer(true);
                    manager.loseHeart();

                    traffic[i].reset();
                }
            }
        }

        manager.update();

        if (manager.isGameOver() || manager.isFinished()) {
            manager.saveRecord();
        }
    }

    protected void paint(Graphics g) {

        int w = getWidth();
        int h = getHeight();

        g.setColor(0x228B22);
        g.fillRect(0, 0, w, h);

        // Road
        g.setColor(0x555555);
        g.fillRect(15, 0, w - 30, h);

        // Road edges
        g.setColor(0xFFFFFF);
        g.fillRect(15, 0, 2, h);
        g.fillRect(w - 17, 0, 2, h);

        // Lane markings
        g.setColor(0xFFFFFF);

        int offset = manager.getRoadOffset();

        for (int y = -20 + offset; y < h; y += 25) {
            g.fillRect(w / 2 - 1, y, 2, 12);
        }

        // Traffic
        for (int i = 0; i < traffic.length; i++) {
            traffic[i].draw(g);
        }

        // Player
        player.draw(g);

        // HUD
        g.setColor(0xFFFFFF);

        g.drawString(
            "HP: " + manager.getHearts(),
            2,
            1,
            Graphics.TOP | Graphics.LEFT
        );

        g.drawString(
            "D:" + manager.getDistance(),
            2,
            15,
            Graphics.TOP | Graphics.LEFT
        );

        g.drawString(
            "R:" + manager.getRecord(),
            2,
            29,
            Graphics.TOP | Graphics.LEFT
        );

        if (manager.isFinished()) {
            drawMessage(g, "FINISH!");
        }

        if (manager.isGameOver()) {
            drawMessage(g, "GAME OVER");
        }
    }

    private void drawMessage(Graphics g, String text) {

        int w = getWidth();
        int h = getHeight();

        g.setColor(0x000000);
        g.fillRect(10, h / 2 - 20, w - 20, 40);

        g.setColor(0xFFFFFF);

        g.drawString(
            text,
            w / 2,
            h / 2 - 8,
            Graphics.HCENTER | Graphics.TOP
        );
    }

    protected void keyPressed(int keyCode) {

        int action = getGameAction(keyCode);

        if (action == LEFT) {
            player.moveLeft();
        }

        if (action == RIGHT) {
            player.moveRight();
        }
    }

    protected void keyReleased(int keyCode) {
        int action = getGameAction(keyCode);

        if (action == LEFT || action == RIGHT) {
            player.stop();
        }
    }
}