package anku;

import anku.ui.UserInterface;

import java.util.Optional;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);

        UserInterface ui = new UserInterface(scanner);
        ui.run();

        double value = 251.;
        if (value < 0) {
            System.out.println("null");;
        }

        double fuzzyValue = max(0.0,
                            min(1.0,
                            min(((value - 50) / 50), ((250 - value) / 50))));

        System.out.println(fuzzyValue);;
    }
}
