package capstone;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import java.util.InvalidPropertiesFormatException;
import com.googlecode.lanterna.gui.GUIScreen;

/**
 *
 * @author arthurmathies
 */
public class GamePlay {
    //Die idee wird hier sein den charakter so zu bewegen das er nirgendswo gegen läuft und die und die 
    //sachen im ernstfall passieren.
    //Dafür gibt es ein array um positionen zu überprüfen.

    Maze maze;
    boolean gameOn = true;

    public GamePlay() {
        gameOn = true;
    }

    void start() throws InvalidPropertiesFormatException {//startet das gameplay
        if (AmAZE.menuGUI != null) {
            AmAZE.menuGUI.getScreen().stopScreen();
        }
        gameOn = true;
        usereingabe();
    }

    private void usereingabe() throws InvalidPropertiesFormatException {//fragt nach der usereingabe
        long x = 0;//zählvariable für monster bewegung
        try {
            while (gameOn == true) {//solange das spiel gespielt wird wird auf false gesetzt sobald esc gedrückt wird oder spiel beendet ist
                screenCheck();
                maze.getTerminal().moveCursor(0, maze.getHeight() + 1);
                for (int i = 0; i < maze.player.getLeben(); i++) {
                    maze.getTerminal().applyForegroundColor(Terminal.Color.RED);
                    maze.getTerminal().putCharacter('\u2665');
                }
                if (maze.player.hasSchluessel()) {
                    maze.getTerminal().applyForegroundColor(Terminal.Color.YELLOW);
                    maze.getTerminal().putCharacter('$');
                }
                Key key = maze.getTerminal().readInput();
                if (key != null) {//nur durchlaufen wenn user taste gedrückt hat um laufzeit zu sparen
                    Key.Kind taste = key.getKind();
                    if (maze.getCurrentY() != maze.getHeight() - 1) {
                        if (taste.compareTo(Key.Kind.ArrowDown) == 0) {
                            checkField(0, 1);
                        }
                    }
                    if (maze.getCurrentY() != 0) {
                        if (taste.compareTo(Key.Kind.ArrowUp) == 0) {
                            checkField(0, -1);
                        }
                    }
                    if (maze.getCurrentX() != 0) {
                        if (taste.compareTo(Key.Kind.ArrowLeft) == 0) {
                            checkField(-1, 0);
                        }
                    }
                    if (maze.getCurrentX() != maze.getWidth() - 1) {
                        if (taste.compareTo(Key.Kind.ArrowRight) == 0) {
                            checkField(1, 0);
                        }
                    }
                    if (taste.compareTo(Key.Kind.Escape) == 0) {
                        AmAZE.menuGUI = TerminalFacade.createGUIScreen();
                        AmAZE.menuGUI.getScreen().startScreen();
                        AmAZE.menuGUI.showWindow(new SpielMenue(), GUIScreen.Position.CENTER);
                        AmAZE.menuGUI.getScreen().refresh();
                    }
                }
                if (x % 5 == 0) {//jeder 5te durchlauf werden die mosnter bewegt
                    monsterattack();
                    if (maze.player.getLeben() == -1) {
                        gameOn = false;
                        AmAZE.menuGUI = AmAZE.menuGUI = TerminalFacade.createGUIScreen();
                        if (AmAZE.menuGUI == null) {
                            System.err.println("Couldn't allocate a terminal!");
                            return;
                        }
                        AmAZE.menuGUI.getScreen().startScreen();
                        AmAZE.menuGUI.showWindow(new Lost(), GUIScreen.Position.CENTER);
                    }
                }
                Thread.sleep(50);
                x++;
                maze.getTerminal().moveCursor(0, maze.getHeight() + 1);
                for (int i = 0; i < 4; i++) {
                    maze.getTerminal().applyForegroundColor(Terminal.Color.BLACK);
                    maze.getTerminal().putCharacter(' ');
                }
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void screenCheck() {//Checkt ob der Spieler auf dem Terminal zu sehen ist.
        if (maze.getMap().length > 100 || maze.getMap()[0].length > 30) {
            if (maze.player.x < maze.obereREckeX || maze.player.x > maze.obereREckeX + maze.rows
                    || maze.player.y < maze.obereREckeY || maze.player.y > maze.obereREckeY + maze.columns) {
                changeDisplay();
            }

        }

    }

    private void changeDisplay() {//verändert den screen sollte der player nicht auf dem terminal zu sehen sein
        while (maze.player.x < maze.obereREckeX || maze.player.x > maze.obereREckeX + maze.rows
                || maze.player.y < maze.obereREckeY || maze.player.y > maze.obereREckeY + maze.columns) {
            if (maze.player.x > maze.obereREckeX + maze.rows) {
                maze.obereREckeX += maze.rows + 1;
            }
            if (maze.player.x < maze.obereREckeX) {
                if (maze.obereREckeX - maze.rows + 1 > 0) {
                    maze.obereREckeX -= maze.rows + 1;
                } else {
                    maze.obereREckeX = 0;
                }
            }

            if (maze.player.y < maze.obereREckeY || maze.player.y > maze.obereREckeY + maze.columns) {
                if (maze.player.y > maze.obereREckeY + maze.columns) {
                    maze.obereREckeY += maze.columns + 1;
                }
                if (maze.player.y < maze.obereREckeY) {
                    if (maze.obereREckeY - (maze.columns + 1) > 0) {
                        maze.obereREckeY -= maze.columns + 1;
                    } else {
                        maze.obereREckeY = 0;
                    }
                }
            }
            for (int i = 0; i < 30; i++) {//drawing terminal
                for (int j = 0; j < 100; j++) {
                    if (maze.obereREckeX + j < maze.getMap().length && maze.obereREckeY + i < maze.getMap()[0].length) {
                        maze.getTerminal().moveCursor(j, i);
                        if (maze.getMap()[j + maze.obereREckeX][i + maze.obereREckeY] != null) {
                            maze.getTerminal().applyForegroundColor(maze.getMap()[j + maze.obereREckeX][i + maze.obereREckeY].getColor());
                            maze.getTerminal().putCharacter(maze.getMap()[j + maze.obereREckeX][i + maze.obereREckeY].getZeichen());
                        } else {
                            maze.getTerminal().putCharacter(' ');
                        }
                    } else {
                        maze.getTerminal().putCharacter(' ');
                    }
                }
            }

        }
    }

    private void monsterattack() {//monster werden bewegt
        for (int i = 0; i < maze.getDynaHinder().size(); i++) {
            DynamischesHindernis monster = (DynamischesHindernis) maze.getDynaHinder().get(i);
            monster.move(maze);
        }

    }

    private void checkField(int mX, int mY) throws InvalidPropertiesFormatException, InterruptedException {//überprüft die vom spieler ausgewählten felder
        if (maze.getMap()[maze.getCurrentX() + mX][maze.getCurrentY() + mY] != null) {
            if (maze.getMap()[maze.getCurrentX() + mX][maze.getCurrentY() + mY] instanceof DynamischesHindernis) {
                if (maze.player.reduceLeben() == -1) {
                    gameOn = false;
                    AmAZE.menuGUI = AmAZE.menuGUI = TerminalFacade.createGUIScreen();
                    if (AmAZE.menuGUI == null) {
                        System.err.println("Couldn't allocate a terminal!");
                        return;
                    }
                    AmAZE.menuGUI.getScreen().startScreen();
                    AmAZE.menuGUI.showWindow(new Lost(), GUIScreen.Position.CENTER);
                }
                removeC();
                if (maze.player.hasSchluessel()) {
                    maze.player.gotSchluessel();
                    maze.placePlayer(maze.player.getLeben());
                } else {
                    maze.placePlayer(maze.player.getLeben());
                }
                return;
            }
            if (maze.getMap()[maze.getCurrentX() + mX][maze.getCurrentY() + mY] instanceof StatischesHindernis) {
                if (maze.player.reduceLeben() == -1) {
                    gameOn = false;
                }
                removeC();
                if (maze.player.hasSchluessel()) {
                    maze.player.gotSchluessel();
                    maze.placePlayer(maze.player.getLeben());
                } else {
                    maze.placePlayer(maze.player.getLeben());
                }
                return;
            }
            if (maze.getMap()[maze.getCurrentX() + mX][maze.getCurrentY() + mY] instanceof Schluessel) {
                maze.player.gotSchluessel();
                removeC();
                moveP(mX, mY);

            }
            if (maze.getMap()[maze.getCurrentX() + mX][maze.getCurrentY() + mY] instanceof Ausgang) {
                if (maze.player.hasSchluessel()) {
                    gameOn = false;//maybe sound effect for game over soundsnap
                    AmAZE.menuGUI = AmAZE.menuGUI = TerminalFacade.createGUIScreen();
                    if (AmAZE.menuGUI == null) {
                        System.err.println("Couldn't allocate a terminal!");
                        return;
                    }
                    AmAZE.menuGUI.getScreen().startScreen();
                    AmAZE.menuGUI.showWindow(new Won(), GUIScreen.Position.CENTER);
                }
            }
        } else {
            removeC();
            moveP(mX, mY);
        }

    }

    private void removeC() {//player wird von seiner alten position weg genommen
        maze.getTerminal().moveCursor(maze.getCurrentX() % 100, maze.getCurrentY() % 30);
        maze.getTerminal().putCharacter(' ');
        maze.getMap()[maze.getCurrentX()][maze.getCurrentY()] = null;
    }

    private void moveP(int mX, int mY) {//player wird an eine neue stelle gesetzt
        String y = Integer.toString(maze.getCurrentY() + mY);
        String x = Integer.toString(maze.getCurrentX() + mX);
        maze.getMap()[maze.getCurrentX()][maze.getCurrentY()] = null;
        maze.setX(mX);
        maze.setY(mY);
        maze.player.move(x + "," + y);
        maze.getMap()[maze.getCurrentX()][maze.getCurrentY()] = maze.player;
        if (maze.getCurrentX() % 100 < 100 && maze.getCurrentY() % 30 < 30) {
            maze.getTerminal().applyForegroundColor(Terminal.Color.GREEN);
            maze.getTerminal().moveCursor(maze.getCurrentX() % 100, maze.getCurrentY() % 30);
            maze.getTerminal().putCharacter('P');
        }
    }

}
