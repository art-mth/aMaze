package capstone;

import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author arthurmathies
 */
public class Wand extends Spielfeldtypen {

    private final char zeichen;
    private final String position;
    private final Terminal.Color color;

    public Wand(String position) {
        this.zeichen = 'X';
        this.position = position;
        color = Terminal.Color.BLUE;

    }

    @Override
    public char getZeichen() {
        return zeichen;
    }

    public Terminal.Color getColor() {
        return color;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return Character.toString(zeichen);
    }
}
