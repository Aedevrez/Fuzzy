package anku;

import anku.ui.UserInterface;

import java.util.Optional;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
    static void main() throws Exception {
        Scanner scanner = new Scanner(System.in);

        UserInterface ui = new UserInterface(scanner);
        ui.run();
    }
}
