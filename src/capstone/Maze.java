package capstone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.io.File;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Random;

/**
 *
 * @author arthurmathies
 */
public class Maze {//Kreirt sowohl das Terminal als auch das zweidimensionale Array für das spiel

    public static SwingTerminal terminal;//terminal objekt auf dem alles operiert
    private int width;//breite
    private int height;//höhe des terminals
    private Spielfeldtypen[][] map;//map um spielfeld zu speichern. wichtig für gameplay
    private ArrayList<Eingang> eingaenge = new ArrayList<>();//ArrayList für zufallsgenerator zwecke
    private ArrayList<DynamischesHindernis> dynaHinder = new ArrayList<>();
    private int currentx = 0;//current Position des Spielers
    private int currenty = 0;
    public Player player;
    int obereREckeX = 0;//speichert obere rechte ecke
    int obereREckeY = 0;
    int columns = 29;//anzahl der spalten
    int rows = 99;//anzahl der zeilen
//Getter und Setter Methoden folgen
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setX(int x) {
        currentx = currentx + x;
    }

    public void setY(int y) {
        currenty = currenty + y;
    }

    public int getCurrentX() {
        return currentx;
    }

    public int getCurrentY() {
        return currenty;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public Spielfeldtypen[][] getMap() {
        return map;
    }

    public ArrayList getEingaenge() {
        return eingaenge;
    }

    public ArrayList getDynaHinder() {
        return dynaHinder;
    }

    public Maze(String levelName) throws InvalidPropertiesFormatException {//Maze konstruktor
        AmAZE.menuGUI.getScreen().stopScreen();
        try {
            Properties prop = readProp(levelName);
            if (prop.containsKey("Width") && prop.containsKey("Height")) {
                width = Integer.parseInt(prop.getProperty("Width"));
                height = Integer.parseInt(prop.getProperty("Height"));
            } else {
                throw new InvalidPropertiesFormatException("Width und/oder Height sind in Properties File nicht definiert.");
            }

            if (width <= 100 && height <= 30) {//Terminal Größenbegrenzung für angenehmeres Gameplay
                terminal = TerminalFacade.createSwingTerminal(width, height + 1);//terminal in richtiger größe erstellen
            } else {
                terminal = TerminalFacade.createSwingTerminal(100, 31);
            }

            map = new Spielfeldtypen[width][height];//zweidinemsionales array für gameplay
            fillMaze(prop);
            terminal.setCursorVisible(false);
            terminal.enterPrivateMode();//Terminal zeigen
            terminal.getJFrame().setResizable(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }

    }

    private Properties readProp(String levelName) throws FileNotFoundException, IOException {//liest die property datei ein
        Properties prop = new Properties();//Property datei zum auslesen
        FileInputStream in = new FileInputStream(new File(levelName));
        if (in != null) {
            prop.load(in);
        } else {
            in.close();
            throw new FileNotFoundException("property file not found");
        }
        return prop;
    }

    private void fillMaze(Properties prop) throws NumberFormatException, InvalidPropertiesFormatException {//Befüllt das Maze mit den Objekten
        int numEingaenge = 0;
        boolean schluessel = false;
        int leben = 3;
        if (prop.containsKey("Lifes")) {
            leben = Integer.parseInt(prop.getProperty("Lifes"));
        }
        if (prop.containsKey("Schluessel")) {
            if (prop.getProperty("Schluessel").equals("true")) {
                schluessel = true;
            }
        }
        for (int i = 0; i < height; i++) {//geht ganze property datei durch und sucht nach keys und setzt diese
            for (int j = 0; j < width; j++) {
                String y = Integer.toString(i);
                String x = Integer.toString(j);
                if (prop.containsKey(x + "," + y)) {
                    int number = Integer.parseInt(prop.getProperty(x + "," + y));
                    char zeichen;
                    switch (number) {
                        case 0:
                            map[j][i] = new Wand(x + "," + y);
                            zeichen = 'X';
                            terminal.applyForegroundColor(Terminal.Color.BLUE);
                            break;
                        case 1:
                            numEingaenge++;
                            eingaenge.add(new Eingang(x + "," + y));
                            map[j][i] = (eingaenge.get(numEingaenge - 1));
                            zeichen = eingaenge.get(numEingaenge - 1).getZeichen();
                            terminal.applyForegroundColor(Terminal.Color.WHITE);
                            break;
                        case 2:
                            map[j][i] = new Ausgang(x + "," + y);
                            zeichen = '\u2690';
                            terminal.applyForegroundColor(Terminal.Color.WHITE);
                            break;
                        case 3:
                            map[j][i] = new StatischesHindernis(x + "," + y);
                            zeichen = '\u2318';
                            terminal.applyForegroundColor(Terminal.Color.CYAN);
                            break;
                        case 4:
                            map[j][i] = new DynamischesHindernis(x + "," + y);
                            dynaHinder.add((DynamischesHindernis) map[j][i]);
                            zeichen = '\u1F40';
                            terminal.applyForegroundColor(Terminal.Color.RED);
                            break;
                        case 5:
                            map[j][i] = new Schluessel(x + "," + y);
                            zeichen = '$';
                            terminal.applyForegroundColor(Terminal.Color.YELLOW);
                            break;
                        case 6:
                            player = new Player(x + "," + y, leben);
                            map[j][i] = player;
                            if (schluessel) {
                                player.gotSchluessel();
                            }
                            zeichen = 'P';
                            currentx = player.x;
                            currenty = player.y;
                            terminal.applyForegroundColor(Terminal.Color.GREEN);
                            break;
                        default:
                            throw new InvalidPropertiesFormatException("Unzulässige Daten in Properties File.");

                    }
                    //switch statement für die verschiedenen zeichen. 0-6 mit verschiedenen unicodes
                    if (j < 100 && i < 30) {
                        terminal.moveCursor(j, i);
                        terminal.putCharacter(zeichen);
                    }
                }
            }
        }
        if (player == null) {
            placePlayer(3);
        }
    }

    private String searchForEingang() {//Sucht random einen der eingänge aus
        Random random = new Random();
        int randomEingang = random.nextInt(eingaenge.size());
        return eingaenge.get(randomEingang).getPosition();
    }

    public void placePlayer(int lifes) throws NumberFormatException {//setzt Spieler auf Position im Maze
        String position = searchForEingang();
        if (player != null && player.hasSchluessel()) {
            player = new Player(position, lifes);
            player.gotSchluessel();
        } else {
            player = new Player(position, lifes);
        }
        String[] xundy = position.split(",");
        currentx = Integer.parseInt(xundy[0]);
        currenty = Integer.parseInt(xundy[1]);
        map[currentx][currenty] = player;
        if (player.x < obereREckeX || player.x > obereREckeX + rows
                    || player.y < obereREckeY || player.y > obereREckeY + columns){}
        else{
        terminal.moveCursor(currentx, currenty);
        terminal.applyForegroundColor(Terminal.Color.GREEN);
        terminal.putCharacter(player.getZeichen());
        }

    }

}
