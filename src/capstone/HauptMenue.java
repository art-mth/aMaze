
package capstone;

/**
 *
 * @author arthurmathies
 */
import com.googlecode.lanterna.gui.*;
import com.googlecode.lanterna.gui.component.Button;
import java.util.InvalidPropertiesFormatException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HauptMenue extends Window {

    public HauptMenue(GamePlay game) throws InvalidPropertiesFormatException {//Hauptmenü in dem neue spiele oder 
        super("Hauptmenü");                                                     //gespeicherte Spiele gestartet werden können
                                                                                //verschiedene Schwierigkeiten können ausgesucht werden
        addComponent(new Button("Neues Spiel starten(difficulty:easy)", new Action() {//Hauptmenü um neue Spiele zu laden
            @Override
            public void doAction() {
                try {
                    game.maze = new Maze("easy.properties");
                    game.start();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(HauptMenue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        addComponent(new Button("Neues Spiel starten(difficulty:medium)", new Action() {
            @Override
            public void doAction() {
                try {
                    game.maze = new Maze("medium.properties");
                    game.start();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(HauptMenue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        addComponent(new Button("Neues Spiel starten(difficulty:hard)", new Action() {
            @Override
            public void doAction() {
                try {
                    game.maze = new Maze("hard.properties");
                    game.start();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(HauptMenue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        addComponent(new Button("Slot 1 laden", new Action() {
            @Override
            public void doAction() {
                try {
                    game.maze = new Maze("slot1.properties");
                    game.start();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(HauptMenue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        addComponent(new Button("Slot 2 laden", new Action() {
            @Override
            public void doAction() {
                try {
                    game.maze = new Maze("slot2.properties");
                    game.start();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(HauptMenue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        addComponent(new Button("Slot 3 laden", new Action() {
            @Override
            public void doAction() {
                try {
                    game.maze = new Maze("slot3.properties");
                    game.start();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(HauptMenue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        addComponent(new Button("Spiel verlassen", new Action() {
            @Override
            public void doAction() {
                System.exit(0);
            }
        }));
    }

}
