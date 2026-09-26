import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;

public class CarRacing extends MIDlet {

    private GameCanvas game;

    public void startApp() {
        if (game == null) {
            game = new GameCanvas();
        }

        Display.getDisplay(this).setCurrent(game);
        game.startGame();
    }

    public void pauseApp() {
        if (game != null) {
            game.pauseGame();
        }
    }

    public void destroyApp(boolean unconditional) {
        if (game != null) {
            game.stopGame();
        }
    }
}