/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import java.util.InvalidPropertiesFormatException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthurmathies
 */
public class Lost extends Window {//Window das dem Spieler gezeigt wird wenn er verloren hat (stirbt)
    public Lost() {
        super("**Sie haben verloren**");
        addComponent(new Button("Zurück zum Hauptmenü", new Action() {
            @Override
            public void doAction() {
                try {
                    AmAZE.game.maze.getTerminal().exitPrivateMode();
                    AmAZE.startGame();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(Won.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
    }
}
