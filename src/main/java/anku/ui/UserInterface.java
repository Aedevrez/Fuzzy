package anku.ui;

import anku.data.MarketValueOfTheHouse;
import lombok.*;

import java.util.Scanner;

@AllArgsConstructor
public class UserInterface {
    private Scanner scanner;

    public void run() {
        double value = 861.;

        MarketValueOfTheHouse marketValueOfTheHouse = new MarketValueOfTheHouse(value);
        System.out.println(marketValueOfTheHouse);
    }
}
