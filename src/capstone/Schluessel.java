package capstone;

import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author arthurmathies
 */
public class Schluessel extends Spielfeldtypen {

    private final char zeichen;
    private final String position;
    private final Terminal.Color color;

    public Schluessel(String position) {
        zeichen = '$';
        this.position = position;
        color = Terminal.Color.YELLOW;

    }

    @Override
    public Terminal.Color getColor() {
        return color;
    }

    @Override
    public char getZeichen() {
        return zeichen;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return Character.toString(zeichen);
    }
}
