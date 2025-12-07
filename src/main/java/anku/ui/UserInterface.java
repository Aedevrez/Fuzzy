package anku.ui;

import anku.data.*;
import lombok.*;

import java.util.Scanner;

@AllArgsConstructor
public class UserInterface {
    private Scanner scanner;

    public void run() throws Exception {
        double value = 750;

        MarketValueOfTheHouse marketValueOfTheHouse = new MarketValueOfTheHouse(value);
        System.out.println(marketValueOfTheHouse);

        double locationValue = 3.5;
        LocationOfTheHouse locationOfTheHouse = new LocationOfTheHouse(locationValue);
        System.out.println(locationOfTheHouse);

        double assetValue = 550;
        ApplicantAssets applicantAssets = new ApplicantAssets(assetValue);
        System.out.println(applicantAssets);

        double salaryValue = 45;
        ApplicantSalary applicantSalary = new ApplicantSalary(salaryValue);
        System.out.println(applicantSalary);

        double rateValue = 3.5;
        InterestRate interestRate = new InterestRate(rateValue);
        System.out.println(interestRate);
    }
}
