package anku.ui;

import anku.data.ApplicantAssets;
import anku.data.ApplicantSalary;
import anku.data.InterestRate;
import anku.data.LocationOfTheHouse;
import anku.data.computedValues.ApplicantEvaluation;
import anku.data.computedValues.HouseEvaluation;
import anku.data.computedValues.LoanEvaluation;
import anku.data.inputValues.MarketValueOfTheHouse;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GraphicalUserInterface extends Application {
    @Override
    public void start(Stage stage) {
        // UI fields
        TextField marketValueField = new TextField();
        TextField locationField = new TextField();
        TextField assetField = new TextField();
        TextField salaryField = new TextField();
        TextField rateField = new TextField();

        TextArea output = new TextArea();
        output.setEditable(false);

        Button calculate = new Button("Calculate Loan Evaluation");

        calculate.setOnAction(e -> {
            try {
                double mvVal = Double.parseDouble(marketValueField.getText());
                double locVal = Double.parseDouble(locationField.getText());
                double assetVal = Double.parseDouble(assetField.getText());
                double salaryVal = Double.parseDouble(salaryField.getText());
                double rateVal = Double.parseDouble(rateField.getText());

                MarketValueOfTheHouse mv = new MarketValueOfTheHouse(mvVal);
                LocationOfTheHouse loc = new LocationOfTheHouse(locVal);
                ApplicantAssets aa = new ApplicantAssets(assetVal);
                ApplicantSalary sal = new ApplicantSalary(salaryVal);
                InterestRate rate = new InterestRate(rateVal);

                HouseEvaluation house = new HouseEvaluation(mv, loc);
                ApplicantEvaluation applicant = new ApplicantEvaluation(aa, sal);
                LoanEvaluation loan = new LoanEvaluation(sal, rate, applicant, house);

                output.setText(house.toString() + "\n" + applicant.toString() + "\n" + loan.toString());

            } catch (Exception ex) {
                output.setText("Invalid input");
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.addRow(0, new Label("Market Value:"), marketValueField);
        grid.addRow(1, new Label("Location Score:"), locationField);
        grid.addRow(2, new Label("Assets:"), assetField);
        grid.addRow(3, new Label("Salary:"), salaryField);
        grid.addRow(4, new Label("Interest Rate:"), rateField);

        VBox root = new VBox(10, grid, calculate, output);
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle("Loan Evaluation GUI");
        stage.show();
    }

    // original print/run logic moved into this helper function
    private String computeLoanEvaluation(double mv,
                                         double loc,
                                         double assets,
                                         double salary,
                                         double rate) throws Exception {

        StringBuilder sb = new StringBuilder();

        MarketValueOfTheHouse market = new MarketValueOfTheHouse(mv);
        sb.append(market).append("\n");

        LocationOfTheHouse location = new LocationOfTheHouse(loc);
        sb.append(location).append("\n");

        ApplicantAssets applicantAssets = new ApplicantAssets(assets);
        sb.append(applicantAssets).append("\n");

        ApplicantSalary applicantSalary = new ApplicantSalary(salary);
        sb.append(applicantSalary).append("\n");

        InterestRate interestRate = new InterestRate(rate);
        sb.append(interestRate).append("\n");

        HouseEvaluation houseEvaluation = new HouseEvaluation(market, location);
        sb.append(houseEvaluation).append("\n");

        ApplicantEvaluation applicantEvaluation =
                new ApplicantEvaluation(applicantAssets, applicantSalary);
        sb.append(applicantEvaluation).append("\n");

        LoanEvaluation loanEvaluation =
                new LoanEvaluation(applicantSalary, interestRate, applicantEvaluation, houseEvaluation);
        sb.append(loanEvaluation).append("\n");

        return sb.toString();
    }
}