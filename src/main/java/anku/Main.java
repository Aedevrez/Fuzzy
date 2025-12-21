package anku;

import anku.ui.GraphicalUserInterface;
import anku.ui.TerminalUserInterface;

import java.util.Optional;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
    public static void main(String[] args) throws Exception {
        /*Scanner scanner = new Scanner(System.in);

        TerminalUserInterface tui = new TerminalUserInterface(scanner);
        tui.run();*/

        GraphicalUserInterface.launch(GraphicalUserInterface.class, args);
    }
}
