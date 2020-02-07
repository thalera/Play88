package game;

import org.junit.Test;
import user_interface.ScaredyTUI;
import user_interface.ScaredyUI;

import java.util.Scanner;

import static org.junit.Assert.*;

public class GameManagerTest {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        ScaredyUI userInterface = new ScaredyTUI(console);
        GameManager gm = new GameManager(userInterface, console);
        gm.begin();
    }
}