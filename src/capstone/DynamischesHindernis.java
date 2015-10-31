package capstone;

import com.googlecode.lanterna.terminal.Terminal;
import java.util.Random;

/**
 *
 * @author arthurmathies
 */
public class DynamischesHindernis extends Spielfeldtypen {

    private Random random = new Random();
    private final char zeichen;
    private String position;
    private final Terminal.Color color;

    public DynamischesHindernis(String position) {
        zeichen = '\u1F40';
        this.position = position;
        color = Terminal.Color.RED;
    }

    @Override
    public Terminal.Color getColor() {
        return color;
    }

    public String getPosition() {
        return position;
    }

    public void move(Maze maze) {//bewegt das dynamische Hindernis in eine zufällige Richtung
        String[] xundy = this.position.split(",");
        int xx = Integer.parseInt(xundy[0]);
        int yy = Integer.parseInt(xundy[1]);
        if ((maze.obereREckeX < xx && xx < maze.obereREckeX + 100) && (maze.obereREckeY < yy && yy < maze.obereREckeY + 30)) {
            int randomRichtung = random.nextInt(4);
            switch (randomRichtung) {
                case 0:
                    gameMove(1, 0, maze);
                    break;
                case 1:
                    gameMove(-1, 0, maze);
                    break;
                case 2:
                    gameMove(0, 1, maze);
                    break;
                case 3:
                    gameMove(0, -1, maze);
                    break;
            }
        }
    }

    private void gameMove(int x, int y, Maze maze) {//bringt die Bewegung auf den screen
        String[] xundy = this.position.split(",");
        int xx = Integer.parseInt(xundy[0]);
        int yy = Integer.parseInt(xundy[1]);
        if (/*(maze.getMap().length > xx + x && maze.getMap()[0].length > yy + y) &&*/(maze.getMap()[xx + x][yy + y] == null || maze.getMap()[xx + x][yy + y] == maze.player)) {
            if (maze.getMap()[xx + x][yy + y] == maze.player) {
                maze.placePlayer(maze.player.reduceLeben());
            }
            this.position = Integer.toString(xx + x) + "," + Integer.toString(yy + y);
            maze.getMap()[xx][yy] = null;
            maze.getMap()[xx + x][yy + y] = this;
            if ((xx + x) % 100 < maze.obereREckeX + 100 && (yy + y) % 30 < maze.obereREckeY + 30) {
                maze.getTerminal().moveCursor(xx % 100, yy % 30);
                maze.getTerminal().putCharacter(' ');
                maze.getTerminal().moveCursor((xx + x) % 100, (yy + y) % 30);
                maze.getTerminal().applyForegroundColor(Terminal.Color.RED);
                maze.getTerminal().putCharacter(zeichen);
            }
        }
    }

    @Override
    public char getZeichen() {
        return zeichen;
    }

    @Override
    public String toString() {
        return Character.toString(zeichen);
    }

}
