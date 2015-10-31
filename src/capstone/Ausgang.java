package capstone;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
/**
 *
 * @author arthurmathies
 */
public class Ausgang extends Spielfeldtypen {
    private final char zeichen;
    private final String position;
    private final Terminal.Color color;
    public Ausgang(String position){
        zeichen = '\u2690';
        this.position = position;
        color = Terminal.Color.WHITE;
    }
    public Terminal.Color getColor(){
        return color;
    }
    @Override
    public char getZeichen(){
        return zeichen;
    }
    public String getPosition(){
        return position;
    }
    @Override
    public String toString(){
        return Character.toString(zeichen);
    }
}
