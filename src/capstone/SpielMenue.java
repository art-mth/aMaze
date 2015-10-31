/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.*;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import java.io.File;
import java.io.FileOutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthurmathies
 */
public class SpielMenue extends Window {//Menü das geöffnet wird wenn während des Spiels escape gedrüclt wird

    public SpielMenue() {
        super("Spielmenü");

        addComponent(new Button("Spiel fortsetzen", new Action() {
            @Override
            public void doAction() {
                AmAZE.menuGUI.getScreen().stopScreen();
                try {
                    AmAZE.game.start();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(SpielMenue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        addComponent(new Button("zum Hauptmenü", new Action() {
            @Override
            public void doAction() {
                try {
                    AmAZE.game.maze.getTerminal().exitPrivateMode();
                    AmAZE.startGame();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(SpielMenue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        addComponent(new Button("Spiel speichern (Slot1)", new Action() {
            @Override
            public void doAction() {
                write("slot1.properties");
            }
        }));
        addComponent(new Button("Spiel speichern (Slot2)", new Action() {
            @Override
            public void doAction() {
                write("slot2.properties");
            }
        }));
        addComponent(new Button("Spiel speichern (Slot3)", new Action() {
            @Override
            public void doAction() {
                write("slot3.properties");
            }
        }));
        addComponent(new Button("Legende", new Action() {
            @Override
            public void doAction() {
                AmAZE.menuGUI.getActiveWindow().close();
                MessageBox.showMessageBox(getOwner(), "Spiellegende", "X=Wand, \u2690=Ausgang, \u1F40=Dynamisches Hindernis\n E=Eingang, $=Schluessel, P=Spieler, \u2318=statisches Hindernis ");
                AmAZE.menuGUI.getScreen().stopScreen();
                try {
                    AmAZE.game.start();
                } catch (InvalidPropertiesFormatException ex) {
                    Logger.getLogger(SpielMenue.class.getName()).log(Level.SEVERE, null, ex);
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
    static final char idWall = '0';
    static final char idIn = '1';
    static final char idOut = '2';
    static final char idStaticTrap = '3';
    static final char idDynamicTrap = '4';
    static final char idKey = '5';

    static void write(String propFileName) {
        //prop.setProperty("SeedValue", (Long.valueOf(seed)).toString());
        Properties prop = new Properties();
        int width = AmAZE.game.maze.getMap().length;
        int height = AmAZE.game.maze.getMap()[0].length;
        prop.setProperty("Width", "" + width);
        prop.setProperty("Height", "" + height);
        prop.setProperty("Lifes", "" +AmAZE.game.maze.player.getLeben());
        prop.setProperty("Schluessel", "" +"true");
        for (int x=0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String coordinates = x+","+y;
                if(AmAZE.game.maze.getMap()[x][y]!=null){
                char next = AmAZE.game.maze.getMap()[x][y].getZeichen();
                
                switch (next) {
                    case 'X':
                        prop.setProperty(coordinates, "" + idWall);
                        break;
                    case 'E':
                        prop.setProperty(coordinates, "" + idIn);
                        break;
                    case '\u2690':
                        prop.setProperty(coordinates, "" + idOut);
                        break;
                    case '\u1F40':
                        prop.setProperty(coordinates, "" + idDynamicTrap);
                        break;
                    case '\u2318':
                        prop.setProperty(coordinates, "" + idStaticTrap);
                        break;  
                    case '$':
                        prop.setProperty(coordinates, "" + idKey);
                        break;
                    case 'P':
                        prop.setProperty(coordinates, "" + '6');
                        break; 
                    default:
                        break;
                }
                if (next >= '1' && next <= '9') {
                    prop.setProperty(coordinates, "" + next);
                }
                }
            }
        }
        try {
            FileOutputStream outFile = new FileOutputStream(new File(propFileName));
            prop.store(outFile, "Properties (Seed)");
            outFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
