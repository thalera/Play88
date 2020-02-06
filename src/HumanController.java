import java.util.Scanner;

public class HumanController implements PlayerController {

    private Scanner console;

    public HumanController(Scanner console) {
        this.console = console;
    }

    @Override
    public boolean pickDeck() {
        return false;
    }

    @Override
    public boolean drawBoth() {
        return false;
    }

    @Override
    public boolean leaveDungeon() {
        return false;
    }
}
