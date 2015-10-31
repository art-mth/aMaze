/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author arthurmathies
 */
public class Eingang extends Spielfeldtypen {

    private final char zeichen;
    private final String position;
private final Terminal.Color color;
    public Eingang(String position) {
        zeichen = 'E';
        this.position = position;
        color = Terminal.Color.WHITE;
    }
    public String getPosition(){
        return position;
    }
    public Terminal.Color getColor(){
        return color;
    }

    @Override
    public char getZeichen() {
        return zeichen;
    }
    @Override
    public String toString(){
        return Character.toString(zeichen);
    }
}
