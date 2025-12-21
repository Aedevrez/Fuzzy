package anku.ui;

import anku.data.ApplicantAssets;
import anku.data.ApplicantSalary;
import anku.data.InterestRate;
import anku.data.LocationOfTheHouse;
import anku.data.computedValues.ApplicantEvaluation;
import anku.data.computedValues.HouseEvaluation;
import anku.data.computedValues.LoanEvaluation;
import anku.data.inputValues.MarketValueOfTheHouse;
import lombok.*;

import java.util.Scanner;

@AllArgsConstructor
public class TerminalUserInterface {
    private Scanner scanner;

    public void run() throws Exception {
        double value = 150; //750
        MarketValueOfTheHouse marketValueOfTheHouse = new MarketValueOfTheHouse(value);
        System.out.println(marketValueOfTheHouse);

        double locationValue = 2; //3.5
        LocationOfTheHouse locationOfTheHouse = new LocationOfTheHouse(locationValue);
        System.out.println(locationOfTheHouse);

        double assetValue = 50;
        ApplicantAssets applicantAssets = new ApplicantAssets(assetValue);
        System.out.println(applicantAssets);

        double salaryValue = 15;
        ApplicantSalary applicantSalary = new ApplicantSalary(salaryValue);
        System.out.println(applicantSalary);

        double rateValue = 8.5;
        InterestRate interestRate = new InterestRate(rateValue);
        System.out.println(interestRate);

        HouseEvaluation houseEvaluation = new HouseEvaluation(marketValueOfTheHouse, locationOfTheHouse);
        System.out.println(houseEvaluation);

        ApplicantEvaluation applicantEvaluation = new ApplicantEvaluation(applicantAssets, applicantSalary);
        System.out.println(applicantEvaluation);

        LoanEvaluation loanEvaluation = new LoanEvaluation(applicantSalary, interestRate, applicantEvaluation, houseEvaluation);
        System.out.println(loanEvaluation);
    }
}
