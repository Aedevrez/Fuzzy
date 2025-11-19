package anku.ui;

import anku.data.MarketValueOfTheHouse;
import lombok.*;

import java.util.Scanner;

@AllArgsConstructor
public class UserInterface {
    private Scanner scanner;

    public void run() throws Exception {
        double value = 78;

        MarketValueOfTheHouse marketValueOfTheHouse = new MarketValueOfTheHouse(value);
        System.out.println(marketValueOfTheHouse);
    }
}
