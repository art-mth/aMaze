package capstone;

import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author arthurmathies
 */
public abstract class Spielfeldtypen {//abstrakte Klasse die als Oberklasse für die anderen gilt

    private char zeichen;
    private String position;
    private Terminal.Color color;

    public Spielfeldtypen() {
    }

    public char getZeichen() {
        return zeichen;
    }

    public Terminal.Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return Character.toString(zeichen);
    }

    public void move(String position) {
    }

    public String getPosition() {
        return position;
    }
}
