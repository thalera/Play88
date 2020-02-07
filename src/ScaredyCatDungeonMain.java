import game.GameManager;
import game.LocalGameManager;
import user_interface.ScaredyTUI;
import user_interface.ScaredyUI;

import java.util.Scanner;

/**
 * Plays Scaredy Cat Dungeon through the console!
 */
public class ScaredyCatDungeonMain {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        ScaredyUI userInterface = new ScaredyTUI(console);
        GameManager gm = new LocalGameManager(userInterface, console);
        gm.begin();
    }
}
