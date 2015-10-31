package capstone;

import com.googlecode.lanterna.TerminalFacade;
import java.util.InvalidPropertiesFormatException;
import com.googlecode.lanterna.gui.GUIScreen;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthurmathies
 */
public class AmAZE {//Spiel Methode mit main methode von hier aus läuft das spiel

    static GamePlay game = new GamePlay();//neues gameplay erstellen. auf diesem läuft zukünftig das spiel
    static GUIScreen menuGUI;

    public static void startGame() throws InvalidPropertiesFormatException {//ruft hauptmenü auf von dem an ein spiel gestartet werden kann
        if (menuGUI != null) {
            menuGUI.getActiveWindow().close();
            menuGUI.getScreen().stopScreen();
        }
        menuGUI = TerminalFacade.createGUIScreen();
        if (menuGUI == null) {
            System.err.println("Couldn't allocate a terminal!");
            return;
        }
        menuGUI.getScreen().startScreen();
        menuGUI.showWindow(new HauptMenue(game), GUIScreen.Position.CENTER);
    }

    public static void main(String[] args) {
        try {
            startGame();//spiel wird gestartet
        } catch (InvalidPropertiesFormatException ex) {
            Logger.getLogger(AmAZE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
