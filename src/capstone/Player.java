package capstone;

import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author arthurmathies
 */
public class Player extends Spielfeldtypen {

    private int leben;
    private boolean schluessel = false;
    private final char zeichen;
    private String position;
    int x;
    int y;
    private final Terminal.Color color;

    //hier kommt hin position in x und y notation das bedeutet string position wird geparsed
    public Player(String position, int leben) {
        zeichen = 'P';
        this.position = position;
        String[] xundy = position.split(",");
        x = Integer.parseInt(xundy[0]);
        y = Integer.parseInt(xundy[1]);
        this.leben = leben;
        schluessel = false;
        color = Terminal.Color.GREEN;
    }

    @Override
    public Terminal.Color getColor() {
        return color;
    }

    public int reduceLeben() {//leben des spielers werden reduziert
        if (leben > 1) {
            leben--;
            return leben;
        } else {
            return -1;
        }
    }

    public int getLeben() {
        return leben;
    }

    public boolean hasSchluessel() {//fragt ob der spieler dern schlüssel hat
        return schluessel;
    }

    public void gotSchluessel() {
        schluessel = true;
    }

    @Override
    public char getZeichen() {
        return zeichen;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public void move(String position) {//kümmert sich um die verwaltung der spielposition intern
        this.position = position;
        String[] xundy = position.split(",");
        x = Integer.parseInt(xundy[0]);
        y = Integer.parseInt(xundy[1]);
    }

    @Override
    public String toString() {
        return Character.toString(zeichen);
    }
}
